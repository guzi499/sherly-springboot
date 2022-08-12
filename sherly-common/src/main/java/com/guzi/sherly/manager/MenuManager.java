package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.sherly.mapper.admin.MenuMapper;
import com.guzi.sherly.model.admin.Menu;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class MenuManager extends ServiceImpl<MenuMapper, Menu> {

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
