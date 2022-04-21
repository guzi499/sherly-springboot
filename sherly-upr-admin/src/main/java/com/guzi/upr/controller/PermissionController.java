package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.PermissionInsertDTO;
import com.guzi.upr.model.dto.PermissionUpdateDTO;
import com.guzi.upr.model.vo.PermissionVO;
import com.guzi.upr.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@RestController
@RequestMapping("/api/permission")
@Api(tags = "权限相关")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list/tree")
    @ApiOperation("查询权限树")
    public Result<List<PermissionVO>> listTree() {
        return Result.success(permissionService.listTree());
    }

    @PostMapping("/save/one")
    @ApiOperation("权限新增")
    public Result saveOne(@RequestBody @Validated PermissionInsertDTO dto) {
        permissionService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update/one")
    @ApiOperation("权限更新")
    public Result updateOne(@RequestBody @Validated PermissionUpdateDTO dto) {
        permissionService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @ApiOperation("权限删除")
    public Result removeOne(@RequestParam Long permissionId) {
        permissionService.removeOne(permissionId);
        return Result.success();
    }
}
