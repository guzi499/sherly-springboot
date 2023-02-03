package com.guzi.sherly.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.modules.quartz.dao.ScheduleTaskLogDao;
import com.guzi.sherly.modules.quartz.dto.ScheduleTaskLogPageDTO;
import com.guzi.sherly.modules.quartz.model.ScheduleTaskLogDO;
import com.guzi.sherly.modules.quartz.vo.ScheduleTaskLogPageVO;
import com.guzi.sherly.modules.quartz.vo.ScheduleTaskLogVO;
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
        Page<ScheduleTaskLogDO> page = scheduleTaskLogDao.listPage(dto);
        List<ScheduleTaskLogPageVO> result = page.getRecords().stream().map(e -> {
            ScheduleTaskLogPageVO vo = new ScheduleTaskLogPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
        return PageResult.build(result, page.getTotal());
    }


    public ScheduleTaskLogVO getOne(Long scheduleTaskLogId) {
        ScheduleTaskLogDO scheduleTaskLogDO = scheduleTaskLogDao.getById(scheduleTaskLogId);
        ScheduleTaskLogVO vo = new ScheduleTaskLogVO();
        BeanUtils.copyProperties(scheduleTaskLogDO, vo);
        return vo;
    }
}
