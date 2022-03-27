package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.dto.RoleInsertDTO;
import com.guzi.upr.model.dto.RoleUpdateDTO;
import com.guzi.upr.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/list/role")
    @ApiOperation("查询角色列表")
    public Result<List<Role>> list() {
        List<Role> roles = roleService.list();
        return Result.success(roles);
    }

    @PostMapping("/save/one")
    @ApiOperation("新增角色")
    public Result saveOne(@RequestBody RoleInsertDTO dto) {
        
        roleService.saveOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @ApiOperation("删除角色")
    public Result removeOne(@RequestParam Long id) {
        roleService.removeOne(id);
        return Result.success();
    }

    @PutMapping("/update/one")
    @ApiOperation("角色修改")
    public Result updateOne(@RequestBody RoleUpdateDTO dto) {
        roleService.updateOne(dto);
        return Result.success();
    }


}
