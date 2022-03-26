package com.guzi.upr.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.guzi.upr.model.admin.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/25 23:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuParentDto extends Menu {
    @TableField(exist = false)
    private List<MenuParentDto> children;
}
