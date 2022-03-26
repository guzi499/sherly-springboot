package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.PermissionInsertDTO;
import com.guzi.upr.model.dto.PermissionUpdateDTO;
import com.guzi.upr.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@RestController
@RequestMapping("/permission")
@Api(tags = "权限相关")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/save/one")
    @ApiOperation("权限新增")
    public Result saveOne(@RequestBody PermissionInsertDTO dto) {
        permissionService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update/one")
    @ApiOperation("权限更新")
    public Result updateOne(@RequestBody PermissionUpdateDTO dto) {
        permissionService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @ApiOperation("权限删除")
    public Result removeOne(@RequestParam Long id) {
        permissionService.removeOne(id);
        return Result.success();
    }
}
