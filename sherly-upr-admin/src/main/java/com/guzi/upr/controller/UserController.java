package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.UserBanDTO;
import com.guzi.upr.model.dto.UserInsertDTO;
import com.guzi.upr.model.dto.UserPageDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import com.guzi.upr.model.vo.UserInfoVo;
import com.guzi.upr.model.vo.UserVo;
import com.guzi.upr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/18
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/page")
    @ApiOperation(value = "用户分页")
    public Result<PageResult<UserVo>> getAll(UserPageDTO dto) {
        // 分页
        return Result.success(userService.page(dto));
    }


    @GetMapping("/")
    @ApiOperation(value = "用户详情")
    public Result<UserInfoVo> getOne(@RequestParam("id") Long id) {

        return Result.success(userService.getById(id));
    }

    @PutMapping("/ban")
    @ApiOperation(value = "禁用用户")
    public Result<UserInfoVo> banUserById(@RequestBody UserBanDTO dto) {
        userService.banUserById(dto);
        return Result.success();
    }

    @PostMapping("/save")
    @ApiOperation(value = "用户新增")
    public Result saveOne(@RequestBody UserInsertDTO dto) {
        userService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update")
    @ApiOperation("用户修改")
    public Result updateOne(@RequestBody UserUpdateDTO dto) {
        userService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove")
    @ApiOperation("用户删除")
    public Result removeOne(@RequestParam Long userId) {
        userService.removeOne(userId);
        return Result.success();
    }
}
