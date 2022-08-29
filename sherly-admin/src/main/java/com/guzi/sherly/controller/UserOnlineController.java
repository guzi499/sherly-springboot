package com.guzi.sherly.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.UserOnlineSelectDTO;
import com.guzi.sherly.model.vo.UserOnlineSelectVO;
import com.guzi.sherly.service.UserOnlineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list_all")
    @PreAuthorize("hasAnyAuthority('user_online:list_all')")
    @ApiOperation("在线用户列表")
    public Result<List<UserOnlineSelectVO>> listAll(@Valid UserOnlineSelectDTO dto) throws JsonProcessingException {
        return Result.success(userOnlineService.listAll(dto));
    }

    @DeleteMapping("/force_quit")
    @PreAuthorize("hasAnyAuthority('user_online:force_quit')")
    @ApiOperation("强制退出")
    public Result forceQuit(@RequestParam String sessionId) {
        userOnlineService.forceQuit(sessionId);
        return Result.success();
    }
}
