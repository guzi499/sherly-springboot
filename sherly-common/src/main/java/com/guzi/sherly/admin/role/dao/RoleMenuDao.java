package com.guzi.sherly.admin.role.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.admin.role.mapper.RoleMenuMapper;
import com.guzi.sherly.admin.role.model.RoleMenuDO;
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
public class RoleMenuDao extends SherlyServiceImpl<RoleMenuMapper, RoleMenuDO> {

    /**
     * 根据角色id删除角色菜单数据
     * @param roleId
     */
    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenuDO::getRoleId, roleId);
        this.remove(wrapper);
    }

    /**
     * 角色菜单新增
     * @param roleId
     * @param menuIds
     */
    public void saveRoleMenu(Long roleId, Collection<Long> menuIds) {
        List<RoleMenuDO> list = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setRoleId(roleId);
            roleMenuDO.setMenuId(menuId);
            list.add(roleMenuDO);
        }
        this.saveBatch(list);
    }

    /**
     * 根据角色id查询角色菜单数据
     * @param roleId
     * @return
     */
    public List<RoleMenuDO> listByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenuDO::getRoleId, roleId);
        return this.list(wrapper);
    }

    /**
     * 根据角色ids查询角色菜单数据
     * @param roleIds
     * @return
     */
    public List<RoleMenuDO> listByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<RoleMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RoleMenuDO::getRoleId, roleIds);
        return this.list(wrapper);
    }

    /**
     * 根据菜单id查询角色菜单数量
     * @param menuId
     * @return
     */
    public Long countByMenuId(Long menuId) {
        LambdaQueryWrapper<RoleMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenuDO::getMenuId, menuId);
        return this.count(wrapper);
    }
}
