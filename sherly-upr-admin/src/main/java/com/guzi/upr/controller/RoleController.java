package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.RoleInsertDTO;
import com.guzi.upr.model.dto.RolePageDTO;
import com.guzi.upr.model.dto.RoleUpdateDTO;
import com.guzi.upr.service.RoleService;
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
@RequestMapping("/role")
@Api(tags = "角色相关")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list/page")
    @ApiOperation("角色分页")
    public Result<PageResult> list(RolePageDTO dto) {
        return Result.success(roleService.listPage(dto));
    }

    @GetMapping("/get/one")
    @ApiOperation("角色详情")
    public Result getOne(@RequestParam Long roleId) {
        return Result.success(roleService.getOne(roleId));
    }

    @PostMapping("/save/one")
    @ApiOperation("角色新增")
    public Result saveOne(@RequestBody RoleInsertDTO dto) {
        roleService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update/one")
    @ApiOperation("角色更新")
    public Result updateOne(@RequestBody RoleUpdateDTO dto) {
        roleService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @ApiOperation("角色删除")
    public Result removeOne(@RequestParam Long roleId) {
        roleService.removeOne(roleId);
        return Result.success();
    }

}
