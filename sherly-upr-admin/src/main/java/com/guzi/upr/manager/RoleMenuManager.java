package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.RoleMenuMapper;
import com.guzi.upr.model.admin.RoleMenu;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/4/6
 */
@Service
public class RoleMenuManager extends ServiceImpl<RoleMenuMapper, RoleMenu> {

    /**
     * 根据部门id删除角色部门表数据
     * @param menuId
     */
    public void removeRoleMenuByMenuId(Long menuId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getMenuId, menuId);
        this.remove(wrapper);
    }
}
