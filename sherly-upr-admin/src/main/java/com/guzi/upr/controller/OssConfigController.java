package com.guzi.upr.controller;

import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.OssConfigInsertDTO;
import com.guzi.upr.model.dto.OssConfigPageDTO;
import com.guzi.upr.model.dto.OssConfigUpdateDTO;
import com.guzi.upr.model.vo.OssConfigPageVO;
import com.guzi.upr.model.vo.OssConfigVO;
import com.guzi.upr.service.OssConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@RestController
@RequestMapping("/api/oss/config")
@Api(tags = "对象存储配置相关")
@Validated
public class OssConfigController {

    @Autowired
    private OssConfigService ossConfigService;


    @GetMapping("/list/page")
    @ApiOperation("对象存储配置分页")
    public Result<PageResult<OssConfigPageVO>> listPage(OssConfigPageDTO dto) {
        return Result.success(ossConfigService.listPage(dto));
    }

    @GetMapping("/get/one")
    @ApiOperation("对象存储配置详情")
    public Result<OssConfigVO> getOne(@RequestParam Long configId) {
        return Result.success(ossConfigService.getOne(configId));
    }

    @PostMapping("/save/one")
    @ApiOperation("对象存储配置新增")
    public Result saveOne(@RequestBody @Validated OssConfigInsertDTO dto) throws Exception {
        ossConfigService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update/one")
    @ApiOperation("对象存储配置更新")
    public Result updateOne(@RequestBody @Validated OssConfigUpdateDTO dto) throws Exception {
        ossConfigService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @ApiOperation("对象存储配置删除")
    public Result removeOne(@RequestParam Long configId) {
        ossConfigService.removeOne(configId);
        return Result.success();
    }
}
