package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.service.QiniuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

/**
 * @author 谷子毅
 * @date 2022/5/15
 */
@RestController
@RequestMapping("/api/qiniu")
@Api(tags = "七牛云相关")
public class QiniuController {

    @Autowired
    private QiniuService qiniuService;


    @ApiOperation("文件上传")
    @PostMapping("/upload/one")
    public Result<String> upload(@RequestParam MultipartFile file){
        return Result.success(qiniuService.upload(file));
    }

    @ApiOperation("文件下载")
    @GetMapping("/download/one")
    public Result download(@RequestParam String fileName) throws UnsupportedEncodingException {

        return Result.success(qiniuService.download(fileName));
    }
}
