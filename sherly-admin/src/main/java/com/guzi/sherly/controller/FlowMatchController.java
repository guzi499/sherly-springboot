package com.guzi.sherly.controller;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.FlowMatchDonePageDTO;
import com.guzi.sherly.model.dto.FlowMatchMyRequestPageDTO;
import com.guzi.sherly.model.dto.FlowMatchTodoPageDTO;
import com.guzi.sherly.model.vo.FlowMatchDonePageVO;
import com.guzi.sherly.model.vo.FlowMatchMyRequestPageVO;
import com.guzi.sherly.model.vo.FlowMatchTodoPageVO;
import com.guzi.sherly.service.FlowMatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@RestController
@RequestMapping("/api/flow_match")
@Api(tags = "流程匹配相关")
@Validated
public class FlowMatchController {

    @Resource
    private FlowMatchService flowMatchService;

    @GetMapping("/todo/list_page")
    @ApiOperation("流程待办")
    public Result<PageResult<FlowMatchTodoPageVO>> todo(FlowMatchTodoPageDTO dto) {
        return Result.success(flowMatchService.todo(dto));
    }

    @GetMapping("/done/list_page")
    @ApiOperation("流程已办")
    public Result<PageResult<FlowMatchDonePageVO>> done(FlowMatchDonePageDTO dto) {
        return Result.success(flowMatchService.done(dto));
    }

    @GetMapping("/my_request/list_page")
    @ApiOperation("流程申请")
    public Result<PageResult<FlowMatchMyRequestPageVO>> myRequest(FlowMatchMyRequestPageDTO dto) {
        return Result.success(flowMatchService.myRequest(dto));
    }
}
