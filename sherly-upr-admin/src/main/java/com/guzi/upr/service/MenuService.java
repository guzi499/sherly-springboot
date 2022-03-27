package com.guzi.upr.service;

import com.guzi.upr.manager.MenuManager;
import com.guzi.upr.model.admin.Menu;
import com.guzi.upr.model.dto.MenuInsertDTO;
import com.guzi.upr.model.dto.MenuUpdateDTO;
import com.guzi.upr.model.vo.MenuParentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class MenuService {

    @Autowired
    private MenuManager menuManager;

    /**
     * 查询菜单树
     *
     * @return
     */
    public List<MenuParentVO> listTree() {
        // 缓存
        // 所有菜单对象
        List<MenuParentVO> menuList = menuManager.list().stream().map(
                e -> {
                    MenuParentVO menuParentDTO = new MenuParentVO();
                    BeanUtils.copyProperties(e, menuParentDTO);
                    return menuParentDTO;
                }
        ).collect(Collectors.toList());
        // 子节点
        // 获取子集
        return menuList.stream().filter(
                        e -> e.getParentId() == 0
                ).sorted(Comparator.comparing(MenuParentVO::getSort))
                .peek(e -> {
                    e.setChildren(getListTree(e, menuList));
                })
                .collect(Collectors.toList());


    }

    /**
     * 获取根节点下所有元素
     *
     * @param parent
     * @param childList
     * @return
     */
    private List<MenuParentVO> getListTree(MenuParentVO parent, List<MenuParentVO> childList) {
        return childList.stream()
                .filter(child -> child.getParentId().equals(parent.getMenuId()))
                .peek(e -> e.setChildren(getListTree(e, childList)))
                .collect(Collectors.toList());
    }

    /**
     * 菜单新增
     *
     * @param dto
     */
    public void saveOne(MenuInsertDTO dto) {
        // 参数检查
        Menu menu = new Menu();
        BeanUtils.copyProperties(dto, menu);
        menuManager.save(menu);
        // 传递节点
    }

    /**
     * 菜单删除
     *
     * @param id
     */
    public void removeOne(Long id) {
        menuManager.removeById(id);
    }

    /**
     * 菜单更新
     *
     * @param dto
     */
    public void updateOne(MenuUpdateDTO dto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(dto, menu);
        menuManager.updateById(menu);

    }

}
