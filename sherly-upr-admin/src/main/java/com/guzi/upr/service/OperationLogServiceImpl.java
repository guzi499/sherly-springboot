package com.guzi.upr.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.upr.log.manager.OperationLogManager;
import com.guzi.upr.log.model.OperationLog;
import com.guzi.upr.log.service.OperationLogService;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.dto.OperationLogPageDTO;
import com.guzi.upr.model.vo.OperationLogPageVO;
import com.guzi.upr.model.vo.OperationLogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogManager operationLogManager;

    @Override
    public void saveOne(OperationLog operationLog) {
        operationLogManager.save(operationLog);
    }

    @Override
    public PageResult<OperationLogPageVO> listPage(OperationLogPageDTO dto) {
        Page<OperationLog> page = operationLogManager.listPage(dto);

        List<OperationLogPageVO> result = page.getRecords().stream().map(e -> {
            OperationLogPageVO vo = new OperationLogPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public OperationLogVO getOne(Long logId) {
        OperationLog log = operationLogManager.getById(logId);
        OperationLogVO vo = new OperationLogVO();
        BeanUtils.copyProperties(log, vo);
        return vo;
    }

    @Override
    public void removeAll() {
        operationLogManager.removeAll();
    }
}
