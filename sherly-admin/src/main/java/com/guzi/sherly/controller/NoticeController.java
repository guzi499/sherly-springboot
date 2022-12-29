package com.guzi.sherly.controller;

import com.guzi.sherly.manager.NoticeManager;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@RestController
@Api(tags = "模块相关")
@RequestMapping("/api/module")
@Validated
public class NoticeController {

    @Resource
    private NoticeManager noticeManager;


}
