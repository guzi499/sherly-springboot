package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.RolePermissionManager;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.RolePermission;
import com.guzi.upr.model.dto.RoleInsertDTO;
import com.guzi.upr.model.dto.RoleUpdateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class RoleService {
    @Resource
    private RoleManager roleManager;
    @Resource
    private RolePermissionManager rolePermissionManager;

    public List<Role> list() {
        List<Role> list = roleManager.list(new LambdaQueryWrapper<Role>().orderByAsc(Role::getRoleId));
        return list;
    }

    public void saveOne(RoleInsertDTO dto) {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        roleManager.save(role);
        // 权限
        saveRolePermission(role, dto.getPermissions());
    }

    public void removeOne(Long id) {
        rolePermissionManager.removeById(id);
    }

    public void updateOne(RoleUpdateDTO dto) {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        boolean b = roleManager.updateById(role);
        // 更新权限
        rolePermissionManager.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, role.getRoleId()));
        saveRolePermission(role, dto.getPermissions());
    }

    private void saveRolePermission(Role role, List<Long> permissions2) {
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Long permission : permissions2) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getRoleId());
            rolePermission.setPermissionId(permission);
            rolePermissions.add(rolePermission);
        }
        if (!rolePermissions.isEmpty()) {
            rolePermissionManager.saveBatch(rolePermissions);
        }
    }
}
