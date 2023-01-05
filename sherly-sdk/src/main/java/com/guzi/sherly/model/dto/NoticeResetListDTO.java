package com.guzi.sherly.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2023/1/5
 */
@Data
public class NoticeResetListDTO {
    /** 消息编号列表 */
    @ApiModelProperty(value = "消息编号列表")
    private List<Long> noticeIds;
}
