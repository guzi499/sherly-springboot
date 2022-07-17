package com.guzi.upr.controller;

import com.guzi.upr.log.service.OperationLogService;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.OperationLogPageDTO;
import com.guzi.upr.model.vo.OperationLogPageVO;
import com.guzi.upr.model.vo.OperationLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@RestController
@RequestMapping("/api/operation_log")
@Api(tags = "操作日志相关")
@Validated
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/list_page")
    @ApiOperation("操作日志分页")
    public Result<PageResult<OperationLogPageVO>> listPage(OperationLogPageDTO dto) {
        return Result.success(operationLogService.listPage(dto));
    }

    @GetMapping("/get_one")
    @ApiOperation("操作日志详情")
    public Result<OperationLogVO> getOne(@RequestParam Long logId) {
        return Result.success(operationLogService.getOne(logId));
    }

    @DeleteMapping("/remove_all")
    @ApiOperation("操作日志清空")
    public Result removeAll() {
        operationLogService.removeAll();
        return Result.success();
    }
}
