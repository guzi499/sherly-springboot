package com.guzi.sherly.controller;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.FlowModelDeployDTO;
import com.guzi.sherly.model.dto.FlowModelInsertDTO;
import com.guzi.sherly.model.dto.FlowModelPageDTO;
import com.guzi.sherly.model.dto.FlowModelUpdateDTO;
import com.guzi.sherly.model.vo.FlowModelPageVO;
import com.guzi.sherly.model.vo.FlowModelVO;
import com.guzi.sherly.service.FlowModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 谷子毅
 * @date 2022/8/31
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

    @PutMapping("/update_one")
    @ApiOperation("流程模型更新")
    public Result updateOne(@RequestBody @Valid FlowModelUpdateDTO dto) {
        flowModelService.updateOne(dto);
        return Result.success();
    }

    @GetMapping("/list_page")
    @ApiOperation("流程模型分页")
    public Result<PageResult<FlowModelPageVO>> listPage(FlowModelPageDTO dto) {
        return Result.success(flowModelService.listPage(dto));
    }

    @DeleteMapping("/remove_one")
    @ApiOperation("流程模型删除")
    public Result removeOne(@RequestParam String id) {
        flowModelService.removeOne(id);
        return Result.success();
    }

    @GetMapping("/get_one")
    @ApiOperation("流程模型详情")
    public Result<FlowModelVO> getOne(@RequestParam String id) {
        return Result.success(flowModelService.getOne(id));
    }

    @PostMapping("/deploy")
    @ApiOperation("流程模型部署")
    public Result deploy(@RequestBody FlowModelDeployDTO dto) {
        flowModelService.deploy(dto);
        return Result.success();
    }
}
