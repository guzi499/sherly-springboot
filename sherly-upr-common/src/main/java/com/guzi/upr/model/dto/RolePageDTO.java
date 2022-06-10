package com.guzi.upr.model.dto;

import com.guzi.upr.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/4/7
 */
@Data
public class RolePageDTO extends PageQuery {
    /** 角色名称 */
    @ApiModelProperty("角色名称")
    private String roleName;
}
