package com.guzi.upr.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.upr.log.manager.OperationLogManager;
import com.guzi.upr.log.model.OperationLog;
import com.guzi.upr.log.service.OperationLogService;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.OperationLogPageDTO;
import com.guzi.upr.model.dto.UserSelectDTO;
import com.guzi.upr.model.vo.OperationLogPageVO;
import com.guzi.upr.model.vo.OperationLogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogManager operationLogManager;

    @Autowired
    private UserManager userManager;

    @Override
    public void saveOne(OperationLog operationLog) {
        operationLogManager.save(operationLog);
    }

    @Override
    public PageResult<OperationLogPageVO> listPage(OperationLogPageDTO dto) {
        if (StrUtil.isNotBlank(dto.getRealName())) {
            UserSelectDTO userSelectDTO = new UserSelectDTO();
            userSelectDTO.setRealName(dto.getRealName());
            List<Long> userIds = userManager.listAll(userSelectDTO).stream().map(User::getUserId).collect(Collectors.toList());
            dto.setUserIds(userIds);
        }
        Page<OperationLog> page = operationLogManager.listPage(dto);
        List<Long> userIds = page.getRecords().stream().map(OperationLog::getCreateUserId).collect(Collectors.toList());
        Map<Long, String> userMap = userManager.listByIds(userIds).stream().collect(Collectors.toMap(User::getUserId, User::getRealName));
        List<OperationLogPageVO> result = page.getRecords().stream().map(e -> {
            OperationLogPageVO vo = new OperationLogPageVO();
            BeanUtils.copyProperties(e, vo);
            vo.setOperateUser(userMap.get(e.getCreateUserId()));
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
