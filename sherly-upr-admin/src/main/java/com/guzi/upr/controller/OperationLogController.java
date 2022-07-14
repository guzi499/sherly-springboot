package com.guzi.upr.controller;

import com.guzi.upr.log.service.OperationLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@RestController
@RequestMapping("/api/menu")
@Api(tags = "操作日志相关")
@Validated
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;
}
