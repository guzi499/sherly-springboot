package com.guzi.sherly.controller;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.TenantInsertDTO;
import com.guzi.sherly.model.dto.TenantMenuUpdateDTO;
import com.guzi.sherly.model.dto.TenantPageDTO;
import com.guzi.sherly.model.dto.TenantUpdateDTO;
import com.guzi.sherly.model.vo.TenantPageVO;
import com.guzi.sherly.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/22
 */
@RestController
@RequestMapping("/api/tenant")
@Api(tags = "租户相关")
@Validated
public class TenantController {

    @Resource
    private TenantService tenantService;

    @GetMapping("/list_page")
    @PreAuthorize("hasAnyAuthority('tenant:list_page')")
    @ApiOperation("租户分页")
    public Result<PageResult<TenantPageVO>> listPage(TenantPageDTO dto) {
        return Result.success(tenantService.listPage(dto));
    }


    @PostMapping("/save_one")
    @PreAuthorize("hasAnyAuthority('tenant:save_one')")
    @ApiOperation("租户新增")
    public Result saveOne(@RequestBody @Valid TenantInsertDTO dto) {
        tenantService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update_one")
    @PreAuthorize("hasAnyAuthority('tenant:update_one')")
    @ApiOperation("租户更新")
    public Result updateOne(@RequestBody @Valid TenantUpdateDTO dto) {
        tenantService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @PreAuthorize("hasAnyAuthority('tenant:remove_one')")
    @ApiOperation("租户删除")
    public Result removeOne(@RequestParam Long tenantId) {
        tenantService.removeOne(tenantId);
        return Result.success();
    }

    @PutMapping("/update_menu")
    @PreAuthorize("hasAnyAuthority('tenant:update_menu')")
    @ApiOperation("租户菜单更新")
    public Result updateMenu(@RequestBody @Valid TenantMenuUpdateDTO dto) {
        tenantService.updateMenu(dto);
        return Result.success();
    }

    @GetMapping("/list_menu")
    @PreAuthorize("hasAnyAuthority('tenant:list_menu')")
    @ApiOperation("租户菜单列表")
    public Result<List<Long>> listMenu(@RequestParam Long tenantId) {
        return Result.success(tenantService.listMenu(tenantId));
    }

    @GetMapping("/list_export")
    @ApiOperation(value = "租户导出", produces = "application/octet-stream")
    public void listExport(HttpServletResponse response) {
        tenantService.listExport(response);
    }
}
