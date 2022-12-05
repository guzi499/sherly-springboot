package com.guzi.sherly.controller;

import com.guzi.sherly.manager.ScheduleTaskManager;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
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
}
