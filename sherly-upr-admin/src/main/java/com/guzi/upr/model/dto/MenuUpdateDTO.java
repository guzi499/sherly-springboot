package com.guzi.upr.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Data
public class MenuUpdateDTO {
    private List<MenuParentDTO> menuList;
}
