package com.guzi.sherly.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.sherly.admin.errorcode.dao.ErrorCodeDao;
import com.guzi.sherly.admin.errorcode.dto.ErrorCodeInsertDTO;
import com.guzi.sherly.admin.errorcode.dto.ErrorCodePageDTO;
import com.guzi.sherly.admin.errorcode.dto.ErrorCodeUpdateDTO;
import com.guzi.sherly.admin.errorcode.model.ErrorCodeDO;
import com.guzi.sherly.admin.errorcode.vo.ErrorCodePageVO;
import com.guzi.sherly.admin.module.dao.ModuleDao;
import com.guzi.sherly.admin.module.model.ModuleDO;
import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.common.model.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.ERROR_REPEAT;

/**
 * @author 李仁杰
 * @date 2022/9/1
 */
@Service
public class ErrorCodeService {

    @Resource
    private ErrorCodeDao errorCodeDao;

    @Resource
    private ModuleDao moduleDao;

    /**
     * 错误新增
     * @param dto
     */
    public void saveOne(ErrorCodeInsertDTO dto) {
        // 查重
        ErrorCodeDO one = errorCodeDao.getByErrorCode(dto.getErrorCode());
        if (one != null) {
            throw new BizException(ERROR_REPEAT);
        }

        ErrorCodeDO errorCodeDO = new ErrorCodeDO();
        BeanUtils.copyProperties(dto, errorCodeDO);
        errorCodeDao.save(errorCodeDO);
    }

    /**
     * 错误更新
     * @param dto
     */
    public void updateOne(ErrorCodeUpdateDTO dto) {
        ErrorCodeDO errorCodeDO = new ErrorCodeDO();
        BeanUtils.copyProperties(dto, errorCodeDO);
        errorCodeDao.updateById(errorCodeDO);
    }

    /**
     * 错误删除
     * @param errorId
     */
    public void removeOne(Integer errorId) {
        errorCodeDao.removeById(errorId);
    }

    /**
     * 错误查询
     * @param dto
     * @return
     */
    public PageResult<ErrorCodePageVO> listPage(ErrorCodePageDTO dto) {
        IPage<ErrorCodeDO> page = errorCodeDao.listPage(dto);

        List<Integer> moduleIds = page.getRecords().stream().map(ErrorCodeDO::getModuleId).collect(Collectors.toList());
        List<ModuleDO> moduleDOs = moduleDao.listByIds(moduleIds);
        Map<Integer, String> idMapCode = moduleDOs.stream().collect(Collectors.toMap(ModuleDO::getModuleId, ModuleDO::getModuleCode));

        List<ErrorCodePageVO> result = page.getRecords().stream().map(e -> {
            ErrorCodePageVO vo = new ErrorCodePageVO();
            BeanUtils.copyProperties(e, vo);
            vo.setModuleCode(idMapCode.get(e.getModuleId()));
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }
}
