package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.RoleInsertDTO;
import com.guzi.upr.model.dto.RolePageDTO;
import com.guzi.upr.model.dto.RoleUpdateDTO;
import com.guzi.upr.model.vo.RolePageVO;
import com.guzi.upr.model.vo.RoleVO;
import com.guzi.upr.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@RestController
@RequestMapping("/api/role")
@Api(tags = "角色相关")
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list/page")
    @PreAuthorize("hasAnyAuthority('role:list:page')")
    @ApiOperation("角色分页")
    public Result<PageResult<RolePageVO>> list(RolePageDTO dto) {
        return Result.success(roleService.listPage(dto));
    }

    @GetMapping("/get/one")
    @PreAuthorize("hasAnyAuthority('role:get:one')")
    @ApiOperation("角色详情")
    public Result<RoleVO> getOne(@RequestParam Long roleId) {
        return Result.success(roleService.getOne(roleId));
    }

    @PostMapping("/save/one")
    @PreAuthorize("hasAnyAuthority('role:save:one')")
    @ApiOperation("角色新增")
    public Result saveOne(@RequestBody @Valid RoleInsertDTO dto) {
        roleService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update/one")
    @PreAuthorize("hasAnyAuthority('role:update:one')")
    @ApiOperation("角色更新")
    public Result updateOne(@RequestBody @Valid RoleUpdateDTO dto) {
        roleService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @PreAuthorize("hasAnyAuthority('role:remove:one')")
    @ApiOperation("角色删除")
    public Result removeOne(@RequestParam Long roleId) {
        roleService.removeOne(roleId);
        return Result.success();
    }

}
