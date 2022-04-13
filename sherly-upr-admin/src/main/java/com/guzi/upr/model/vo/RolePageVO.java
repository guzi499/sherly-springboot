package com.guzi.upr.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 谷子毅
 * @date 2022/4/7
 */
@Data
public class RolePageVO {
    /** 角色id */
    @ApiModelProperty("角色id")
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /** 角色名称 */
    @ApiModelProperty("角色名称")
    private String roleName;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
