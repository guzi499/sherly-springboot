package com.guzi.sherly.manager;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guzi.sherly.admin.user.dao.UserDao;
import com.guzi.sherly.admin.user.dto.UserSelectDTO;
import com.guzi.sherly.admin.user.model.User;
import com.guzi.sherly.common.model.PageResult;
import com.guzi.sherly.modules.log.dao.OperationLogDao;
import com.guzi.sherly.modules.log.dto.OperationLogPageDTO;
import com.guzi.sherly.modules.log.model.OperationLog;
import com.guzi.sherly.modules.log.service.OperationLogManager;
import com.guzi.sherly.modules.log.vo.OperationLogPageVO;
import com.guzi.sherly.modules.log.vo.OperationLogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class OperationLogManagerImpl implements OperationLogManager {

    @Resource
    private OperationLogDao operationLogDao;

    @Resource
    private UserDao userDao;

    @Override
    public void saveOne(OperationLog operationLog) {
        operationLogDao.save(operationLog);
    }

    @Override
    public PageResult<OperationLogPageVO> listPage(OperationLogPageDTO dto) {
        if (StrUtil.isNotBlank(dto.getRealName())) {
            UserSelectDTO userSelectDTO = new UserSelectDTO();
            userSelectDTO.setRealName(dto.getRealName());
            List<Long> userIds = userDao.listAll(userSelectDTO).stream().map(User::getUserId).collect(Collectors.toList());
            dto.setUserIds(userIds);
        }
        Page<OperationLog> page = operationLogDao.listPage(dto);
        List<Long> userIds = page.getRecords().stream().map(OperationLog::getCreateUserId).collect(Collectors.toList());
        Map<Long, String> userMap = userDao.listByIds(userIds).stream().collect(Collectors.toMap(User::getUserId, User::getRealName));
        List<OperationLogPageVO> result = page.getRecords().stream().map(e -> {
            OperationLogPageVO vo = new OperationLogPageVO();
            BeanUtils.copyProperties(e, vo);
            vo.setOperateUser(userMap.get(e.getCreateUserId()));
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getTotal());
    }

    @Override
    public OperationLogVO getOne(Long logId) {
        OperationLog log = operationLogDao.getById(logId);
        OperationLogVO vo = new OperationLogVO();
        BeanUtils.copyProperties(log, vo);
        return vo;
    }

    @Override
    public void removeAll() {
        operationLogDao.removeAll();
    }
}
