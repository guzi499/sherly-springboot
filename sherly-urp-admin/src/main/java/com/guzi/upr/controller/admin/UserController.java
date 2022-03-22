package com.guzi.upr.controller.admin;

import com.guzi.upr.mapper.admin.UserMapper;
import com.guzi.upr.model.admin.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/18
 */
@RestController
@Api(tags = "用户相关")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user")
    @ApiOperation(value = "新增用户")
    public List<User> getAll() {
        return null;
    }
}
