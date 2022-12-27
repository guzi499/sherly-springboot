package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.dto.ScheduleTaskLogPageDTO;
import com.guzi.sherly.model.vo.ScheduleTaskLogPageVO;
import com.guzi.sherly.model.vo.ScheduleTaskLogVO;
import com.guzi.sherly.modules.quartz.dao.ScheduleTaskLogDao;
import com.guzi.sherly.modules.quartz.model.ScheduleTaskLog;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/12/5
 */
@Service
public class ScheduleTaskLogManager {

    @Resource
    private ScheduleTaskLogDao scheduleTaskLogDao;

    public PageResult<ScheduleTaskLogPageVO> listPage(ScheduleTaskLogPageDTO dto) {
        Page<ScheduleTaskLog> page = scheduleTaskLogDao.listPage(dto);
        List<ScheduleTaskLogPageVO> result = page.getRecords().stream().map(e -> {
            ScheduleTaskLogPageVO vo = new ScheduleTaskLogPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
        return PageResult.build(result, page.getTotal());
    }


    public ScheduleTaskLogVO getOne(Long scheduleTaskLogId) {
        ScheduleTaskLog scheduleTaskLog = scheduleTaskLogDao.getById(scheduleTaskLogId);
        ScheduleTaskLogVO vo = new ScheduleTaskLogVO();
        BeanUtils.copyProperties(scheduleTaskLog, vo);
        return vo;
    }
}
