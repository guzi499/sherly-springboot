package com.guzi.sherly.controller;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.UserInsertDTO;
import com.guzi.sherly.model.dto.UserPageDTO;
import com.guzi.sherly.model.dto.UserSelectDTO;
import com.guzi.sherly.model.dto.UserUpdateDTO;
import com.guzi.sherly.model.vo.UserPageVo;
import com.guzi.sherly.model.vo.UserSelectVO;
import com.guzi.sherly.model.vo.UserVo;
import com.guzi.sherly.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/18
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户相关")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list_page")
    @PreAuthorize("hasAnyAuthority('user:list_page')")
    @ApiOperation(value = "用户分页")
    public Result<PageResult<UserPageVo>> listPage(UserPageDTO dto) {
        return Result.success(userService.listPage(dto));
    }

    @GetMapping("/list_export")
    @PreAuthorize("hasAnyAuthority('user:list_export')")
    @ApiOperation(value = "用户导出", produces = "application/octet-stream")
    public void listExport(HttpServletResponse response) {
        userService.listExport(response);
    }

    @GetMapping("/get_one")
    @PreAuthorize("hasAnyAuthority('user:get_one')")
    @ApiOperation(value = "用户详情")
    public Result<UserVo> getOne(@RequestParam Long userId) {
        return Result.success(userService.getOne(userId));
    }

    @PutMapping("/ban_one")
    @PreAuthorize("hasAnyAuthority('user:ban_one')")
    @ApiOperation(value = "用户禁用/启用")
    public Result banOne(@RequestParam Long userId, @RequestParam Integer enable) {
        userService.banOne(userId, enable);
        return Result.success();
    }

    @PostMapping("/save_one")
    @PreAuthorize("hasAnyAuthority('user:save_one')")
    @ApiOperation(value = "用户新增")
    public Result saveOne(@RequestBody @Valid UserInsertDTO dto) {
        userService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update_one")
    @PreAuthorize("hasAnyAuthority('user:update_one')")
    @ApiOperation("用户更新")
    public Result updateOne(@RequestBody @Valid UserUpdateDTO dto) {
        userService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @PreAuthorize("hasAnyAuthority('user:remove_one')")
    @ApiOperation("用户删除")
    public Result removeOne(@RequestParam Long userId) {
        userService.removeOne(userId);
        return Result.success();
    }

    @GetMapping("/list_all")
    @ApiOperation("用户查询")
    public Result<List<UserSelectVO>> listAll(UserSelectDTO dto) {
        return Result.success(userService.listAll(dto));
    }
}
