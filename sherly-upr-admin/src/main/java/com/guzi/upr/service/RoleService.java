package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.RoleMenuManager;
import com.guzi.upr.manager.RolePermissionManager;
import com.guzi.upr.manager.UserRoleManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.RoleMenu;
import com.guzi.upr.model.admin.RolePermission;
import com.guzi.upr.model.dto.RoleInsertDTO;
import com.guzi.upr.model.dto.RolePageDTO;
import com.guzi.upr.model.dto.RoleUpdateDTO;
import com.guzi.upr.model.vo.RolePageVO;
import com.guzi.upr.model.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class RoleService {
    @Autowired
    private RoleManager roleManager;

    @Autowired
    private RolePermissionManager rolePermissionManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private UserRoleManager userRoleManager;

    /**
     * 角色分页
     *
     * @param dto
     * @return
     */
    public PageResult listPage(RolePageDTO dto) {
        // 分页查询
        IPage<Role> page = roleManager.listPage(dto.getPage(), dto.getRoleName());

        // 对象转换成vo类型
        List<RolePageVO> result = page.getRecords().stream().map(e -> {
            RolePageVO vo = new RolePageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 角色详情
     *
     * @param roleId
     * @return
     */
    public RoleVO getOne(Long roleId) {
        Role role = roleManager.getById(roleId);

        // 查询菜单和权限
        List<RoleMenu> roleMenus = roleMenuManager.listByRoleId(roleId);
        List<RolePermission> rolePermissions = rolePermissionManager.listByRoleId(roleId);
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        List<Long> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        // 组装vo
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);
        vo.setMenuIds(menuIds);
        vo.setPermissionsIds(permissionIds);

        return vo;
    }

    /**
     * 角色新增
     *
     * @param dto
     */
    public void saveOne(RoleInsertDTO dto) {
        // 去重
        Role one = roleManager.getByRoleName(dto.getRoleName());
        if (one != null) {
            throw new BizException(ResultAdminEnum.ROLE_REPEAT);
        }

        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        roleManager.save(role);
    }

    /**
     * 角色更新
     *
     * @param dto
     */
    public void updateOne(RoleUpdateDTO dto) {
        // 去重
        Role one = roleManager.getByRoleName(dto.getRoleName());
        if (one != null && !one.getRoleId().equals(dto.getRoleId())) {
            throw new BizException(ResultAdminEnum.ROLE_REPEAT);
        }

        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        roleManager.updateById(role);

        // 先全部删除角色菜单、角色权限数据
        roleMenuManager.removeRoleMenuByRoleId(dto.getRoleId());
        rolePermissionManager.removeRolePermissionByRoleId(dto.getRoleId());

        // 再保存角色菜单、角色权限数据
        roleMenuManager.saveRoleMenu(dto.getRoleId(), dto.getMenuIds());
        rolePermissionManager.saveRolePermission(dto.getRoleId(), dto.getPermissionsIds());
    }

    /**
     * 角色删除
     *
     * @param roleId
     */
    public void removeOne(Long roleId) {
        roleManager.removeById(roleId);

        // 删除角色菜单、角色权限、用户角色数据
        roleMenuManager.removeRoleMenuByRoleId(roleId);
        rolePermissionManager.removeRolePermissionByRoleId(roleId);
        userRoleManager.removeUserRoleByRoleId(roleId);
    }
}
