package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        return Result.success(loginService.login(dto));
    }
}
