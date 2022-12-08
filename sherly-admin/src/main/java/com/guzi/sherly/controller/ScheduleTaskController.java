package com.guzi.sherly.controller;

import com.guzi.sherly.manager.ScheduleTaskManager;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.ScheduleTaskInsertDTO;
import com.guzi.sherly.model.dto.ScheduleTaskPageDTO;
import com.guzi.sherly.model.dto.ScheduleTaskUpdateDTO;
import com.guzi.sherly.model.vo.ScheduleTaskPageVO;
import com.guzi.sherly.modules.log.annotation.SherlyLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result<PageResult<ScheduleTaskPageVO>> list(ScheduleTaskPageDTO dto) {
        return Result.success(scheduleTaskManager.listPage(dto));
    }

    @PostMapping("/save_one")
    @ApiOperation("定时任务新增")
    public Result saveOne(@RequestBody ScheduleTaskInsertDTO dto) {
        scheduleTaskManager.saveOne(dto);
        return Result.success();
    }

    @GetMapping("/run_once")
    @ApiOperation("定时任务执行一次")
    @SherlyLog(noRecord = true)
    public Result runOnce(@RequestParam Integer scheduleTaskId) {
        scheduleTaskManager.runOnce(scheduleTaskId);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @ApiOperation("定时任务删除")
    public Result removeOne(@RequestParam Integer scheduleTaskId) {
        scheduleTaskManager.removeOne(scheduleTaskId);
        return Result.success();
    }

    @PutMapping("/update_one")
    @ApiOperation("定时任务更新")
    public Result updateOne(@RequestBody ScheduleTaskUpdateDTO dto) {
        scheduleTaskManager.updateOne(dto);
        return Result.success();
    }

}
