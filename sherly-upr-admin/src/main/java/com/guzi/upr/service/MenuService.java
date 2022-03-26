package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guzi.upr.manager.MenuManager;
import com.guzi.upr.model.admin.Menu;
import com.guzi.upr.model.dto.MenuInsertDTO;
import com.guzi.upr.model.dto.MenuListTreeQueryDTO;
import com.guzi.upr.model.dto.MenuParentDTO;
import com.guzi.upr.model.dto.MenuUpdateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource
    private MenuManager menuManager;

    /**
     * 查询菜单树
     *
     * @param dto
     * @return
     */
    public List<MenuParentDTO> listTree(MenuListTreeQueryDTO dto) {
        // 缓存
        if (dto.getParentId() == null || dto.getParentId() == 0) {
            // 所有菜单对象
            List<MenuParentDTO> menuList = menuManager.list(new LambdaQueryWrapper<Menu>()).stream().map(
                    e -> {
                        MenuParentDTO menuParentDTO = new MenuParentDTO();
                        BeanUtils.copyProperties(e, menuParentDTO);
                        return menuParentDTO;
                    }
            ).collect(Collectors.toList());
            // 子节点
            // 获取子集
            return menuList.stream().filter(
                            e -> e.getParentId() == 0
                    ).sorted(Comparator.comparing(MenuParentDTO::getSort))
                    .peek(e -> {
                        MenuParentDTO menuParentDto = getListTree(e, menuList);
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    private MenuParentDTO getListTree(MenuParentDTO parent, List<MenuParentDTO> childList) {
        List<MenuParentDTO> collect = childList.stream().filter(child -> child.getParentId().equals(parent.getMenuId())).collect(Collectors.toList());
        parent.setChildren(collect);

        for (MenuParentDTO child : collect) {
            getListTree(child, childList);
        }
        return parent;
    }

    /**
     * 菜单新增
     *
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
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
