package com.guzi.sherly.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guzi.sherly.constants.RedisKey;
import com.guzi.sherly.model.admin.UserOnline;
import com.guzi.sherly.model.dto.UserOnlineSelectDTO;
import com.guzi.sherly.model.vo.UserOnlineSelectVO;
import com.guzi.sherly.modules.security.model.RedisSecurityModel;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/5/18
 */
@Service
public class UserOnlineService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 在线用户列表
     * @param dto
     * @return
     * @throws JsonProcessingException
     */
    @SneakyThrows
    public List<UserOnlineSelectVO> listAll(UserOnlineSelectDTO dto) {
        // 获取所有在线用户的redisKey
        Set<String> keys = redisTemplate.keys(RedisKey.SESSION_ID + "*");

        // 如果key为空直接返回空
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }

        List<UserOnlineSelectVO> result = new ArrayList<>();
        for (String key : keys) {

            RedisSecurityModel redisSecurityModel = (RedisSecurityModel) redisTemplate.opsForValue().get(key);
            if (redisSecurityModel == null) {
                continue;
            }
            String sessionId = key.split(":")[1];
            UserOnline userOnline = redisSecurityModel.getUserOnline();

            // 只查询当前租户下登录用户
            if (!Objects.equals(SecurityUtil.getTenantCode(), userOnline.getLoginTenantCode())){
                continue;
            }
            // 根据查询条件过滤
            if (dto.getPhone() != null && !userOnline.getPhone().contains(dto.getPhone())) {
                continue;
            }
            UserOnlineSelectVO vo = new UserOnlineSelectVO();
            BeanUtils.copyProperties(userOnline, vo);
            vo.setSessionId(sessionId);
            result.add(vo);
        }
        return result.stream().sorted(Comparator.comparing(UserOnlineSelectVO::getLoginTime).reversed()).collect(Collectors.toList());
    }

    /**
     * 强制退出
     * @param sessionId
     */
    public void forceQuit(String sessionId) {
        redisTemplate.delete(RedisKey.SESSION_ID + sessionId);
    }
}
