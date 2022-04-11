package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.DepartmentInsertDTO;
import com.guzi.upr.model.dto.DepartmentUpdateDTO;
import com.guzi.upr.model.vo.DepartmentVO;
import com.guzi.upr.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 周孟凡
 * @date 2022/3/30
 */
@RestController
@RequestMapping("/department")
@Api(tags = "部门相关")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list/tree")
    @ApiOperation("查询部门树")
    public Result<List<DepartmentVO>> listTree() {
        return Result.success(departmentService.listTree());
    }

    @PostMapping("/save/one")
    @ApiOperation("部门新增")
    public Result saveOne(@RequestBody DepartmentInsertDTO dto) {
        departmentService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update/one")
    @ApiOperation("部门更新")
    public Result updateOne(@RequestBody DepartmentUpdateDTO dto) {
        departmentService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @ApiOperation("部门删除")
    public Result removeOne(@RequestParam Long departmentId) {
        departmentService.removeOne(departmentId);
        return Result.success();
    }
}
