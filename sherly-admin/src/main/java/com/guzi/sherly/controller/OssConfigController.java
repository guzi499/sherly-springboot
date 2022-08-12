package com.guzi.sherly.controller;

import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.OssConfigInsertDTO;
import com.guzi.sherly.model.dto.OssConfigPageDTO;
import com.guzi.sherly.model.dto.OssConfigUpdateDTO;
import com.guzi.sherly.model.vo.OssConfigPageVO;
import com.guzi.sherly.model.vo.OssConfigVO;
import com.guzi.sherly.service.OssConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 谷子毅
 * @date 2022/6/24
 */
@RestController
@RequestMapping("/api/oss_config")
@Api(tags = "对象存储配置相关")
@Validated
public class OssConfigController {

    @Autowired
    private OssConfigService ossConfigService;


    @GetMapping("/list_page")
    @PreAuthorize("hasAnyAuthority('oss_config:list_page')")
    @ApiOperation("对象存储配置分页")
    public Result<PageResult<OssConfigPageVO>> listPage(OssConfigPageDTO dto) {
        return Result.success(ossConfigService.listPage(dto));
    }

    @GetMapping("/get_one")
    @PreAuthorize("hasAnyAuthority('oss_config:get_one')")
    @ApiOperation("对象存储配置详情")
    public Result<OssConfigVO> getOne(@RequestParam Long configId) throws Exception {
        return Result.success(ossConfigService.getOne(configId));
    }

    @PutMapping("/enable_one")
    @PreAuthorize("hasAnyAuthority('oss_config:enable_one')")
    @ApiOperation("对象存储配置激活")
    public Result enableOne(@RequestParam Long configId) {
        ossConfigService.enableOne(configId);
        return Result.success();
    }

    @PostMapping("/save_one")
    @PreAuthorize("hasAnyAuthority('oss_config:save_one')")
    @ApiOperation("对象存储配置新增")
    public Result saveOne(@RequestBody @Valid OssConfigInsertDTO dto) throws Exception {
        ossConfigService.saveOne(dto);
        return Result.success();
    }

    @PutMapping("/update_one")
    @PreAuthorize("hasAnyAuthority('oss_config:update_one')")
    @ApiOperation("对象存储配置更新")
    public Result updateOne(@RequestBody @Valid OssConfigUpdateDTO dto) throws Exception {
        ossConfigService.updateOne(dto);
        return Result.success();
    }

    @DeleteMapping("/remove_one")
    @PreAuthorize("hasAnyAuthority('oss_config:remove_one')")
    @ApiOperation("对象存储配置删除")
    public Result removeOne(@RequestParam Long configId) {
        ossConfigService.removeOne(configId);
        return Result.success();
    }
}
