package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.modules.notice.dao.NoticeDao;
import com.guzi.sherly.modules.notice.dto.NoticeClearListDTO;
import com.guzi.sherly.modules.notice.dto.NoticePageDTO;
import com.guzi.sherly.modules.notice.dto.NoticeResetListDTO;
import com.guzi.sherly.modules.notice.model.Notice;
import com.guzi.sherly.modules.notice.vo.NoticePageVO;
import org.springframework.beans.BeanUtils;
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

    /**
     * 消息分页
     * @param dto
     * @return
     */
    public PageResult<NoticePageVO> listPage(NoticePageDTO dto) {
        Page<Notice> page = noticeDao.listPage(dto);
        List<NoticePageVO> result = page.getRecords().stream().map(e -> {
            NoticePageVO vo = new NoticePageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }

    /**
     * 消息部分设置已读
     * @param dto
     */
    public void clearList(NoticeClearListDTO dto) {
        List<Long> noticeIds = dto.getNoticeIds();
        noticeDao.clearList(noticeIds);
    }

    /**
     * 消息全部设置已读
     */
    public void clearAll() {
        noticeDao.clearAll();
    }

    /**
     * 消息部分设置未读
     * @param dto
     */
    public void resetList(NoticeResetListDTO dto) {
        List<Long> noticeIds = dto.getNoticeIds();
        noticeDao.resetList(noticeIds);
    }
}
