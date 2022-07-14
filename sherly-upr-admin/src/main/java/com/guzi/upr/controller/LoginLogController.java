package com.guzi.upr.controller;

import com.guzi.upr.service.LoginLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@RestController
@RequestMapping("/api/menu")
@Api(tags = "登陆日志相关")
@Validated
public class LoginLogController {

    @Autowired
    private LoginLogService loginLogService;

}
