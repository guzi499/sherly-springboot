package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.RoleMenuMapper;
import com.guzi.upr.model.admin.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/6
 */
@Service
public class RoleMenuManager extends ServiceImpl<RoleMenuMapper, RoleMenu> {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 根据菜单id删除角色菜单表数据
     *
     * @param menuId
     */
    public void removeRoleMenuByMenuId(Long menuId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getMenuId, menuId);
        this.remove(wrapper);
    }

    /**
     * 根据角色id删除角色菜单数据
     *
     * @param roleId
     */
    public void removeRoleMenuByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        this.remove(wrapper);
    }

    /**
     * 保存角色菜单数据
     *
     * @param roleId
     * @param menuIds
     */
    public void saveRoleMenu(Long roleId, List<Long> menuIds) {
        roleMenuMapper.saveRoleMenu(roleId, menuIds);
    }

    /**
     * 根据角色id查询角色菜单数据
     *
     * @param roleId
     * @return
     */
    public List<RoleMenu> listByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        return this.list(wrapper);
    }

    /**
     * 根据角色id查询角色菜单数据
     *
     * @param roleIds
     * @return
     */
    public List<RoleMenu> listByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RoleMenu::getRoleId, roleIds);
        return this.list(wrapper);
    }
}
