package com.guzi.sherly.controller;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.FlowDefinitionPageDTO;
import com.guzi.sherly.model.vo.FlowDefinitionPageVO;
import com.guzi.sherly.service.FlowDefinitionService;
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
 * @date 2022/10/21
 */
@RestController
@RequestMapping("/api/flow_definition")
@Api(tags = "流程定义相关")
@Validated
public class FlowDefinitionController {

    @Resource
    private FlowDefinitionService flowDefinitionService;

    @GetMapping("/list_page")
    @ApiOperation("流程定义分页")
    public Result<PageResult<FlowDefinitionPageVO>> listPage(FlowDefinitionPageDTO dto) {
        return Result.success(flowDefinitionService.listPage(dto));
    }

    @GetMapping("/remove_one")
    @ApiOperation("流程定义删除")
    public Result removeOne(@RequestParam String definitionId) {
        flowDefinitionService.removeOne(definitionId);
        return Result.success();
    }
}
