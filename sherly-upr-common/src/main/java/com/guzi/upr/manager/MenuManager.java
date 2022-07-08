package com.guzi.upr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.MenuMapper;
import com.guzi.upr.model.admin.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class MenuManager extends ServiceImpl<MenuMapper, Menu> {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据菜单id获取子菜单数量
     *
     * @param menuId
     * @return
     */
    public Long countChildrenByMenuId(Long menuId) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, menuId);
        return this.count(wrapper);
    }
}
