package com.guzi.upr.service;

import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.MenuManager;
import com.guzi.upr.manager.RoleMenuManager;
import com.guzi.upr.model.admin.Menu;
import com.guzi.upr.model.dto.MenuInsertDTO;
import com.guzi.upr.model.dto.MenuUpdateDTO;
import com.guzi.upr.model.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.upr.model.contants.CommonConstants.ROOT_PARENT_ID;
import static com.guzi.upr.constants.enums.ResultAdminEnum.*;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class MenuService {

    @Autowired
    private MenuManager menuManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    /**
     * 查询菜单树
     *
     * @return
     */
    public List<MenuVO> listTree() {
        List<Menu> list = menuManager.list();

        // 对象转换成vo类型
        List<MenuVO> all = list.stream().map(e -> {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(e, menuVO);
            return menuVO;
        }).sorted(Comparator.comparing(MenuVO::getSort)).collect(Collectors.toList());

        return all.stream().filter(e -> Objects.equals(e.getParentId(), ROOT_PARENT_ID))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

    /**
     * 递归拼装子结点
     *
     * @param parent
     * @param all
     * @return
     */
    private List<MenuVO> getChildren(MenuVO parent, List<MenuVO> all) {
        return all.stream()
                .filter(e -> Objects.equals(e.getParentId(), parent.getMenuId()))
                .peek(e -> e.setChildren(getChildren(e, all)))
                .collect(Collectors.toList());
    }

    /**
     * 菜单新增
     *
     * @param dto
     */
    public void saveOne(MenuInsertDTO dto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(dto, menu);
        menuManager.save(menu);
    }

    /**
     * 菜单删除
     *
     * @param menuId
     */
    public void removeOne(Long menuId) {
        if (roleMenuManager.countByMenuId(menuId) > 0) {
            throw new BizException(MENU_BOUND_ROLE);
        }
        if (menuManager.countChildrenByMenuId(menuId) > 0) {
            throw new BizException(MENU_HAS_CHILDREN);
        }
        menuManager.removeById(menuId);
    }

    /**
     * 菜单更新
     *
     * @param dto
     */
    public void updateOne(MenuUpdateDTO dto) {
        if (Objects.equals(dto.getParentId(), dto.getMenuId())) {
            throw new BizException(MENU_PARENT_SELF);
        }
        Menu menu = new Menu();
        BeanUtils.copyProperties(dto, menu);
        menuManager.updateById(menu);
    }

}
