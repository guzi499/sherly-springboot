package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.DepartmentInsertDTO;
import com.guzi.upr.model.dto.DepartmentUpdateDTO;
import com.guzi.upr.model.vo.DepartmentVO;
import com.guzi.upr.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 周孟凡
 * @date 2022/3/30
 */
@RestController
@RequestMapping("/api/department")
@Api(tags = "部门相关")
@Validated
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list_tree")
    @PreAuthorize("hasAnyAuthority('department:list_tree')")
    @ApiOperation("查询部门树")
    public Result<List<DepartmentVO>> listTree() {
        return Result.success(departmentService.listTree());
    }

    @PostMapping("/save_one")
    @PreAuthorize("hasAnyAuthority('department:save_one')")
    @ApiOperation("部门新增")
    public Result saveOne(@RequestBody @Valid DepartmentInsertDTO dto) {
        departmentService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update_one")
    @PreAuthorize("hasAnyAuthority('department:update_one')")
    @ApiOperation("部门更新")
    public Result updateOne(@RequestBody @Valid DepartmentUpdateDTO dto) {
        departmentService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @PreAuthorize("hasAnyAuthority('department:remove_one')")
    @ApiOperation("部门删除")
    public Result removeOne(@RequestParam Long departmentId) {
        departmentService.removeOne(departmentId);
        return Result.success();
    }
}
