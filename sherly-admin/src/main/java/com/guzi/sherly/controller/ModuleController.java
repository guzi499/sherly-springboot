package com.guzi.sherly.controller;

import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.ModuleInsertDTO;
import com.guzi.sherly.model.dto.ModuleUpdateDTO;
import com.guzi.sherly.model.vo.ModuleVO;
import com.guzi.sherly.service.ModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "模块相关")
@RequestMapping("/api/module")
@Validated
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping("/list_tree")
    @ApiOperation("查询模块树")
    public Result<List<ModuleVO>> listTree() {
        return Result.success(moduleService.listTree());
    }

    @PostMapping("/save_one")
    @ApiOperation("模块新增")
    public Result saveOne(@RequestBody @Valid ModuleInsertDTO dto) {
        moduleService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update_one")
    @ApiOperation("模块更新")
    public Result updateOne(@RequestBody @Valid ModuleUpdateDTO dto) {
        moduleService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @ApiOperation("模块删除")
    public Result removeOne(@RequestParam Integer moduleId) {
        moduleService.removeOne(moduleId);
        return Result.success();
    }
}
