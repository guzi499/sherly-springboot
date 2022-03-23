package com.guzi.upr.controller.admin;

import com.guzi.upr.exception.BizException;
import com.guzi.upr.exception.ResultSystemEnum;
import com.guzi.upr.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/23
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/biz")
    public Result biz() {
        throw new BizException("code", "message");
    }

    @GetMapping("/other")
    public Result other() {
        int a = 2/0;
        return Result.success();
    }

    @GetMapping("/success")
    public Result ok() {
        return Result.success("ok");
    }

    @GetMapping("/enumBiz")
    public Result enumBiz() {
        throw new BizException(ResultSystemEnum.ERROR);
    }

    @GetMapping("/npe")
    public Result npe() {
        String a = null;
        boolean equals = a.equals("2");
        return Result.success();
    }
}
