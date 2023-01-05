package com.guzi.sherly.admin.role.dto;

import com.guzi.sherly.common.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/4/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageDTO extends PageQuery {
    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
