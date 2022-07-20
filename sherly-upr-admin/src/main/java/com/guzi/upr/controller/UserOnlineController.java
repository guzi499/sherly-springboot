package com.guzi.upr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.UserOnlineSelectDTO;
import com.guzi.upr.model.vo.UserOnlineSelectVO;
import com.guzi.upr.service.UserOnlineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/user_online")
@Api(tags = "在线用户相关")
@Validated
public class UserOnlineController {

    @Autowired
    private UserOnlineService userOnlineService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('user_online:list')")
    @ApiOperation("在线用户列表")
    public Result<List<UserOnlineSelectVO>> list(@Valid UserOnlineSelectDTO dto) throws JsonProcessingException {
        return Result.success(userOnlineService.list(dto));
    }

    @GetMapping("/force_quit")
    @PreAuthorize("hasAnyAuthority('user_online:force_quit')")
    @ApiOperation("强制退出")
    public Result forceQuit(@RequestParam String phone) {
        userOnlineService.forceQuit(phone);
        return Result.success();
    }
}
