package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.UserInsertDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import com.guzi.upr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    @ApiOperation(value = "获取所有用户")
    public Result<List<User>> getAll() {
        // 分页
        return null;
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增用户")
    public Result saveOne(UserInsertDTO dto) {
        userService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update")
    @ApiOperation("更新")
    public Result updateOne(UserUpdateDTO dto) {
        userService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove")
    @ApiOperation("移除用户")
    public Result removeOne(@RequestParam Long userId) {
        userService.removeOne(userId);
        return Result.success();
    }
}
