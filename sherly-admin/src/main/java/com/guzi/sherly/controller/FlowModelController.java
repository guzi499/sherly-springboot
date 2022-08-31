package com.guzi.sherly.controller;

import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.FlowModelInsertDTO;
import com.guzi.sherly.service.FlowModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 谷子毅* @date 2022/8/31
 */
@RestController
@RequestMapping("/api/flow_model")
@Api(tags = "流程模型相关")
@Validated
public class FlowModelController {

    @Autowired
    private FlowModelService flowModelService;

    @PostMapping("/save_one")
    @ApiOperation("流程模型新增")
    public Result saveOne(@RequestBody @Valid FlowModelInsertDTO dto) {
        flowModelService.saveOne(dto);
        return Result.success();
    }

}
