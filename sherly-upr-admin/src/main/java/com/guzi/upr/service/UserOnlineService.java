package com.guzi.upr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.constants.RedisKey;
import com.guzi.upr.security.SherlyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 谷子毅
 * @date 2022/5/18
 */
@Service
public class UserOnlineService {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List list() throws JsonProcessingException {
        Set<String> keys = redisTemplate.keys(RedisKey.GENERATE_USER + "*");

        if (keys == null) {
            return new ArrayList();
        }
        System.out.println(keys);

        List<String> redisStrings = redisTemplate.opsForValue().multiGet(keys);

        for (String redisString : redisStrings) {
            SherlyUserDetails loginUser = OBJECTMAPPER.readValue(redisString, new TypeReference<SherlyUserDetails>() {});
        }

        return null;
    }
}
