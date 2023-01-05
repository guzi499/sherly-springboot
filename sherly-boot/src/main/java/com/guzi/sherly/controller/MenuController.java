package com.guzi.sherly.controller;

import com.guzi.sherly.admin.menu.dto.MenuInsertDTO;
import com.guzi.sherly.admin.menu.dto.MenuUpdateDTO;
import com.guzi.sherly.admin.menu.vo.MenuVO;
import com.guzi.sherly.common.model.Result;
import com.guzi.sherly.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@RestController
@RequestMapping("/api/menu")
@Api(tags = "菜单相关")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/list_tree")
    @ApiOperation("查询菜单树")
    public Result<List<MenuVO>> listTree() {
        return Result.success(menuService.listTree());
    }

    @PostMapping("/save_one")
    @PreAuthorize("hasAnyAuthority('menu:save_one')")
    @ApiOperation("菜单新增")
    public Result saveOne(@RequestBody @Valid MenuInsertDTO dto) {
        menuService.saveOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @PreAuthorize("hasAnyAuthority('menu:remove_one')")
    @ApiOperation("菜单删除")
    public Result removeOne(@RequestParam Long menuId) {
        menuService.removeOne(menuId);
        return Result.success();
    }

    @PutMapping("/update_one")
    @PreAuthorize("hasAnyAuthority('menu:update_one')")
    @ApiOperation("菜单修改")
    public Result updateOne(@RequestBody @Valid MenuUpdateDTO dto) {
        menuService.updateOne(dto);
        return Result.success();
    }
}
