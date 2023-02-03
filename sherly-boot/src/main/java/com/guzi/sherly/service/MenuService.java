package com.guzi.sherly.service;

import com.guzi.sherly.admin.menu.dao.MenuDao;
import com.guzi.sherly.admin.menu.dto.MenuInsertDTO;
import com.guzi.sherly.admin.menu.dto.MenuUpdateDTO;
import com.guzi.sherly.admin.menu.model.MenuDO;
import com.guzi.sherly.admin.menu.vo.MenuVO;
import com.guzi.sherly.admin.role.dao.RoleMenuDao;
import com.guzi.sherly.common.exception.BizException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.common.contants.CommonConstants.ROOT_PARENT_ID;
import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.*;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class MenuService {

    @Resource
    private MenuDao menuDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    /**
     * 查询菜单树
     * @return
     */
    public List<MenuVO> listTree() {
        List<MenuDO> list = menuDao.list();

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
     * @param dto
     */
    public void saveOne(MenuInsertDTO dto) {
        MenuDO menuDO = new MenuDO();
        BeanUtils.copyProperties(dto, menuDO);
        menuDao.save(menuDO);
    }

    /**
     * 菜单删除
     * @param menuId
     */
    public void removeOne(Long menuId) {
        if (roleMenuDao.countByMenuId(menuId) > 0) {
            throw new BizException(MENU_BOUND_ROLE);
        }
        if (menuDao.countChildrenByMenuId(menuId) > 0) {
            throw new BizException(MENU_HAS_CHILDREN);
        }
        menuDao.removeById(menuId);
    }

    /**
     * 菜单更新
     * @param dto
     */
    public void updateOne(MenuUpdateDTO dto) {
        if (Objects.equals(dto.getParentId(), dto.getMenuId())) {
            throw new BizException(MENU_PARENT_SELF);
        }
        MenuDO menuDO = new MenuDO();
        BeanUtils.copyProperties(dto, menuDO);
        menuDao.updateById(menuDO);
    }

}
