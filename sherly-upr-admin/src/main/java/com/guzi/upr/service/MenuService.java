package com.guzi.upr.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guzi.upr.mapper.admin.MenuMapper;
import com.guzi.upr.model.admin.Menu;
import com.guzi.upr.model.dto.MenuListTreeQueryDTO;
import com.guzi.upr.model.dto.MenuInsertDTO;
import com.guzi.upr.model.dto.MenuUpdateDTO;
import com.guzi.upr.model.vo.MenuListTreeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    /**
     * 查询菜单树
     * @param dto
     * @return
     */
    public List<MenuListTreeVO> listTree(MenuListTreeQueryDTO dto) {

        // todo 在这里写您的代码

        return null;
    }

    /**
     * 菜单新增
     * @param dto
     */
    public void saveOne(MenuInsertDTO dto) {

        // todo 在这里写您的代码

    }

    /**
     * 菜单删除
     * @param id
     */
    public void removeOne(Long id) {

        // todo 在这里写代码

    }

    /**
     * 菜单更新
     * @param dto
     */
    public void updateOne(MenuUpdateDTO dto) {

        // todo 在这里写代码

    }
}
