package com.guzi.sherly.controller;

import com.guzi.sherly.manager.OssManager;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.OperationLogSelfPageDTO;
import com.guzi.sherly.model.dto.UserSelfUpdateDTO;
import com.guzi.sherly.model.dto.UserUpdatePasswordDTO;
import com.guzi.sherly.model.vo.OperationLogPageVO;
import com.guzi.sherly.model.vo.UserSelfVO;
import com.guzi.sherly.service.UserSelfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource
    private UserSelfService userSelfService;

    @Resource
    private OssManager ossManager;

    @GetMapping("/get_self")
    @ApiOperation("用户个人中心")
    public Result<UserSelfVO> getSelf() {
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
    public Result updateAvatar(@RequestParam MultipartFile file) {
        String avatarPath = ossManager.uploadOne(file, "avatar/");
        userSelfService.updateAvatar(avatarPath);
        return Result.success();
    }

    @GetMapping("/operation_log/list_page")
    @ApiOperation("个人中心操作日志列表")
    public Result<PageResult<OperationLogPageVO>> operationLog(OperationLogSelfPageDTO dto) {
        return Result.success(userSelfService.operationLogListPage(dto));
    }
}
