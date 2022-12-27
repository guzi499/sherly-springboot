package com.guzi.sherly.controller;

import com.guzi.sherly.manager.ScheduleTaskLogManager;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.ScheduleTaskLogPageDTO;
import com.guzi.sherly.model.vo.ScheduleTaskLogPageVO;
import com.guzi.sherly.model.vo.ScheduleTaskLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/12/5
 */
@RestController
@RequestMapping("/api/schedule_task_log")
@Api(tags = "定时任务日志相关")
@Validated
public class ScheduleTaskLogController {

    @Resource
    private ScheduleTaskLogManager scheduleTaskLogManager;

    @GetMapping("/list_page")
    @ApiOperation("定时任务日志分页")
    public Result<PageResult<ScheduleTaskLogPageVO>> listPage(ScheduleTaskLogPageDTO dto) {
        return Result.success(scheduleTaskLogManager.listPage(dto));
    }

    @GetMapping("/get_one")
    @ApiOperation("定时任务日志详情")
    public Result<ScheduleTaskLogVO> getOne(@RequestParam Long scheduleTaskLogId) {
        return Result.success(scheduleTaskLogManager.getOne(scheduleTaskLogId));
    }
}
