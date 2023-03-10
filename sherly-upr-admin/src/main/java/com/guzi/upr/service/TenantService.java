package com.guzi.upr.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.constants.SqlParam;
import com.guzi.upr.constants.SqlStatement;
import com.guzi.upr.manager.*;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.*;
import com.guzi.upr.model.dto.TenantInsertDTO;
import com.guzi.upr.model.dto.TenantMenuUpdateDTO;
import com.guzi.upr.model.dto.TenantPageDTO;
import com.guzi.upr.model.dto.TenantUpdateDTO;
import com.guzi.upr.model.exception.BizException;
import com.guzi.upr.model.vo.TenantPageVO;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.util.ExecSqlUtil;
import com.guzi.upr.util.GlobalPropertiesUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.guzi.upr.model.contants.CommonConstants.ENABLE;
import static com.guzi.upr.model.contants.CommonConstants.ROOT_PARENT_ID;
import static com.guzi.upr.model.exception.enums.AdminErrorEnum.TENANT_REPEAT;


/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class TenantService {

    @Autowired
    private TenantManager tenantManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UserRoleManager userRoleManager;

    @Autowired
    private DepartmentManager departmentManager;

    @Autowired
    private MenuManager menuManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountUserManager accountUserManager;

    /**
     * 租户条件分页
     * @param dto
     * @return
     */
    public PageResult listPage(TenantPageDTO dto) {

        IPage<Tenant> page = tenantManager.listPage(dto);

        List<TenantPageVO> result = page.getRecords().stream().map(e -> {
            TenantPageVO vo = new TenantPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 租户新增
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(TenantInsertDTO dto) {
        // 查重
        Tenant one = tenantManager.getByTenantNameOrTenantCode(dto.getTenantName(), dto.getTenantCode());
        if (one != null) {
            throw new BizException(TENANT_REPEAT);
        }

        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenantManager.save(tenant);

        // 执行sql语句创建新租户的数据库表
        ExecSqlUtil.execSql(SqlStatement.CREATE_TENANT, Collections.singletonMap(SqlParam.DATABASE, dto.getTenantCode()));

        // 新建用户租户
        AccountUser accountUser = accountUserManager.getByPhone(dto.getContactPhone());
        if (accountUser == null) {
            accountUser = new AccountUser();
            accountUser.setPhone(dto.getContactPhone());
            accountUser.setPassword(passwordEncoder.encode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultPassword()));
            accountUser.setTenantData(dto.getTenantCode());
            accountUser.setLastLoginTenantCode(dto.getTenantCode());
            accountUserManager.save(accountUser);
        } else {
            List<String> split = StrUtil.split(accountUser.getTenantData(), ",");
            split.add(dto.getTenantCode());
            String tenantData = String.join(",", split);
            accountUser.setTenantData(tenantData);
            accountUserManager.updateById(accountUser);
        }

        // 设置要操作的租户数据库
        SecurityUtil.setOperateTenantCode(dto.getTenantCode());

        // 新建部门
        Department department = new Department();
        department.setDepartmentName(dto.getTenantName());
        department.setParentId(ROOT_PARENT_ID);
        department.setSort(1);
        departmentManager.save(department);

        // 新建角色
        Role role = new Role();
        role.setRoleName("超级管理员");
        roleManager.save(role);

        // 新建用户
        User user = new User();
        user.setAccountUserId(accountUser.getAccountUserId());
        user.setPhone(dto.getContactPhone());
        user.setRealName(dto.getContactUser());
        user.setEnable(ENABLE);
        user.setDepartmentId(department.getDepartmentId());
        user.setGender(1);
        userManager.save(user);

        // 关联用户角色
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getRoleId());
        userRole.setUserId(user.getUserId());
        userRoleManager.save(userRole);

        SecurityUtil.clearOperateTenantCode();

    }

    /**
     * 租户更新
     * @param dto
     */
    public void updateOne(TenantUpdateDTO dto) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenantManager.updateById(tenant);
    }

    /**
     * 租户删除
     * @param tenantId
     */
    public void removeOne(Long tenantId) {
        tenantManager.removeById(tenantId);
    }

    /**
     * 租户菜单更新
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(TenantMenuUpdateDTO dto) {
        Tenant tenant = tenantManager.getById(dto.getTenantId());
        if (tenant.getTenantCode().equals(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb())) {
            return;
        }

        List<Long> menuIds = dto.getMenuIds();
        List<Menu> menus = menuManager.listByIds(menuIds);

        SecurityUtil.setOperateTenantCode(tenant.getTenantCode());

        // 先清空菜单，再分配新的菜单
        menuManager.removeAll();
        menuManager.saveBatch(menus);

        // 处理角色菜单数据
        List<Role> roles = roleManager.list();
        roles.forEach(e -> {
            // 如果是管理员
            if (Objects.equals(e.getRoleId(), 1L)) {
                roleMenuManager.removeRoleMenuByRoleId(1L);
                roleMenuManager.saveRoleMenu(1L, menuIds);
                return;
            }
            // 如果是其他角色
            List<RoleMenu> roleMenus = roleMenuManager.listByRoleId(e.getRoleId());
            List<Long> oldMenuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
            Set<Long> resultMenuIds = CollUtil.intersectionDistinct(menuIds, oldMenuIds);
            roleMenuManager.removeRoleMenuByRoleId(e.getRoleId());
            roleMenuManager.saveRoleMenu(e.getRoleId(), resultMenuIds);
        });

        SecurityUtil.clearOperateTenantCode();
    }

    /**
     * 租户菜单列表
     * @param tenantId
     * @return
     */
    public List<Long> listMenu(Long tenantId) {
        Tenant tenant = tenantManager.getById(tenantId);

        SecurityUtil.setOperateTenantCode(tenant.getTenantCode());
        List<Menu> list = menuManager.list();
        SecurityUtil.clearOperateTenantCode();

        return list.stream().map(Menu::getMenuId).collect(Collectors.toList());
    }
}
