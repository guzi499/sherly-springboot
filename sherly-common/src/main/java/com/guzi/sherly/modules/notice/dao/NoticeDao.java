package com.guzi.sherly.modules.notice.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.model.dto.NoticePageDTO;
import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.mybatisplus.wrapper.SherlyLambdaQueryWrapper;
import com.guzi.sherly.modules.notice.mapper.NoticeMapper;
import com.guzi.sherly.modules.notice.model.Notice;
import org.springframework.stereotype.Service;

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
}
