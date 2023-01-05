package com.guzi.sherly.modules.notice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guzi.sherly.common.model.SimpleBaseModel;
import com.guzi.sherly.modules.notice.enums.NoticeStatusEnum;
import com.guzi.sherly.modules.notice.enums.NoticeTypeEnum;
import lombok.Data;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Data
@TableName("sys_notice")
public class Notice extends SimpleBaseModel {
    /** 消息编号 */
    @TableId(type = IdType.AUTO)
    private Long noticeId;

    /** 消息类型{@link NoticeTypeEnum} */
    private Integer noticeType;

    /** 关联用户编号 */
    private Long noticeUserId;

    /** 消息标题 */
    private String noticeTitle;

    /** 消息内容 */
    private String noticeText;

    /** 状态{@link NoticeStatusEnum} */
    private Integer noticeStatus;
}
