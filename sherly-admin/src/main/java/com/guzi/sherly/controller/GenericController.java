package com.guzi.sherly.controller;

import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.vo.BasicInfoVO;
import com.guzi.sherly.service.GenericService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class GenericController {

    @Autowired
    private GenericService genericService;

    @GetMapping("/basic_data")
    @ApiOperation("用户登录基本信息")
    public Result<BasicInfoVO> getBasicData() throws Exception {
        return Result.success(genericService.getBasicData());
    }

    @GetMapping("heart_beat")
    @ApiOperation("心跳检测")
    public Result heartBeat() {
        return Result.success();
    }
}
