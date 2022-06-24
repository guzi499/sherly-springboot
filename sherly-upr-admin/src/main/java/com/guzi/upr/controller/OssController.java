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
 * @date 2022/5/15
 */
@RestController
@RequestMapping("/api/oss")
@Api(tags = "对象存储相关")
public class OssController {

    @Autowired
    private OssService ossService;


    @ApiOperation("文件上传")
    @PostMapping("/upload/one")
    public Result<String> upload(@RequestParam MultipartFile file){
        return Result.success(ossService.upload(file));
    }

    @ApiOperation("文件下载")
    @GetMapping("/download/one")
    public Result download(@RequestParam String fileName) {

        return Result.success(ossService.download(fileName));
    }
}
