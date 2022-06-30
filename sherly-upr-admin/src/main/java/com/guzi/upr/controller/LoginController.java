package com.guzi.upr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@RestController
@Api(tags = "登录相关")
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<LoginVO> login(@RequestBody @Validated LoginDTO dto, HttpServletRequest request) throws JsonProcessingException {
        return Result.success(loginService.login(dto, request));
    }

    @GetMapping("/logout")
    @ApiOperation("登出")
    public Result logout() {
        loginService.logout();
        return Result.success();
    }
}
