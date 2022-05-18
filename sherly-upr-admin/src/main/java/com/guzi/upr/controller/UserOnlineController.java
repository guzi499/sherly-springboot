package com.guzi.upr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guzi.upr.model.Result;
import com.guzi.upr.service.UserOnlineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谷子毅
 * @date 2022/5/18
 */
@RestController
@RequestMapping("/api/user/online")
@Api(tags = "在线用户相关")
public class UserOnlineController {

    @Autowired
    private UserOnlineService userOnlineService;

    @GetMapping("/list")
    @ApiOperation("在线用户相关")
    public Result list() throws JsonProcessingException {
        return Result.success(userOnlineService.list());
    }
}
