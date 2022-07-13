package com.guzi.upr.controller;

import com.guzi.upr.model.Result;
import com.guzi.upr.model.dto.EmailConfigDTO;
import com.guzi.upr.model.dto.EmailSendDTO;
import com.guzi.upr.model.vo.EmailConfigVO;
import com.guzi.upr.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 谷子毅
 * @date 2022/6/8
 */
@RestController
@RequestMapping("/api/email")
@Api(tags = "邮件相关")
@Validated
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/get_one")
    @ApiOperation("邮件配置详情")
    public Result<EmailConfigVO> getOne() {
        return Result.success(emailService.getOne());
    }

    @PostMapping("/save_or_update")
    @ApiOperation("邮件配置保存或修改")
    public Result saveOrUpdateOne(@RequestBody @Valid EmailConfigDTO dto) {
        emailService.saveOrUpdate(dto);
        return Result.success();
    }

    @PostMapping("/send")
    @ApiOperation("邮件发送")
    public Result send(@RequestBody @Valid EmailSendDTO dto) {
        emailService.send(dto);
        return Result.success();
    }

}
