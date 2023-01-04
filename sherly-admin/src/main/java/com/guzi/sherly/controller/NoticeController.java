package com.guzi.sherly.controller;

import com.guzi.sherly.manager.NoticeManager;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.Result;
import com.guzi.sherly.model.dto.NoticeClearListDTO;
import com.guzi.sherly.model.dto.NoticePageDTO;
import com.guzi.sherly.model.vo.NoticePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 谷子毅
 * @date 2022/12/29
 */
@RestController
@Api(tags = "消息相关")
@RequestMapping("/api/module")
@Validated
public class NoticeController {

    @Resource
    private NoticeManager noticeManager;

    @GetMapping("/list_page")
    @ApiOperation("消息分页")
    public Result<PageResult<NoticePageVO>> listPage(NoticePageDTO dto) {
        return Result.success(noticeManager.listPage(dto));
    }

    @PutMapping("/clear_list")
    @ApiOperation("消息部分设置已读")
    public Result clearList(NoticeClearListDTO dto) {
        noticeManager.clearList(dto);
        return Result.success();
    }

    @PutMapping("/clear_all")
    @ApiOperation("消息全部设置已读")
    public Result clearAll() {
        noticeManager.clearAll();
        return Result.success();
    }

}
