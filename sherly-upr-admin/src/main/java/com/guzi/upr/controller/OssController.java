package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 谷子毅
 * @date 2022/6/26
 */
@RestController
@RequestMapping("/api/oss")
@Api(tags = "对象存储相关")
public class OssController {

    @Autowired
    private OssService ossService;

    @GetMapping("/list/page")
    @ApiOperation("文件分页")
    public Result listPage() {
        return Result.success();
    }

    @PostMapping("/upload/one")
    @ApiOperation("文件上传")
    public Result<String> uploadOne(@RequestParam MultipartFile file, @RequestParam String path) throws Exception {
        return Result.success(ossService.uploadOne(file.getBytes(), path));
    }

    @GetMapping("/download/one")
    @ApiOperation("文件下载")
    public Result downloadOne(@RequestParam String path) {
        return Result.success();
    }

    @DeleteMapping("/remove/one")
    @ApiOperation("文件删除")
    public Result removeOne() {
        return Result.success();
    }
}
