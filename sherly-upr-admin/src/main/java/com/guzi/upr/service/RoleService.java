package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.RolePermissionManager;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.dto.RoleInsertDTO;
import com.guzi.upr.model.dto.RoleUpdateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class RoleService {
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private RolePermissionManager rolePermissionManager;

    /**
     * 角色列表
     *
     * @return
     */
    public List<Role> list() {
        // TODO: 2022/3/27 分页
        return roleManager.list();
    }

    /**
     * 保存角色
     *
     * @param dto
     */
    public void saveOne(RoleInsertDTO dto) {
        // 角色名重复
        if (roleManager.count(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, dto.getRoleName())) > 0) {
            return;
        }
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        roleManager.save(role);
        // 添加权限
        rolePermissionManager.updatePermission(role, dto.getPermissions());
    }

    /**
     * 移除角色
     *
     * @param id
     */
    public void removeOne(Long id) {
        rolePermissionManager.removeById(id);
    }

    /**
     * 更新角色信息
     *
     * @param dto
     */
    public void updateOne(RoleUpdateDTO dto) {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        boolean b = roleManager.updateById(role);
        // 更新权限
        rolePermissionManager.removePermissionByRoleId(dto.getRoleId());
        rolePermissionManager.updatePermission(role, dto.getPermissions());

    }


}
