package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.MenuMapper;
import com.guzi.upr.model.admin.Menu;
import com.guzi.upr.model.dto.MenuInsertDTO;
import com.guzi.upr.model.dto.MenuListTreeQueryDTO;
import com.guzi.upr.model.dto.MenuParentDto;
import com.guzi.upr.model.dto.MenuUpdateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    /**
     * 查询菜单树
     *
     * @param dto
     * @return
     */
    public List<MenuParentDto> listTree(MenuListTreeQueryDTO dto) {
        // 缓存
        if (dto.getParentId() == null || dto.getParentId() == 0) {
            // 父节点
            List<Menu> parentList = list(new LambdaQueryWrapper<Menu>().isNull(Menu::getParentId).eq(Menu::getDeleted, 0));
            // 子节点
            List<Menu> childList = list(new LambdaQueryWrapper<Menu>().isNotNull(Menu::getParentId).eq(Menu::getDeleted, 0));

            List<MenuParentDto> menuParentDtos = new ArrayList<>();
            for (Menu menu : parentList) {
                // 获取子集
                MenuParentDto menuParent = new MenuParentDto();
                BeanUtils.copyProperties(menu, menuParent);
                MenuParentDto menuParentDto = getListTree(menuParent, childList);
                menuParentDtos.add(menuParentDto);
            }
            return menuParentDtos;
        }
        return null;
    }

    private MenuParentDto getListTree(MenuParentDto parent, List<Menu> childList) {
        List<MenuParentDto> collect = childList.stream().filter(child -> child.getParentId().equals(parent.getMenuId())).map(item -> {
            MenuParentDto menuParentDto1 = new MenuParentDto();
            BeanUtils.copyProperties(item, menuParentDto1);
            return menuParentDto1;
        }).collect(Collectors.toList());
        parent.setChildren(collect);
        // 填充子集 fixme 排除已选子集
        for (MenuParentDto child : collect) {
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
        List<MenuParentDto> menuList = dto.getMenuList();
        // 传递节点
        menuList.forEach(
                menu -> {
                    // 设置父节点属性
                    if (menu.getMenuId() == null) {
                        save(menu);
                    }
                    if (menu.getChildren() == null) {
                        return;
                    }
                    List<Menu> children = menu.getChildren().stream().peek(item -> item.setParentId(menu.getMenuId())).collect(Collectors.toList());
                    // 设置子节点属性
                    saveBatch(children);
                    // 子节点分支
                    saveChildList(children);
                }
        );
    }

    /**
     * 新增菜单处理子集
     *
     * @param children 根一级下子集
     */
    private void saveChildList(List<? extends Menu> children) {
        List<Menu> childList = new ArrayList<>();
        for (Menu child : children) {
            // 设置parentID
            List<MenuParentDto> children1 = ((MenuParentDto) child).getChildren().stream().peek(item -> item.setParentId(child.getMenuId())).collect(Collectors.toList());
            childList.addAll(children1);
            // 保存子级
            saveBatch(childList);
            // 下一级
            for (MenuParentDto menuParentDto : children1) {
                if (menuParentDto.getChildren() != null && !menuParentDto.getChildren().isEmpty()) {
                    saveChildList(children1);
                }
            }
        }
    }

    /**
     * 菜单删除
     *
     * @param id
     */
    public void removeOne(Long id) {
        Menu byId = getById(id);
        byId.setDeleted(1);
        updateById(byId);
    }

    /**
     * 菜单更新
     *
     * @param dto
     */
    public void updateOne(MenuUpdateDTO dto) {
        List<MenuParentDto> menuList = dto.getMenuList();
        ArrayList<Menu> menus = new ArrayList<>();
        menuList.forEach(
                menu -> {
                    List<MenuParentDto> children = menu.getChildren();
                    menus.addAll(children);
                    updateChildrenList(menus, children);
                }
        );
        // 统一更新
        menus.addAll(menuList);
        updateBatchById(menus);

    }

    /**
     * 更新菜单方法
     *
     * @param menus    菜单
     * @param children 子集
     */
    private void updateChildrenList(ArrayList<Menu> menus, List<MenuParentDto> children) {
        for (MenuParentDto child : children) {
            if (null == child.getChildren() && child.getChildren().isEmpty()) {
                continue;
            }
            menus.addAll(children);
            // 下一级
            List<MenuParentDto> children1 = child.getChildren();
            updateChildrenList(menus, children1);
        }
    }
}
