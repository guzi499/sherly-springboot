package com.guzi.sherly.modules.notice.dto;

import com.guzi.sherly.common.model.PageQuery;
import com.guzi.sherly.modules.notice.enums.NoticeStatusEnum;
import com.guzi.sherly.modules.notice.enums.NoticeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticePageDTO extends PageQuery {
    /** 消息类型{@link NoticeTypeEnum} */
    private Integer noticeType;

    /** 关联用户编号 */
    private Long noticeUserId;

    /** 状态{@link NoticeStatusEnum} */
    private Integer noticeStatus;
}
