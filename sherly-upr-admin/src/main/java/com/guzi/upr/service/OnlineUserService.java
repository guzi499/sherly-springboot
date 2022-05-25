package com.guzi.upr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.constants.RedisKey;
import com.guzi.upr.model.admin.OnlineUser;
import com.guzi.upr.model.dto.OnlineUserQueryDTO;
import com.guzi.upr.security.model.RedisSecurityModel;
import com.guzi.upr.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author 谷子毅
 * @date 2022/5/18
 */
@Service
public class OnlineUserService {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<OnlineUser> list(OnlineUserQueryDTO dto) throws JsonProcessingException {
        // 获取所有在线用户的redisKey
        Set<String> keys = redisTemplate.keys(RedisKey.GENERATE_USER + "*");

        // 如果key为空直接返回空
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }

        ArrayList<OnlineUser> result = new ArrayList<>();
        for (String key : keys) {

            String redisString = redisTemplate.opsForValue().get(key);
            RedisSecurityModel redisSecurityModel = OBJECTMAPPER.readValue(redisString, new TypeReference<RedisSecurityModel>() {});
            OnlineUser onlineUser = redisSecurityModel.getOnlineUser();

            // 只查询当前租户下登录用户
            if (!Objects.equals(SecurityUtil.getTenantCode(), onlineUser.getLoginTenantCode())){
                continue;
            }
            // 根据查询条件过滤
            if (dto.getPhone() != null && !Objects.equals(dto.getPhone(), onlineUser.getPhone())) {
                continue;
            }
            result.add(onlineUser);
        }
        return result;
    }
}
