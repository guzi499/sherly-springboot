package com.guzi.sherly.manager;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.NoticePageDTO;
import com.guzi.sherly.modules.notice.dao.NoticeDao;
import com.guzi.sherly.modules.notice.model.Notice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@Service
public class NoticeManager {

    @Resource
    private NoticeDao noticeDao;

    /**
     * 消息新增
     * @param noticeType
     * @param noticeTitle
     * @param noticeText
     * @param userIds
     */
    public void saveList(Integer noticeType, String noticeTitle, String noticeText, List<Long> userIds) {
        List<Notice> list = userIds.stream().map(userId -> {
            Notice notice = new Notice();
            notice.setNoticeType(noticeType);
            notice.setNoticeTitle(noticeTitle);
            notice.setNoticeText(noticeText);
            notice.setNoticeUserId(userId);
            return notice;
        }).collect(Collectors.toList());

        noticeDao.saveBatch(list);
    }

    public PageResult listPage(NoticePageDTO dto) {
        noticeDao.listPage(dto);
        return null;
    }

}
