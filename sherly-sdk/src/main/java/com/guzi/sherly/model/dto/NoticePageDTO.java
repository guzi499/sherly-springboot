package com.guzi.sherly.model.dto;

import com.guzi.sherly.model.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticePageDTO extends PageQuery {
    /** 消息类型[enum] */
    private Integer noticeType;

    /** 关联用户编号 */
    private Long noticeUserId;

    /** 状态[enum] */
    private Integer noticeStatus;
}
