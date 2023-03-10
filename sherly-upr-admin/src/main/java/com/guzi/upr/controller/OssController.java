package com.guzi.upr.controller;

import cn.hutool.core.io.IoUtil;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.OssFilePageDTO;
import com.guzi.upr.model.vo.OssFilePageVO;
import com.guzi.upr.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author 谷子毅
 * @date 2022/6/26
 */
@RestController
@RequestMapping("/api/oss")
@Api(tags = "对象存储相关")
@Validated
public class OssController {

    @Autowired
    private OssService ossService;

    @GetMapping("/list_page")
    @PreAuthorize("hasAnyAuthority('oss:list_page')")
    @ApiOperation("文件分页")
    public Result<PageResult<OssFilePageVO>> listPage(OssFilePageDTO dto) {
        return Result.success(ossService.listPage(dto));
    }

    @PostMapping("/upload_one")
    @PreAuthorize("hasAnyAuthority('oss:upload_one')")
    @ApiOperation("文件上传")
    public Result<String> uploadOne(@RequestParam MultipartFile file, @RequestParam String path) throws Exception {
        return Result.success(ossService.uploadOne(file.getBytes(), path));
    }

    @GetMapping("/download_one")
    @PreAuthorize("hasAnyAuthority('oss:download_one')")
    @ApiOperation("文件下载")
    public void downloadOne(HttpServletResponse response, @RequestParam String path) throws Exception {
        byte[] fileBytes = ossService.downloadOne(path);
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(path, "UTF-8"));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        IoUtil.write(response.getOutputStream(), false, fileBytes);
    }

    @GetMapping("/access_url")
    @PreAuthorize("hasAnyAuthority('oss:access_url')")
    @ApiOperation("文件链接")
    public Result<String> accessUrl(@RequestParam String path) throws Exception {
        return Result.success(ossService.accessUrl(path));
    }

    @DeleteMapping("/remove_one")
    @PreAuthorize("hasAnyAuthority('oss:remove_one')")
    @ApiOperation("文件删除")
    public Result removeOne(@RequestParam Long fileId) throws Exception {
        ossService.removeOne(fileId);
        return Result.success();
    }
}
