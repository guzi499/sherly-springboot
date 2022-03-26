package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.PermissionMapper;
import com.guzi.upr.model.admin.Permission;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class PermissionManager extends ServiceImpl<PermissionMapper, Permission> {

    /**
     * 权限名查重
     * @param permissionName
     * @param parentId
     * @return
     */
    public Long countByPermissionName(String permissionName, Long parentId) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPermissionName, permissionName)
                .eq(Permission::getParentId, parentId);
        return this.count(wrapper);
    }
}
