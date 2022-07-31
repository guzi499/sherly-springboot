package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.TenantInsertDTO;
import com.guzi.upr.model.dto.TenantMenuUpdateDTO;
import com.guzi.upr.model.dto.TenantPageDTO;
import com.guzi.upr.model.dto.TenantUpdateDTO;
import com.guzi.upr.model.exception.BizException;
import com.guzi.upr.model.vo.TenantPageVO;
import com.guzi.upr.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.guzi.upr.model.exception.enums.AdminErrorEnum.DELETE_TENANT_ERROR;

/**
 * @author 谷子毅
 * @date 2022/3/22
 */
@RestController
@RequestMapping("/api/tenant")
@Api(tags = "租户相关")
@Validated
public class TenantController {

    @Autowired
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
        if (Objects.equals(tenantId, 1L)) {
            throw new BizException(DELETE_TENANT_ERROR);
        }
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
}
