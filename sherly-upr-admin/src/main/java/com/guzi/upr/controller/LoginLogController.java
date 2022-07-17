package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.LoginLogPageDTO;
import com.guzi.upr.model.vo.LoginLogPageVO;
import com.guzi.upr.service.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@RestController
@RequestMapping("/api/login_log")
@Api(tags = "登陆日志相关")
@Validated
public class LoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    @GetMapping("/list_page")
    @ApiOperation("登录日志分页")
    public Result<PageResult<LoginLogPageVO>> listPage(LoginLogPageDTO dto) {
        return Result.success(loginLogService.listPage(dto));
    }

    @GetMapping("/remove_all")
    @ApiOperation("登录日志清空")
    public Result removeAll() {
        loginLogService.removeAll();
        return Result.success();
    }

}
