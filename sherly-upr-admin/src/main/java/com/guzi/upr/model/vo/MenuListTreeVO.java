package com.guzi.upr.model.vo;

import com.guzi.upr.model.dto.MenuParentDTO;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Data
public class MenuListTreeVO {
    private List<MenuParentDTO> menuList;
}
