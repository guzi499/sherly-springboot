package com.guzi.upr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.constants.RedisKey;
import com.guzi.upr.model.admin.UserOnline;
import com.guzi.upr.model.dto.UserOnlineSelectDTO;
import com.guzi.upr.model.vo.UserOnlineSelectVO;
import com.guzi.upr.security.model.RedisSecurityModel;
import com.guzi.upr.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/5/18
 */
@Service
public class UserOnlineService {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 在线用户列表
     * @param dto
     * @return
     * @throws JsonProcessingException
     */
    public List<UserOnlineSelectVO> listAll(UserOnlineSelectDTO dto) throws JsonProcessingException {
        // 获取所有在线用户的redisKey
        Set<String> keys = redisTemplate.keys(RedisKey.GENERATE_USER + "*");

        // 如果key为空直接返回空
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }

        List<UserOnline> result = new ArrayList<>();
        for (String key : keys) {

            String redisString = redisTemplate.opsForValue().get(key);
            RedisSecurityModel redisSecurityModel = OBJECTMAPPER.readValue(redisString, new TypeReference<RedisSecurityModel>() {});
            UserOnline userOnline = redisSecurityModel.getUserOnline();

            // 只查询当前租户下登录用户
            if (!Objects.equals(SecurityUtil.getTenantCode(), userOnline.getLoginTenantCode())){
                continue;
            }
            // 根据查询条件过滤
            if (dto.getPhone() != null && !Objects.equals(dto.getPhone(), userOnline.getPhone())) {
                continue;
            }
            result.add(userOnline);
        }

        return result.stream().map(e -> {
            UserOnlineSelectVO vo = new UserOnlineSelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 强制退出
     * @param phone
     */
    public void forceQuit(String phone) {
        redisTemplate.delete(RedisKey.GENERATE_USER + phone);
    }
}
