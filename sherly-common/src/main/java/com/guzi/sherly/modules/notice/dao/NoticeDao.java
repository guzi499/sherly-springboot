package com.guzi.sherly.modules.notice.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import com.guzi.sherly.modules.notice.dto.NoticePageDTO;
import com.guzi.sherly.modules.notice.mapper.NoticeMapper;
import com.guzi.sherly.modules.notice.model.NoticeDO;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.guzi.sherly.modules.notice.enums.NoticeStatusEnum.READ;
import static com.guzi.sherly.modules.notice.enums.NoticeStatusEnum.UNREAD;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Service
public class NoticeDao extends SherlyServiceImpl<NoticeMapper, NoticeDO> {

    /**
     * 消息分页
     * @param dto
     * @return
     */
    public Page<NoticeDO> listPage(NoticePageDTO dto) {
        SherlyLambdaQueryWrapper<NoticeDO> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.eqIfExist(NoticeDO::getNoticeType, dto.getNoticeType())
                .eqIfExist(NoticeDO::getNoticeUserId, dto.getNoticeUserId())
                .eqIfExist(NoticeDO::getNoticeStatus, dto.getNoticeStatus());
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    /**
     * 根据消息编号设置消息状态为未读
     * @param noticeIds
     */
    public void clearList(List<Long> noticeIds) {
        LambdaUpdateWrapper<NoticeDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(NoticeDO::getNoticeId, noticeIds)
                .set(NoticeDO::getNoticeStatus, READ);
        this.update(wrapper);
    }

    /**
     * 根据关联用户编号设置消息状态为已读
     */
    public void clearAll() {
        LambdaUpdateWrapper<NoticeDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(NoticeDO::getNoticeUserId, SecurityUtil.getUserId())
                .set(NoticeDO::getNoticeStatus, READ);
        this.update(wrapper);
    }

    /**
     * 根据消息编号设置消息状态为未读
     * @param noticeIds
     */
    public void resetList(List<Long> noticeIds) {
        LambdaUpdateWrapper<NoticeDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(NoticeDO::getNoticeId, noticeIds)
                .set(NoticeDO::getNoticeStatus, UNREAD);
        this.update(wrapper);
    }
}
