package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.RolePermissionMapper;
import com.guzi.upr.model.admin.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 付东辉
 * @date 2022/3/26
 */
@Service
public class RolePermissionManager extends ServiceImpl<RolePermissionMapper, RolePermission> {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 根据角色id删除角色权限数据
     *
     * @param roleId
     */
    public void removeRolePermissionByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        this.remove(wrapper);
    }

    /**
     * 保存角色权限数据
     *
     * @param roleId
     * @param permissionsIds
     */
    public void saveRolePermission(Long roleId, List<Long> permissionsIds) {
        rolePermissionMapper.saveRolePermission(roleId, permissionsIds);
    }

    /**
     * 根据角色id查询角色权限数据
     *
     * @param roleId
     * @return
     */
    public List<RolePermission> listByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        return this.list(wrapper);
    }
}
