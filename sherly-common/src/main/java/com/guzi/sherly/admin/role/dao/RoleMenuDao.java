package com.guzi.sherly.admin.role.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.admin.role.mapper.RoleMenuMapper;
import com.guzi.sherly.admin.role.model.RoleMenu;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/6
 */
@Service
public class RoleMenuDao extends SherlyServiceImpl<RoleMenuMapper, RoleMenu> {

    /**
     * 根据角色id删除角色菜单数据
     * @param roleId
     */
    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        this.remove(wrapper);
    }

    /**
     * 角色菜单新增
     * @param roleId
     * @param menuIds
     */
    public void saveRoleMenu(Long roleId, Collection<Long> menuIds) {
        List<RoleMenu> list = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            list.add(roleMenu);
        }
        this.saveBatch(list);
    }

    /**
     * 根据角色id查询角色菜单数据
     * @param roleId
     * @return
     */
    public List<RoleMenu> listByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        return this.list(wrapper);
    }

    /**
     * 根据角色ids查询角色菜单数据
     * @param roleIds
     * @return
     */
    public List<RoleMenu> listByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RoleMenu::getRoleId, roleIds);
        return this.list(wrapper);
    }

    /**
     * 根据菜单id查询角色菜单数量
     * @param menuId
     * @return
     */
    public Long countByMenuId(Long menuId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getMenuId, menuId);
        return this.count(wrapper);
    }
}
