package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.TenantInsertDTO;
import com.guzi.upr.model.dto.TenantPageDTO;
import com.guzi.upr.model.dto.TenantUpdateDTO;
import com.guzi.upr.model.vo.TenantPageVO;
import com.guzi.upr.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/list/page")
    @PreAuthorize("hasAnyAuthority('tenant:list:page')")
    @ApiOperation("租户分页")
    public Result<PageResult<TenantPageVO>> listPage(TenantPageDTO dto) {
        return Result.success(tenantService.listPage(dto));
    }


    @PostMapping("/save/one")
    @PreAuthorize("hasAnyAuthority('tenant:save:one')")
    @ApiOperation("租户新增")
    public Result saveOne(@RequestBody @Valid TenantInsertDTO dto) {
        tenantService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update/one")
    @PreAuthorize("hasAnyAuthority('tenant:update:one')")
    @ApiOperation("租户更新")
    public Result updateOne(@RequestBody @Valid TenantUpdateDTO dto) {
        tenantService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @PreAuthorize("hasAnyAuthority('tenant:remove:one')")
    @ApiOperation("租户删除")
    public Result removeOne(@RequestParam Long id) {
        tenantService.removeOne(id);
        return Result.success();
    }
}
