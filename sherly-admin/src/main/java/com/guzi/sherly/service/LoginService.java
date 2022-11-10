package com.guzi.sherly.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.guzi.sherly.constants.RedisKey;
import com.guzi.sherly.manager.AccountUserManager;
import com.guzi.sherly.manager.TenantManager;
import com.guzi.sherly.manager.UserManager;
import com.guzi.sherly.model.admin.AccountUser;
import com.guzi.sherly.model.admin.Tenant;
import com.guzi.sherly.model.admin.User;
import com.guzi.sherly.model.admin.UserOnline;
import com.guzi.sherly.model.dto.LoginDTO;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.model.vo.LoginTenantVO;
import com.guzi.sherly.model.vo.LoginVO;
import com.guzi.sherly.modules.log.manager.LoginLogManager;
import com.guzi.sherly.modules.log.model.LoginLog;
import com.guzi.sherly.modules.security.model.LoginUserDetails;
import com.guzi.sherly.modules.security.model.RedisSecurityModel;
import com.guzi.sherly.modules.security.model.SecurityModel;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import com.guzi.sherly.util.GlobalPropertiesUtil;
import com.guzi.sherly.util.IpUtil;
import com.guzi.sherly.util.JwtUtil;
import liquibase.pro.packaged.E;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.*;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.*;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class LoginService {

    @Resource
    private AuthenticationProvider authenticationProvider;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private UserManager userManager;

    @Resource
    private AccountUserManager accountUserManager;

    @Resource
    private TenantManager tenantManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private LoginLogManager loginLogManager;

    /**
     * 登录
     * @param dto
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginVO login(LoginDTO dto, HttpServletRequest request) throws Exception {

        // 因为登录前不存在Authentication，必须手动设置特殊操作数据库code，
        SecurityModel securityModel = new SecurityModel();
        securityModel.setTenantCode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityModel, null));

        // 如果登录信息包含租户需要做的处理
        this.dealWithTenantCode(dto);

        // 封装登录参数
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getPhone(), dto.getPassword());

        // 登录校验
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BizException) {
                BizException exception = (BizException) e;
                if (exception.getCode().equals(ERR_USR_PWD.getCode())) {
                    this.recordLoginLog(request, dto.getPhone(), LOGIN_LOG_FAIL, LOGIN_TYPE_PASSWORD);
                } else if (exception.getCode().equals(FORBIDDEN.getCode())) {
                    this.recordLoginLog(request, dto.getPhone(), LOGIN_LOG_DISABLE, LOGIN_TYPE_PASSWORD);
                }
            }
            throw e;
        }

        // 获取登录用户信息
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        this.updateCache(loginUserDetails, null, request);

        // 获取sessionId作为token内容
        String sessionId = loginUserDetails.getSessionId();

        // 生成token返回前端
        LoginVO loginVO = new LoginVO(JwtUtil.generateToken(sessionId));

        // 记录日志
        this.recordLoginLog(request, dto.getPhone(), LOGIN_LOG_SUCCESS, LOGIN_TYPE_PASSWORD);

        return loginVO;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void recordLoginLog(HttpServletRequest request, String username, Integer result, Integer type) {
        LoginLog loginLog = new LoginLog();

        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            UserAgent agent = UserAgentUtil.parse(userAgent);
            loginLog.setOs(agent.getOs().toString());
            loginLog.setBrowser(agent.getBrowser().toString());
        }

        String ip = IpUtil.getIp(request);
        String address = IpUtil.getAddress(ip);

        loginLog.setIp(ip);
        loginLog.setAddress(address);
        loginLog.setUsername(username);
        loginLog.setType(type);
        loginLog.setResult(result);
        loginLog.setCreateTime(new Date());

        this.saveLoginLog(loginLog);
    }

    @Async
    public void saveLoginLog(LoginLog loginLog) {
        loginLogManager.save(loginLog);
    }

    /**
     * 处理登录时携带租户code情况
     * @param dto
     */
    private void dealWithTenantCode(LoginDTO dto) {
        if (StrUtil.isNotBlank(dto.getTenantCode())) {
            Tenant tenant = tenantManager.getByTenantCode(dto.getTenantCode());
            // 如果租户不存在
            if (tenant == null) {
                throw new BizException(TENANT_MISS);
            }

            // 如果在选择租户下无该账号
            AccountUser accountUser = accountUserManager.getByPhone(dto.getPhone());
            List<String> split = StrUtil.split(accountUser.getTenantData(), ",");
            if (!split.contains(dto.getTenantCode())) {
                throw new BizException(NOT_IN_ACCOUNT);
            }

            // 如果选择的租户已过期
            if (tenant.getExpireTime().getTime() <= System.currentTimeMillis()) {
                throw new BizException(TENANT_EXPIRED, tenant.getTenantName());
            }
            // 校验租户是否可用，如果可用，那么最近登陆租户切换成该租户code
            accountUser.setLastLoginTenantCode(dto.getTenantCode());
            accountUserManager.updateById(accountUser);
        }
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
        redisTemplate.delete(RedisKey.SESSION_ID + SecurityUtil.getSessionId());

    }

    /**
     * 可用租户列表
     * @param phone
     * @return
     */
    public List<LoginTenantVO> availableList(String phone) {

        AccountUser accountUser = accountUserManager.getByPhone(phone);
        List<String> tenantCodes = StrUtil.split(accountUser.getTenantData(), ",");
        List<Tenant> tenants = tenantManager.listAvailableByTenantCodes(tenantCodes);
        if (CollectionUtils.isEmpty(tenants)) {
            throw new BizException(NO_TENANT);
        }

        return tenants.stream().map(e -> {
            LoginTenantVO vo = new LoginTenantVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

    }

    /**
     * 切换登录租户
     */
    @Transactional(rollbackFor = Exception.class)
    public void loginChange(String tenantCode, HttpServletRequest request) throws Exception {
        String phone = SecurityUtil.getPhone();
        String sessionId = SecurityUtil.getSessionId();

        SecurityModel securityModel = new SecurityModel();
        securityModel.setTenantCode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityModel, null));
        AccountUser accountUser = accountUserManager.getByPhone(phone);
        accountUser.setLastLoginTenantCode(tenantCode);
        accountUserManager.updateById(accountUser);

        LoginUserDetails loginUserDetails = (LoginUserDetails)userDetailsService.loadUserByUsername(phone);
        this.updateCache(loginUserDetails, sessionId, request);
    }

    public void updateCache(LoginUserDetails loginUserDetails, String sessionId, HttpServletRequest request) throws JsonProcessingException {
        // redis缓存登录用户信息
        RedisSecurityModel redisSecurityModel = loginUserDetails.getRedisSecurityModel(request);
        UserOnline userOnline = redisSecurityModel.getUserOnline();
        userOnline.setAddress(IpUtil.getAddress(userOnline.getIp()));
        redisSecurityModel.setUserOnline(userOnline);
        if (sessionId != null) {
            redisSecurityModel.getSecurityModel().setSessionId(sessionId);
        }

        // 权限信息更新到redis
        String redisString = JSONUtil.toJsonStr(redisSecurityModel);
        redisTemplate.opsForValue().set(RedisKey.SESSION_ID + redisSecurityModel.getSecurityModel().getSessionId(), redisString, 6, TimeUnit.HOURS);

        // 更新用户信息
        this.updateUser(loginUserDetails.getUser(), request);
    }

    /**
     * 可用租户列表
     * @param dto
     * @return
     */
    public List<LoginTenantVO> availableListCheck(LoginDTO dto) {
        AccountUser accountUser = accountUserManager.getByPhone(dto.getPhone());
        if (accountUser == null) {
            throw new BizException(NO_REGISTER);
        }
        if (!passwordEncoder.matches(dto.getPassword(), accountUser.getPassword())) {
            throw new BizException(ERR_USR_PWD);
        }
        List<String> tenantCodes = StrUtil.split(accountUser.getTenantData(), ",");
        List<Tenant> tenants = tenantManager.listAvailableByTenantCodes(tenantCodes);

        if (CollectionUtils.isEmpty(tenants)) {
            throw new BizException(NO_TENANT);
        }

        return tenants.stream().map(e -> {
            LoginTenantVO vo = new LoginTenantVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
