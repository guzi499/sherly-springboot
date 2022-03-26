package com.guzi.upr.model.vo;

import com.guzi.upr.model.dto.MenuParentDto;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Data
public class MenuListTreeVO {
    private List<MenuParentDto> menuList;
}
