package com.guzi.upr.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.MenuMapper;
import com.guzi.upr.model.admin.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/25
 */
@Service
public class MenuManager extends ServiceImpl<MenuMapper, Menu> {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据角色ids获取菜单列表
     * @param roleIds
     * @return
     */
    public List<Menu> listByRoleIds(List<Long> roleIds) {
        return menuMapper.listByRoleIds(roleIds);
    }
}
