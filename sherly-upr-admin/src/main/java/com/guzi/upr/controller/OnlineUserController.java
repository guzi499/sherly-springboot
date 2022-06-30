package com.guzi.upr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.admin.OnlineUser;
import com.guzi.upr.model.dto.OnlineUserQueryDTO;
import com.guzi.upr.service.OnlineUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/5/18
 */
@RestController
@RequestMapping("/api/user/online")
@Api(tags = "在线用户相关")
@Validated
public class OnlineUserController {

    @Autowired
    private OnlineUserService onlineUserService;

    @GetMapping("/list")
    @ApiOperation("在线用户列表")
    public Result<List<OnlineUser>> list(@Valid OnlineUserQueryDTO dto) throws JsonProcessingException {
        return Result.success(onlineUserService.list(dto));
    }

    @GetMapping("/force/quit")
    @ApiOperation("强制退出")
    public Result forceQuit(@RequestParam String phone) {
        onlineUserService.forceQuit(phone);
        return Result.success();
    }
}
