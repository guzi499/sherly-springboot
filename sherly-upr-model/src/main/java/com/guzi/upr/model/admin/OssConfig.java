package com.guzi.upr.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.upr.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/6/11
 */
@Data
@TableName("sys_oss_config")
public class OssConfig extends BaseModel {
    /** id */
    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 供应商 */
    @ApiModelProperty("供应商")
    private Integer supplier;

    /** accessKey */
    @ApiModelProperty("accessKey")
    private String accessKey;

    /** secretKey */
    @ApiModelProperty("secretKey")
    private String secretKey;

    /** preUrl */
    @ApiModelProperty("preUrl")
    private String preUrl;

    /** bucket */
    @ApiModelProperty("bucket")
    private String bucket;
}
