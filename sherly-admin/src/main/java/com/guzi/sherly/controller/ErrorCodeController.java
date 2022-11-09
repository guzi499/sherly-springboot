package com.guzi.sherly.controller;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.ErrorCodeInsertDTO;
import com.guzi.sherly.model.dto.ErrorCodePageDTO;
import com.guzi.sherly.model.dto.ErrorCodeUpdateDTO;
import com.guzi.sherly.model.vo.ErrorCodePageVO;
import com.guzi.sherly.service.ErrorCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@RestController
@Api(tags = "错误相关")
@RequestMapping("/api/error_code")
@Validated
public class ErrorCodeController {

    @Resource
    private ErrorCodeService errorCodeService;

    @PostMapping("/save_one")
    @ApiOperation("错误新增")
    public Result saveOne(@RequestBody @Valid ErrorCodeInsertDTO dto) {
        errorCodeService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update_one")
    @ApiOperation("错误更新")
    public Result updateOne(@RequestBody @Valid ErrorCodeUpdateDTO dto) {
        errorCodeService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @ApiOperation("错误删除")
    public Result removeOne(@RequestParam Integer errorId) {
        errorCodeService.removeOne(errorId);
        return Result.success();
    }

    @GetMapping("/list_page")
    @ApiOperation("错误分页")
    public Result<PageResult<ErrorCodePageVO>> listPage(ErrorCodePageDTO dto) {
        return Result.success(errorCodeService.listPage(dto));
    }
}
