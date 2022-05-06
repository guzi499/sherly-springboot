package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.UserInsertDTO;
import com.guzi.upr.model.dto.UserPageDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import com.guzi.upr.model.vo.UserPageVo;
import com.guzi.upr.model.vo.UserVo;
import com.guzi.upr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 谷子毅
 * @date 2022/3/18
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户相关")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list/page")
    @PreAuthorize("hasAnyAuthority('user:list:page')")
    @ApiOperation(value = "用户分页")
    public Result<PageResult<UserPageVo>> listPage(UserPageDTO dto) {
        return Result.success(userService.listPage(dto));
    }

    @GetMapping("/list/export")
    @PreAuthorize("hasAnyAuthority('user:list:export')")
    @ApiOperation(value = "用户导出", produces = "application/octet-stream")
    public void listExport(HttpServletResponse response) throws IOException {
        userService.listExport(response);
    }


    @GetMapping("/get/one")
    @PreAuthorize("hasAnyAuthority('user:get:one')")
    @ApiOperation(value = "用户详情")
    public Result<UserVo> getOne(@RequestParam Long userId) {
        return Result.success(userService.getOne(userId));
    }

    @PutMapping("/ban/one")
    @PreAuthorize("hasAnyAuthority('user:ban:one')")
    @ApiOperation(value = "用户禁用/启用")
    public Result banOne(@RequestParam Long userId, @RequestParam @Range(min = 0, max = 1) Integer enable) {
        userService.banOne(userId, enable);
        return Result.success();
    }

    @PostMapping("/save/one")
    @PreAuthorize("hasAnyAuthority('user:save:one')")
    @ApiOperation(value = "用户新增")
    public Result saveOne(@RequestBody @Validated UserInsertDTO dto) {
        userService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update/one")
    @PreAuthorize("hasAnyAuthority('user:update:one')")
    @ApiOperation("用户更新")
    public Result updateOne(@RequestBody @Validated UserUpdateDTO dto) {
        userService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @PreAuthorize("hasAnyAuthority('user:remove:one')")
    @ApiOperation("用户删除")
    public Result removeOne(@RequestParam Long userId) {
        userService.removeOne(userId);
        return Result.success();
    }
}
