package com.guzi.sherly.modules.notice.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.model.dto.NoticePageDTO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import com.guzi.sherly.modules.notice.mapper.NoticeMapper;
import com.guzi.sherly.modules.notice.model.Notice;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.guzi.sherly.modules.notice.enums.NoticeStatusEnum.READ;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Service
public class NoticeDao extends SherlyServiceImpl<NoticeMapper, Notice> {

    public Page<Notice> listPage(NoticePageDTO dto) {
        SherlyLambdaQueryWrapper<Notice> wrapper = new SherlyLambdaQueryWrapper<>();
        wrapper.eqIfExist(Notice::getNoticeType, dto.getNoticeType())
                .eqIfExist(Notice::getNoticeUserId, dto.getNoticeUserId())
                .eqIfExist(Notice::getNoticeStatus, dto.getNoticeStatus());
        return this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
    }

    public void clearList(List<Long> noticeIds) {
        LambdaUpdateWrapper<Notice> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(Notice::getNoticeId, noticeIds)
                .set(Notice::getNoticeStatus, READ.getStatus());
        this.update(wrapper);
    }

    public void clearAll() {
        LambdaUpdateWrapper<Notice> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Notice::getNoticeUserId, SecurityUtil.getUserId())
                .set(Notice::getNoticeStatus, READ.getStatus());
        this.update(wrapper);
    }
}
