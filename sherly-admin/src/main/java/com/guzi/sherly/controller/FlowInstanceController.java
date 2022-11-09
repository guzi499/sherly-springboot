package com.guzi.sherly.controller;

import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.FlowInstanceStartupDTO;
import com.guzi.sherly.service.FlowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/10/21
 */
@RestController
@RequestMapping("/api/flow_instance")
@Api(tags = "流程实例相关")
@Validated
public class FlowInstanceController {

    @Resource
    private FlowInstanceService flowInstanceService;

    @PostMapping("startup")
    @ApiOperation("流程实例启动")
    public Result startup(@RequestBody FlowInstanceStartupDTO dto) {
        flowInstanceService.startup(dto);
        return Result.success();
    }
}
