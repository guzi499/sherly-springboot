package com.guzi.sherly.controller;

import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.EmailConfigDTO;
import com.guzi.sherly.model.dto.EmailSendDTO;
import com.guzi.sherly.model.vo.EmailConfigVO;
import com.guzi.sherly.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private EmailService emailService;

    @GetMapping("/get_one")
    @PreAuthorize("hasAnyAuthority('email:get_one')")
    @ApiOperation("邮件配置详情")
    public Result<EmailConfigVO> getOne() {
        return Result.success(emailService.getOne());
    }

    @PostMapping("/save_or_update")
    @PreAuthorize("hasAnyAuthority('email:save_or_update')")
    @ApiOperation("邮件配置保存或修改")
    public Result saveOrUpdateOne(@RequestBody @Valid EmailConfigDTO dto) {
        emailService.saveOrUpdate(dto);
        return Result.success();
    }

    @PostMapping("/send")
    @PreAuthorize("hasAnyAuthority('email:send')")
    @ApiOperation("邮件发送")
    public Result send(@RequestBody @Valid EmailSendDTO dto) {
        emailService.send(dto);
        return Result.success();
    }

}
