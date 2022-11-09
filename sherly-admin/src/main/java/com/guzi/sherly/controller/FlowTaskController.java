package com.guzi.sherly.controller;

import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.TaskHandleDTO;
import com.guzi.sherly.service.FlowTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@RestController
@RequestMapping("/api/flow_task")
@Api(tags = "流程任务相关")
@Validated
public class FlowTaskController {

    @Resource
    private FlowTaskService flowTaskService;

    @PostMapping("handle")
    @ApiOperation("流程任务办理")
    public Result handle(TaskHandleDTO dto) {
        flowTaskService.handle(dto);
        return Result.success();
    }
}
