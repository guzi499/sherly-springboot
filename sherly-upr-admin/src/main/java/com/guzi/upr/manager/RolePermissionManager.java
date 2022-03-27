package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.RolePermissionMapper;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.RolePermission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/26 19:01
 */
@Service
public class RolePermissionManager extends ServiceImpl<RolePermissionMapper, RolePermission> {
    /**
     * 更新权限
     *
     * @param role
     * @param permissions
     */
    public void updatePermission(Role role, List<Long> permissions) {
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Long permission : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getRoleId());
            rolePermission.setPermissionId(permission);
            rolePermissions.add(rolePermission);
        }
        if (!rolePermissions.isEmpty()) {
            saveBatch(rolePermissions);
        }
    }

    /**
     * 移除角色id对应权限id
     *
     * @param roleId
     */
    public void removePermissionByRoleId(Long roleId) {
        remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
    }
}
