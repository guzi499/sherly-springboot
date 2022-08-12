package com.guzi.sherly.controller;

import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.LoginDTO;
import com.guzi.sherly.model.vo.LoginTenantVO;
import com.guzi.sherly.model.vo.LoginVO;
import com.guzi.sherly.modules.log.annotation.SherlyLog;
import com.guzi.sherly.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@RestController
@Api(tags = "登录相关")
@RequestMapping("/api")
@Validated
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation("登录")
    @SherlyLog(noRecord = true)
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO dto, HttpServletRequest request) throws Exception {
        return Result.success(loginService.login(dto, request));
    }

    @GetMapping("/logout")
    @ApiOperation("登出")
    @SherlyLog(noRecord = true)
    public Result logout() {
        loginService.logout();
        return Result.success();
    }

    @GetMapping("/available_list")
    @ApiOperation("可用租户列表")
    @SherlyLog(noRecord = true)
    public Result<List<LoginTenantVO>> availableList(@RequestParam String phone) {
        return Result.success(loginService.availableList(phone));
    }

    @PostMapping("/available_list_check")
    @ApiOperation("可用租户列表")
    @SherlyLog(noRecord = true)
    public Result<List<LoginTenantVO>> availableListCheck(@RequestBody LoginDTO dto) {
        return Result.success(loginService.availableListCheck(dto));
    }

    @PutMapping("/login_change")
    @ApiOperation("切换登录租户")
    public Result loginChange(@RequestParam String tenantCode, HttpServletRequest request) throws Exception {
        loginService.loginChange(tenantCode, request);
        return Result.success();
    }
}
