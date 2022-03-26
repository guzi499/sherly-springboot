package com.guzi.upr.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Data
public class MenuUpdateDTO {
    private List<MenuParentDto> menuList;
}
