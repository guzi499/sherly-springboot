package com.guzi.sherly.modules.notice.dao;

import com.guzi.sherly.modules.mybatisplus.service.SherlyServiceImpl;
import com.guzi.sherly.modules.notice.mapper.NoticeMapper;
import com.guzi.sherly.modules.notice.model.Notice;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Service
public class NoticeDao extends SherlyServiceImpl<NoticeMapper, Notice> {
}
