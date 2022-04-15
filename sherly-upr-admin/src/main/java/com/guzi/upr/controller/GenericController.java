package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.service.GenericService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谷子毅
 * @date 2022/4/5
 */
@RestController
@Api(tags = "通用控制器")
@RequestMapping("/api/generic")
public class GenericController {

    @Autowired
    private GenericService genericService;

    @GetMapping("/basic/data")
    @ApiOperation("获取登录基本信息")
    public Result getBasicData() {
        return Result.success(genericService.getBasicData());
    }
}
