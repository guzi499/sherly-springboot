package com.guzi.sherly.controller;

import com.guzi.sherly.admin.tenant.dto.*;
import com.guzi.sherly.admin.tenant.vo.TenantPackagePageVO;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.common.model.Result;
import com.guzi.sherly.service.TenantPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/11/15
 */
@RestController
@RequestMapping("/api/tenant_package")
@Api(tags = "租户套餐相关")
@Validated
public class TenantPackageController {

    @Resource
    private TenantPackageService tenantPackageService;

    @GetMapping("/list_page")
    @ApiOperation(value = "租户套餐分页")
    public Result<PageResult<TenantPackagePageVO>> listPage(TenantPackagePageDTO dto) {
        return Result.success(tenantPackageService.listPage(dto));
    }

    @PostMapping("/save_one")
    @ApiOperation(value = "租户套餐新增")
    public Result saveOne(@RequestBody @Valid TenantPackageInsertDTO dto) {
        tenantPackageService.saveOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @ApiOperation(value = "租户套餐删除")
    public Result removeOne(@RequestParam Long tenantPackageId) {
        tenantPackageService.removeOne(tenantPackageId);
        return Result.success();
    }

    @PutMapping("/update_one")
    @ApiOperation(value = "租户套餐更新")
    public Result updateOne(@RequestBody @Valid TenantPackageUpdateDTO dto) {
        tenantPackageService.updateOne(dto);
        return Result.success();
    }

    @GetMapping("/list_all")
    @ApiOperation("租户套餐查询")
    public Result<List> listAll(TenantPackageSelectDTO dto) {
        return Result.success(tenantPackageService.listAll(dto));
    }

    @GetMapping("/list_menu")
    @ApiOperation("租户套餐菜单列表")
    public Result<List<Long>> listMenu(@RequestParam Long tenantPackageId) {
        return Result.success(tenantPackageService.listMenu(tenantPackageId));
    }

    @PutMapping("update_menu")
    @ApiOperation("租户套餐菜单更新")
    public Result updateMenu(@RequestBody @Valid TenantPackageMenuUpdateDTO dto) {
        tenantPackageService.updateMenu(dto);
        return Result.success();
    }

    @PutMapping("/ban_one")
    @ApiOperation(value = "租户套餐禁用/启用")
    public Result banOne(@RequestParam Long tenantPackageId, @RequestParam Integer enable) {
        tenantPackageService.banOne(tenantPackageId, enable);
        return Result.success();
    }
}
