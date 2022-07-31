package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.OperationLogSelfPageDTO;
import com.guzi.upr.model.dto.UserSelfUpdateDTO;
import com.guzi.upr.model.dto.UserUpdatePasswordDTO;
import com.guzi.upr.model.vo.OperationLogPageVO;
import com.guzi.upr.service.OssService;
import com.guzi.upr.service.UserSelfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@RestController
@RequestMapping("/api/user_self")
@Api(tags = "个人中心相关")
@Validated
public class UserSelfController {

    @Autowired
    private UserSelfService userSelfService;

    @Autowired
    private OssService ossService;

    @GetMapping("/get_self")
    @ApiOperation("用户个人中心")
    public Result getSelf() throws Exception {
        return Result.success(userSelfService.getSelf());
    }

    @PutMapping("/update_self")
    @ApiOperation("用户个人中心更新")
    public Result updateSelf(@RequestBody @Valid UserSelfUpdateDTO dto) {
        userSelfService.updateSelf(dto);
        return Result.success();
    }

    @PutMapping("/update_password")
    @ApiOperation("用户修改密码")
    public Result updatePassword(@RequestBody @Valid UserUpdatePasswordDTO dto) {
        userSelfService.updatePassword(dto);
        return Result.success();
    }

    @PutMapping("/update_avatar")
    @ApiOperation("用户修改头像")
    public Result updateAvatar(@RequestParam MultipartFile file) throws Exception {
        String avatarPath = ossService.uploadOne(file.getBytes(), "avatar$" + file.getName());
        userSelfService.updateAvatar(avatarPath);
        return Result.success();
    }

    @GetMapping("/operation_log/list_page")
    @ApiOperation("个人中心操作日志列表")
    public Result<PageResult<OperationLogPageVO>> operationLog(OperationLogSelfPageDTO dto) {
        return Result.success(userSelfService.operationLogListPage(dto));
    }
}
