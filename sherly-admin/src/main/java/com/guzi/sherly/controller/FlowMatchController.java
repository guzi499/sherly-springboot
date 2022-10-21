package com.guzi.sherly.controller;

import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.FlowMatchDonePageDTO;
import com.guzi.sherly.model.dto.FlowMatchRequestPageDTO;
import com.guzi.sherly.model.dto.FlowMatchTodoPageDTO;
import com.guzi.sherly.service.FlowMatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@RestController
@RequestMapping("/api/flow_match")
@Api(tags = "流程匹配相关")
@Validated
public class FlowMatchController {

    @Autowired
    private FlowMatchService flowMatchService;

    @GetMapping("/todo/list_page")
    @ApiOperation("流程待办")
    public Result todo(FlowMatchTodoPageDTO dto) {
        return Result.success(flowMatchService.todo(dto));
    }

    @GetMapping("/done/list_page")
    @ApiOperation("流程已办")
    public Result done(FlowMatchDonePageDTO dto) {
        return Result.success(flowMatchService.done(dto));
    }

    @GetMapping("/request/list_page")
    @ApiOperation("流程申请")
    public Result request(FlowMatchRequestPageDTO dto) {
        return Result.success(flowMatchService.request(dto));
    }
}
