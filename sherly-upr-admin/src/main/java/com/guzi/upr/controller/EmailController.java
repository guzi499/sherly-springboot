package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.EmailConfigDTO;
import com.guzi.upr.model.dto.EmailSendDTO;
import com.guzi.upr.model.vo.EmailConfigVO;
import com.guzi.upr.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@RestController
@RequestMapping("/api/email")
@Api(tags = "邮件相关")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/get/one")
    @ApiOperation("邮件配置详情")
    public Result<EmailConfigVO> getOne() {
        return Result.success(emailService.getOne());
    }

    @PostMapping("/saveOrUpdate/one")
    @ApiOperation("邮件配置保存或修改")
    public Result saveOrUpdateOne(@RequestBody EmailConfigDTO dto) {
        emailService.saveOrUpdateOne(dto);
        return Result.success();
    }

    @PostMapping("/send")
    @ApiOperation("邮件发送")
    public Result send(@RequestBody EmailSendDTO dto) {
        emailService.send(dto);
        return Result.success();
    }

}
