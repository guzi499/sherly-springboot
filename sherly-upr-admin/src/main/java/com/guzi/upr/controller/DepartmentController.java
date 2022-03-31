package com.guzi.upr.controller;

import com.guzi.upr.model.PageQuery;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.DepartmentInsertDTO;
import com.guzi.upr.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: DepartmentController
 * @author: 冰焰
 * @date: 2022/3/30
 * @Version: V1.0
 **/

@RestController
@RequestMapping("/department")
@Api(tags = "部门相关")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list")
    @ApiOperation(value = "获取所有部门")
    public PageResult<User> getAll(PageQuery pageQuery) {
        // 分页
        return departmentService.page(pageQuery);
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增部门")
    public Result saveOne(@RequestBody DepartmentInsertDTO dto) {
        departmentService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update")
    @ApiOperation("更新")
    public Result updateOne(@RequestBody DepartmentInsertDTO dto) {
        departmentService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove")
    @ApiOperation("移除部门")
    public Result removeOne(@RequestParam Long id) {
        departmentService.removeOne(id);
        return Result.success();
    }
}
