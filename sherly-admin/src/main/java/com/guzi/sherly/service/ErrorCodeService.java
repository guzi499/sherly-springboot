package com.guzi.sherly.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.manager.ErrorCodeManager;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.admin.ErrorCode;
import com.guzi.sherly.model.dto.ErrorCodeInsertDTO;
import com.guzi.sherly.model.dto.ErrorCodePageDTO;
import com.guzi.sherly.model.dto.ErrorCodeUpdateDTO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.ErrorCodePageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.ERROR_REPEAT;


@Service
public class ErrorCodeService {

    @Autowired
    private ErrorCodeManager errorCodeManager;

    /**
     * 错误新增
     * @param dto
     */
    public void saveOne(ErrorCodeInsertDTO dto) {
        ErrorCode errorCode = new ErrorCode();
        // 查重
        ErrorCode one = errorCodeManager.getErrorCode(dto.getErrorCode());
        if (one != null) {
            throw new BizException(ERROR_REPEAT);
        }
        BeanUtils.copyProperties(dto, errorCode);
        errorCodeManager.save(errorCode);
    }

    /**
     * 错误更新
     * @param dto
     */
    public void updateOne(ErrorCodeUpdateDTO dto) {
        ErrorCode errorCode = new ErrorCode();
        // 查重
        ErrorCode one = errorCodeManager.getErrorCode(dto.getErrorCode());
        if (one != null) {
            throw new BizException(ERROR_REPEAT);
        }
        BeanUtils.copyProperties(dto, errorCode);
        errorCodeManager.updateById(errorCode);
    }

    /**
     * 错误删除
     * @param errorId
     */
    public void removeOne(Integer errorId) {
        errorCodeManager.removeById(errorId);
    }

    /**
     * 错误查询
     * @param dto
     * @return
     */
    public PageResult listPage(ErrorCodePageDTO dto) {
        IPage<ErrorCode> page = errorCodeManager.listPage(dto);

        List<ErrorCodePageVO> result = page.getRecords().stream().map(e -> {
            ErrorCodePageVO vo = new ErrorCodePageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 实时更新模块代码
     * @param moduleId
     * @param moduleCode
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateModuleCode(Integer moduleId, String moduleCode) {
        List<ErrorCode> list = errorCodeManager.getAll(moduleId);
        for (ErrorCode e : list) {
            e.setModuleCode(moduleCode);
        }
        errorCodeManager.updateBatchById(list);
    }
}
