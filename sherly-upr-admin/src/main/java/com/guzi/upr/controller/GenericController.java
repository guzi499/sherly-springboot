package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.vo.BasicInfoVO;
import com.guzi.upr.model.vo.DepartmentSelectVO;
import com.guzi.upr.model.vo.MenuSelectVO;
import com.guzi.upr.model.vo.RoleSelectVO;
import com.guzi.upr.service.GenericService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/4/5
 */
@RestController
@Api(tags = "通用控制器")
@RequestMapping("/api/generic")
@Validated
public class GenericController {

    @Autowired
    private GenericService genericService;

    @GetMapping("/basic/data")
    @ApiOperation("获取登录基本信息")
    public Result<BasicInfoVO> getBasicData() throws Exception {
        return Result.success(genericService.getBasicData());
    }

    @GetMapping("basic/menu")
    @ApiOperation("菜单下拉框")
    public Result<List<MenuSelectVO>> getBasicMenu() {
        return Result.success(genericService.getBasicMenu());
    }

    @GetMapping("basic/role")
    @ApiOperation("角色下拉框")
    public Result<List<RoleSelectVO>> getBasicRole() {
        return Result.success(genericService.getBasicRole());
    }

    @GetMapping("basic/department")
    @ApiOperation("部门下拉框")
    public Result<List<DepartmentSelectVO>> getBasicDepartment() {
        return Result.success(genericService.getBasicDepartment());
    }
}
