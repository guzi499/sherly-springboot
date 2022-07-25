package com.guzi.upr.service;

import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.constants.RedisKey;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.admin.UserOnline;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.security.model.LoginUserDetails;
import com.guzi.upr.security.model.RedisSecurityModel;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.util.JwtUtil;
import com.guzi.upr.util.LogRecordUtil;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.guzi.upr.model.contants.CommonConstants.LOGIN_LOG_SUCCESS;
import static com.guzi.upr.model.contants.CommonConstants.LOGIN_TYPE_PASSWORD;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class LoginService {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserManager userManager;

    @Autowired
    private LogRecordUtil logRecordUtil;

    @Autowired
    private Ip2regionSearcher ip2regionSearcher;


    /**
     * 登录
     * @param dto
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginVO login(LoginDTO dto, HttpServletRequest request) throws Exception {
        // 封装登录参数
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getPhone(), dto.getPassword());

        // 登录校验
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);

        // 获取登录用户信息
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        // redis缓存登录用户信息
        RedisSecurityModel redisSecurityModel = loginUserDetails.getRedisSecurityModel(request);
        UserOnline userOnline = redisSecurityModel.getUserOnline();
        userOnline.setAddress(ip2regionSearcher.getAddress(userOnline.getIp()));
        redisSecurityModel.setUserOnline(userOnline);

        // 权限信息更新到redis
        String redisString = OBJECTMAPPER.writeValueAsString(redisSecurityModel);
        redisTemplate.opsForValue().set(RedisKey.GENERATE_USER + dto.getPhone(), redisString, 6, TimeUnit.HOURS);

        // 更新用户信息
        this.updateUser(loginUserDetails.getUser(), request);

        // 生成key标签作为token内容
        String keyLabel = dto.getPhone() + "#" + System.currentTimeMillis();

        // 生成token返回前端
        LoginVO loginVO = new LoginVO(JwtUtil.generateToken(keyLabel));
        // 记录日志
        logRecordUtil.recordLoginLog(request, dto.getPhone(), LOGIN_LOG_SUCCESS, LOGIN_TYPE_PASSWORD);

        return loginVO;
    }

    /**
     * 更新用户登录信息
     * @param user
     * @param request
     */
    private void updateUser(User user, HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request);
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(ip);
        userManager.updateById(user);
    }

    /**
     * 登出
     */
    public void logout() {

        redisTemplate.delete(RedisKey.GENERATE_USER + SecurityUtil.getPhone());

    }
}
