package com.guzi.sherly.controller;

import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.common.model.Result;
import com.guzi.sherly.modules.log.annotation.SherlyLog;
import com.guzi.sherly.modules.log.dto.OperationLogPageDTO;
import com.guzi.sherly.modules.log.service.OperationLogManager;
import com.guzi.sherly.modules.log.vo.OperationLogPageVO;
import com.guzi.sherly.modules.log.vo.OperationLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@RestController
@RequestMapping("/api/operation_log")
@Api(tags = "操作日志相关")
@Validated
public class OperationLogController {

    @Resource
    private OperationLogManager operationLogManager;

    @GetMapping("/list_page")
    @PreAuthorize("hasAnyAuthority('operation_log:list_page')")
    @ApiOperation("操作日志分页")
    @SherlyLog(noRecord = true)
    public Result<PageResult<OperationLogPageVO>> listPage(OperationLogPageDTO dto) {
        return Result.success(operationLogManager.listPage(dto));
    }

    @GetMapping("/get_one")
    @PreAuthorize("hasAnyAuthority('operation_log:get_one')")
    @ApiOperation("操作日志详情")
    @SherlyLog(noRecord = true)
    public Result<OperationLogVO> getOne(@RequestParam Long logId) {
        return Result.success(operationLogManager.getOne(logId));
    }

    @DeleteMapping("/remove_all")
    @PreAuthorize("hasAnyAuthority('operation_log:remove_all')")
    @ApiOperation("操作日志清空")
    public Result removeAll() {
        operationLogManager.removeAll();
        return Result.success();
    }
}
