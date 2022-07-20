package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.RoleInsertDTO;
import com.guzi.upr.model.dto.RolePageDTO;
import com.guzi.upr.model.dto.RoleSelectDTO;
import com.guzi.upr.model.dto.RoleUpdateDTO;
import com.guzi.upr.model.vo.RolePageVO;
import com.guzi.upr.model.vo.RoleSelectVO;
import com.guzi.upr.model.vo.RoleVO;
import com.guzi.upr.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/list_page")
    @PreAuthorize("hasAnyAuthority('role:list_page')")
    @ApiOperation("角色分页")
    public Result<PageResult<RolePageVO>> list(RolePageDTO dto) {
        return Result.success(roleService.listPage(dto));
    }

    @GetMapping("/get_one")
    @PreAuthorize("hasAnyAuthority('role:get_one')")
    @ApiOperation("角色详情")
    public Result<RoleVO> getOne(@RequestParam Long roleId) {
        return Result.success(roleService.getOne(roleId));
    }

    @PostMapping("/save_one")
    @PreAuthorize("hasAnyAuthority('role:save_one')")
    @ApiOperation("角色新增")
    public Result saveOne(@RequestBody @Valid RoleInsertDTO dto) {
        roleService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update_one")
    @PreAuthorize("hasAnyAuthority('role:update_one')")
    @ApiOperation("角色更新")
    public Result updateOne(@RequestBody @Valid RoleUpdateDTO dto) {
        roleService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @PreAuthorize("hasAnyAuthority('role:remove_one')")
    @ApiOperation("角色删除")
    public Result removeOne(@RequestParam Long roleId) {
        roleService.removeOne(roleId);
        return Result.success();
    }

    @GetMapping("/list_all")
    @PreAuthorize("hasAnyAuthority('role:list_all')")
    @ApiOperation("角色查询")
    public Result<List<RoleSelectVO>> listAll(RoleSelectDTO dto) {
        return Result.success(roleService.listAll(dto));
    }
}
