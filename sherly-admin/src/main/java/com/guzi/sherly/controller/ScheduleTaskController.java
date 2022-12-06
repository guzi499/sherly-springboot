package com.guzi.sherly.controller;

import com.guzi.sherly.manager.ScheduleTaskManager;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.ScheduleTaskInsertDTO;
import com.guzi.sherly.model.dto.ScheduleTaskPageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/12/5
 */
@RestController
@RequestMapping("/api/schedule_task")
@Api(tags = "定时任务相关")
@Validated
public class ScheduleTaskController {

    @Resource
    private ScheduleTaskManager scheduleTaskManager;

    @GetMapping("/list_page")
    @ApiOperation("定时任务分页")
    public Result list(ScheduleTaskPageDTO dto) {
        return Result.success(scheduleTaskManager.listPage(dto));
    }

    @GetMapping("/save_one")
    @ApiOperation("定时任务新增")
    public Result saveOne(ScheduleTaskInsertDTO dto) {
        scheduleTaskManager.saveOne(dto);
        return Result.success();
    }
}
