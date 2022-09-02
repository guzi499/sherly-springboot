package com.guzi.sherly.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.manager.ErrorCodeManager;
import com.guzi.sherly.manager.ModuleManager;
import com.guzi.sherly.model.PageResult;
import com.guzi.sherly.model.admin.ErrorCode;
import com.guzi.sherly.model.admin.Module;
import com.guzi.sherly.model.dto.ErrorCodeInsertDTO;
import com.guzi.sherly.model.dto.ErrorCodePageDTO;
import com.guzi.sherly.model.dto.ErrorCodeUpdateDTO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.ErrorCodePageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.ERROR_REPEAT;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Service
public class ErrorCodeService {

    @Autowired
    private ErrorCodeManager errorCodeManager;

    @Autowired
    private ModuleManager moduleManager;

    /**
     * 错误新增
     * @param dto
     */
    public void saveOne(ErrorCodeInsertDTO dto) {
        // 查重
        ErrorCode one = errorCodeManager.getByErrorCode(dto.getErrorCode());
        if (one != null) {
            throw new BizException(ERROR_REPEAT);
        }

        ErrorCode errorCode = new ErrorCode();
        BeanUtils.copyProperties(dto, errorCode);
        errorCodeManager.save(errorCode);
    }

    /**
     * 错误更新
     * @param dto
     */
    public void updateOne(ErrorCodeUpdateDTO dto) {
        ErrorCode errorCode = new ErrorCode();
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
    public PageResult<ErrorCodePageVO> listPage(ErrorCodePageDTO dto) {
        IPage<ErrorCode> page = errorCodeManager.listPage(dto);

        List<Integer> moduleIds = page.getRecords().stream().map(ErrorCode::getModuleId).collect(Collectors.toList());
        List<Module> modules = moduleManager.listByIds(moduleIds);
        Map<Integer, String> idMapCode = modules.stream().collect(Collectors.toMap(Module::getModuleId, Module::getModuleCode));

        List<ErrorCodePageVO> result = page.getRecords().stream().map(e -> {
            ErrorCodePageVO vo = new ErrorCodePageVO();
            BeanUtils.copyProperties(e, vo);
            vo.setModuleCode(idMapCode.get(e.getModuleId()));
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }
}
