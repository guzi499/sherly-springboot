package com.guzi.sherly.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.constants.SqlParam;
import com.guzi.sherly.constants.SqlStatement;
import com.guzi.sherly.dao.*;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.admin.*;
import com.guzi.sherly.model.dto.TenantInsertDTO;
import com.guzi.sherly.model.dto.TenantMenuUpdateDTO;
import com.guzi.sherly.model.dto.TenantPageDTO;
import com.guzi.sherly.model.dto.TenantUpdateDTO;
import com.guzi.sherly.model.eo.TenantEO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.TenantPageVO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import com.guzi.sherly.util.ExecSqlUtil;
import com.guzi.sherly.util.GlobalPropertiesUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.ENABLE;
import static com.guzi.sherly.model.contants.CommonConstants.ROOT_PARENT_ID;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.DELETE_TENANT_ERROR;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.TENANT_REPEAT;


/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class TenantService {

    @Resource
    private TenantDao tenantDao;

    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private DepartmentDao departmentDao;

    @Resource
    private MenuDao menuDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AccountUserDao accountUserDao;

    /**
     * 租户条件分页
     * @param dto
     * @return
     */
    public PageResult<TenantPageVO> listPage(TenantPageDTO dto) {

        IPage<Tenant> page = tenantDao.listPage(dto);

        List<TenantPageVO> result = page.getRecords().stream().map(e -> {
            TenantPageVO vo = new TenantPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }

    /**
     * 租户新增
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(TenantInsertDTO dto) {
        // 查重
        Tenant one = tenantDao.getByTenantNameOrTenantCode(dto.getTenantName(), dto.getTenantCode());
        if (one != null) {
            throw new BizException(TENANT_REPEAT);
        }

        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenantDao.save(tenant);

        // 执行sql语句创建新租户的数据库表
        ExecSqlUtil.execSql(SqlStatement.CREATE_TENANT, Collections.singletonMap(SqlParam.DATABASE, dto.getTenantCode()));

        // 新建用户租户
        AccountUser accountUser = accountUserDao.getByPhone(dto.getContactPhone());
        if (accountUser == null) {
            accountUser = new AccountUser();
            accountUser.setPhone(dto.getContactPhone());
            accountUser.setPassword(passwordEncoder.encode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultPassword()));
            accountUser.setTenantData(dto.getTenantCode());
            accountUser.setLastLoginTenantCode(dto.getTenantCode());
            accountUserDao.save(accountUser);
        } else {
            List<String> split = StrUtil.split(accountUser.getTenantData(), ",");
            split.add(dto.getTenantCode());
            split = split.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
            String tenantData = String.join(",", split);
            accountUser.setTenantData(tenantData);
            accountUserDao.updateById(accountUser);
        }

        // 设置要操作的租户数据库
        SecurityUtil.setOperateTenantCode(dto.getTenantCode());

        // 新建部门
        Department department = new Department();
        department.setDepartmentName(dto.getTenantName());
        department.setParentId(ROOT_PARENT_ID);
        department.setSort(1);
        departmentDao.save(department);

        // 新建角色
        Role role = new Role();
        role.setRoleName("超级管理员");
        roleDao.save(role);

        // 新建用户
        User user = new User();
        user.setAccountUserId(accountUser.getAccountUserId());
        user.setPhone(dto.getContactPhone());
        user.setRealName(dto.getContactUser());
        user.setEnable(ENABLE);
        user.setDepartmentId(department.getDepartmentId());
        user.setGender(1);
        userDao.save(user);

        // 关联用户角色
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getRoleId());
        userRole.setUserId(user.getUserId());
        userRoleDao.save(userRole);

        SecurityUtil.clearOperateTenantCode();

    }

    /**
     * 租户更新
     * @param dto
     */
    public void updateOne(TenantUpdateDTO dto) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto, tenant);
        tenantDao.updateById(tenant);
    }

    /**
     * 租户删除
     * @param tenantId
     */
    public void removeOne(Long tenantId) {
        if (Objects.equals(tenantId, 1L)) {
            throw new BizException(DELETE_TENANT_ERROR);
        }
        tenantDao.removeById(tenantId);
    }

    /**
     * 租户菜单更新
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(TenantMenuUpdateDTO dto) {
        Tenant tenant = tenantDao.getById(dto.getTenantId());
        if (tenant.getTenantCode().equals(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb())) {
            return;
        }

        List<Long> menuIds = dto.getMenuIds();
        List<Menu> menus = menuDao.listByIds(menuIds);

        SecurityUtil.setOperateTenantCode(tenant.getTenantCode());

        // 先清空菜单，再分配新的菜单
        menuDao.removeAll();
        menuDao.saveBatch(menus);

        // 处理角色菜单数据
        List<Role> roles = roleDao.list();
        roles.forEach(e -> {
            // 如果是管理员
            if (Objects.equals(e.getRoleId(), 1L)) {
                roleMenuDao.removeByRoleId(1L);
                roleMenuDao.saveRoleMenu(1L, menuIds);
                return;
            }
            // 如果是其他角色
            List<RoleMenu> roleMenus = roleMenuDao.listByRoleId(e.getRoleId());
            List<Long> oldMenuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
            Set<Long> resultMenuIds = CollUtil.intersectionDistinct(menuIds, oldMenuIds);
            roleMenuDao.removeByRoleId(e.getRoleId());
            roleMenuDao.saveRoleMenu(e.getRoleId(), resultMenuIds);
        });

        SecurityUtil.clearOperateTenantCode();
    }

    /**
     * 租户菜单列表
     * @param tenantId
     * @return
     */
    public List<Long> listMenu(Long tenantId) {
        Tenant tenant = tenantDao.getById(tenantId);

        SecurityUtil.setOperateTenantCode(tenant.getTenantCode());
        List<Menu> list = menuDao.list();
        SecurityUtil.clearOperateTenantCode();

        return list.stream().map(Menu::getMenuId).collect(Collectors.toList());
    }

    /**
     * 租户导出
     * @param response
     */
    @SneakyThrows
    public void listExport(HttpServletResponse response) {
        List<Tenant> list = tenantDao.list();

        List<TenantEO> result = list.stream().map(e -> {
            TenantEO tenantEO = new TenantEO();
            BeanUtils.copyProperties(e, tenantEO);
            return tenantEO;
        }).collect(Collectors.toList());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("租户列表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), TenantEO.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("租户列表")
                .doWrite(result);
    }


}
