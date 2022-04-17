package com.guzi.upr.model.dto;

import com.guzi.upr.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 付东辉
 * @date 2022/4/9
 */
@Data
public class UserPageDTO extends PageQuery {
    /** 昵称 */
    @ApiModelProperty("昵称")
    private String nickname;

    /** 姓名 */
    @ApiModelProperty("姓名")
    private String realName;

    /** 手机号 */
    @ApiModelProperty("手机号")
    private String phone;

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long departmentId;
}
