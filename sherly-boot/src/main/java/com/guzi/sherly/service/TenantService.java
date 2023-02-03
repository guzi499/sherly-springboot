package com.guzi.sherly.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.admin.accountuser.dao.AccountUserDao;
import com.guzi.sherly.admin.accountuser.model.AccountUserDO;
import com.guzi.sherly.admin.department.dao.DepartmentDao;
import com.guzi.sherly.admin.department.model.DepartmentDO;
import com.guzi.sherly.admin.menu.dao.MenuDao;
import com.guzi.sherly.admin.menu.model.MenuDO;
import com.guzi.sherly.admin.role.dao.RoleDao;
import com.guzi.sherly.admin.role.dao.RoleMenuDao;
import com.guzi.sherly.admin.role.model.RoleDO;
import com.guzi.sherly.admin.role.model.RoleMenuDO;
import com.guzi.sherly.admin.tenant.dao.TenantDao;
import com.guzi.sherly.admin.tenant.dto.TenantInsertDTO;
import com.guzi.sherly.admin.tenant.dto.TenantMenuUpdateDTO;
import com.guzi.sherly.admin.tenant.dto.TenantPageDTO;
import com.guzi.sherly.admin.tenant.dto.TenantUpdateDTO;
import com.guzi.sherly.admin.tenant.eo.TenantEO;
import com.guzi.sherly.admin.tenant.model.TenantDO;
import com.guzi.sherly.admin.tenant.vo.TenantPageVO;
import com.guzi.sherly.admin.user.dao.UserDao;
import com.guzi.sherly.admin.user.dao.UserRoleDao;
import com.guzi.sherly.admin.user.model.UserDO;
import com.guzi.sherly.admin.user.model.UserRoleDO;
import com.guzi.sherly.common.constants.SqlParam;
import com.guzi.sherly.common.constants.SqlStatement;
import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.common.util.ExecSqlUtil;
import com.guzi.sherly.common.util.GlobalPropertiesUtil;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static com.guzi.sherly.common.contants.CommonConstants.ENABLE;
import static com.guzi.sherly.common.contants.CommonConstants.ROOT_PARENT_ID;
import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.DELETE_TENANT_ERROR;
import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.TENANT_REPEAT;


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

        IPage<TenantDO> page = tenantDao.listPage(dto);

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
        TenantDO one = tenantDao.getByTenantNameOrTenantCode(dto.getTenantName(), dto.getTenantCode());
        if (one != null) {
            throw new BizException(TENANT_REPEAT);
        }

        TenantDO tenantDO = new TenantDO();
        BeanUtils.copyProperties(dto, tenantDO);
        tenantDao.save(tenantDO);

        // 执行sql语句创建新租户的数据库表
        ExecSqlUtil.execSql(SqlStatement.CREATE_TENANT, Collections.singletonMap(SqlParam.DATABASE, dto.getTenantCode()));

        // 新建用户租户
        AccountUserDO accountUserDO = accountUserDao.getByPhone(dto.getContactPhone());
        if (accountUserDO == null) {
            accountUserDO = new AccountUserDO();
            accountUserDO.setPhone(dto.getContactPhone());
            accountUserDO.setPassword(passwordEncoder.encode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultPassword()));
            accountUserDO.setTenantData(dto.getTenantCode());
            accountUserDO.setLastLoginTenantCode(dto.getTenantCode());
            accountUserDao.save(accountUserDO);
        } else {
            List<String> split = StrUtil.split(accountUserDO.getTenantData(), ",");
            split.add(dto.getTenantCode());
            split = split.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
            String tenantData = String.join(",", split);
            accountUserDO.setTenantData(tenantData);
            accountUserDao.updateById(accountUserDO);
        }

        // 设置要操作的租户数据库
        SecurityUtil.setOperateTenantCode(dto.getTenantCode());

        // 新建部门
        DepartmentDO departmentDO = new DepartmentDO();
        departmentDO.setDepartmentName(dto.getTenantName());
        departmentDO.setParentId(ROOT_PARENT_ID);
        departmentDO.setSort(1);
        departmentDao.save(departmentDO);

        // 新建角色
        RoleDO roleDO = new RoleDO();
        roleDO.setRoleName("超级管理员");
        roleDao.save(roleDO);

        // 新建用户
        UserDO userDO = new UserDO();
        userDO.setAccountUserId(accountUserDO.getAccountUserId());
        userDO.setPhone(dto.getContactPhone());
        userDO.setRealName(dto.getContactUser());
        userDO.setEnable(ENABLE);
        userDO.setDepartmentId(departmentDO.getDepartmentId());
        userDO.setGender(1);
        userDO.setLastLoginTime(new Date());
        userDao.save(userDO);

        // 关联用户角色
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setRoleId(roleDO.getRoleId());
        userRoleDO.setUserId(userDO.getUserId());
        userRoleDao.save(userRoleDO);

        SecurityUtil.clearOperateTenantCode();

    }

    /**
     * 租户更新
     * @param dto
     */
    public void updateOne(TenantUpdateDTO dto) {
        TenantDO tenantDO = new TenantDO();
        BeanUtils.copyProperties(dto, tenantDO);
        tenantDao.updateById(tenantDO);
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
        TenantDO tenantDO = tenantDao.getById(dto.getTenantId());
        if (tenantDO.getTenantCode().equals(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb())) {
            return;
        }

        List<Long> menuIds = dto.getMenuIds();
        List<MenuDO> menuDOs = menuDao.listByIds(menuIds);

        SecurityUtil.setOperateTenantCode(tenantDO.getTenantCode());

        // 先清空菜单，再分配新的菜单
        menuDao.removeAll();
        menuDao.saveBatch(menuDOs);

        // 处理角色菜单数据
        List<RoleDO> roleDOs = roleDao.list();
        roleDOs.forEach(e -> {
            // 如果是管理员
            if (Objects.equals(e.getRoleId(), 1L)) {
                roleMenuDao.removeByRoleId(1L);
                roleMenuDao.saveRoleMenu(1L, menuIds);
                return;
            }
            // 如果是其他角色
            List<RoleMenuDO> roleMenuDOs = roleMenuDao.listByRoleId(e.getRoleId());
            List<Long> oldMenuIds = roleMenuDOs.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toList());
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
        TenantDO tenantDO = tenantDao.getById(tenantId);

        SecurityUtil.setOperateTenantCode(tenantDO.getTenantCode());
        List<MenuDO> list = menuDao.list();
        SecurityUtil.clearOperateTenantCode();

        return list.stream().map(MenuDO::getMenuId).collect(Collectors.toList());
    }

    /**
     * 租户导出
     * @param response
     */
    @SneakyThrows
    public void listExport(HttpServletResponse response) {
        List<TenantDO> list = tenantDao.list();

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
