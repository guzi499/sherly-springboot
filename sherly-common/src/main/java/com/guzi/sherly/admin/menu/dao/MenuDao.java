package com.guzi.sherly.admin.menu.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.sherly.admin.menu.mapper.MenuMapper;
import com.guzi.sherly.admin.menu.model.Menu;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class MenuDao extends SherlyServiceImpl<MenuMapper, Menu> {

    /**
     * 根据菜单id查询子菜单数量
     * @param menuId
     * @return
     */
    public Long countChildrenByMenuId(Long menuId) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, menuId);
        return this.count(wrapper);
    }

    /**
     * 菜单清空
     */
    public void removeAll() {
        this.baseMapper.removeAll();
    }
}
