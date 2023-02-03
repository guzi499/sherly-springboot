package com.guzi.sherly.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.guzi.sherly.admin.accountuser.dao.AccountUserDao;
import com.guzi.sherly.admin.accountuser.model.AccountUserDO;
import com.guzi.sherly.admin.login.dto.LoginDTO;
import com.guzi.sherly.admin.login.vo.LoginTenantVO;
import com.guzi.sherly.admin.login.vo.LoginVO;
import com.guzi.sherly.admin.tenant.dao.TenantDao;
import com.guzi.sherly.admin.tenant.model.TenantDO;
import com.guzi.sherly.admin.user.dao.UserDao;
import com.guzi.sherly.admin.user.model.UserDO;
import com.guzi.sherly.admin.useronline.model.UserOnline;
import com.guzi.sherly.common.constants.RedisKey;
import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.common.util.GlobalPropertiesUtil;
import com.guzi.sherly.common.util.IpUtil;
import com.guzi.sherly.common.util.JwtUtil;
import com.guzi.sherly.manager.LoginLogManager;
import com.guzi.sherly.modules.security.model.LoginUserDetails;
import com.guzi.sherly.modules.security.model.RedisSecurityModel;
import com.guzi.sherly.modules.security.model.SecurityModel;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.*;
import static com.guzi.sherly.modules.log.enums.LoginResultEnum.*;
import static com.guzi.sherly.modules.log.enums.LoginTypeEnum.PASSWORD;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class LoginService {

    @Resource
    private AuthenticationProvider authenticationProvider;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserDao userDao;

    @Resource
    private AccountUserDao accountUserDao;

    @Resource
    private TenantDao tenantDao;

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
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginVO login(LoginDTO dto, HttpServletRequest request) {

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
                    loginLogManager.saveOne(request, dto.getPhone(), CHECK_FAIL, PASSWORD);
                } else if (exception.getCode().equals(FORBIDDEN.getCode())) {
                    loginLogManager.saveOne(request, dto.getPhone(), DISABLE, PASSWORD);
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
        loginLogManager.saveOne(request, dto.getPhone(), SUCCESS, PASSWORD);

        return loginVO;
    }

    /**
     * 处理登录时携带租户code情况
     * @param dto
     */
    private void dealWithTenantCode(LoginDTO dto) {
        if (StrUtil.isNotBlank(dto.getTenantCode())) {
            TenantDO tenantDO = tenantDao.getByTenantCode(dto.getTenantCode());
            // 如果租户不存在
            if (tenantDO == null) {
                throw new BizException(TENANT_MISS);
            }

            // 如果在选择租户下无该账号
            AccountUserDO accountUserDO = accountUserDao.getByPhone(dto.getPhone());
            List<String> split = StrUtil.split(accountUserDO.getTenantData(), ",");
            if (!split.contains(dto.getTenantCode())) {
                throw new BizException(NOT_IN_ACCOUNT);
            }

            // 如果选择的租户已过期
            if (tenantDO.getExpireTime().getTime() <= System.currentTimeMillis()) {
                throw new BizException(TENANT_EXPIRED, tenantDO.getTenantName());
            }
            // 校验租户是否可用，如果可用，那么最近登陆租户切换成该租户code
            accountUserDO.setLastLoginTenantCode(dto.getTenantCode());
            accountUserDao.updateById(accountUserDO);
        }
    }

    /**
     * 更新用户登录信息
     * @param userDO
     * @param request
     */
    private void updateUser(UserDO userDO, HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request);
        userDO.setLastLoginTime(new Date());
        userDO.setLastLoginIp(ip);
        userDao.updateById(userDO);
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

        AccountUserDO accountUserDO = accountUserDao.getByPhone(phone);
        List<String> tenantCodes = StrUtil.split(accountUserDO.getTenantData(), ",");
        List<TenantDO> tenantDOs = tenantDao.listAvailableByTenantCodes(tenantCodes);
        if (CollectionUtils.isEmpty(tenantDOs)) {
            throw new BizException(NO_TENANT);
        }

        return tenantDOs.stream().map(e -> {
            LoginTenantVO vo = new LoginTenantVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

    }

    /**
     * 切换登录租户
     */
    @Transactional(rollbackFor = Exception.class)
    public void loginChange(String tenantCode, HttpServletRequest request) {
        String phone = SecurityUtil.getPhone();
        String sessionId = SecurityUtil.getSessionId();

        SecurityModel securityModel = new SecurityModel();
        securityModel.setTenantCode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityModel, null));
        AccountUserDO accountUserDO = accountUserDao.getByPhone(phone);
        accountUserDO.setLastLoginTenantCode(tenantCode);
        accountUserDao.updateById(accountUserDO);

        LoginUserDetails loginUserDetails = (LoginUserDetails)userDetailsService.loadUserByUsername(phone);
        this.updateCache(loginUserDetails, sessionId, request);
    }

    public void updateCache(LoginUserDetails loginUserDetails, String sessionId, HttpServletRequest request) {
        // redis缓存登录用户信息
        RedisSecurityModel redisSecurityModel = loginUserDetails.getRedisSecurityModel(request);
        UserOnline userOnline = redisSecurityModel.getUserOnline();
        userOnline.setAddress(IpUtil.getAddress(userOnline.getIp()));
        redisSecurityModel.setUserOnline(userOnline);
        if (sessionId != null) {
            redisSecurityModel.getSecurityModel().setSessionId(sessionId);
        }

        // 权限信息更新到redis
        redisTemplate.opsForValue().set(RedisKey.SESSION_ID + redisSecurityModel.getSecurityModel().getSessionId(), redisSecurityModel, 6, TimeUnit.HOURS);

        // 更新用户信息
        this.updateUser(loginUserDetails.getUserDO(), request);
    }

    /**
     * 可用租户列表
     * @param dto
     * @return
     */
    public List<LoginTenantVO> availableListCheck(LoginDTO dto) {
        AccountUserDO accountUserDO = accountUserDao.getByPhone(dto.getPhone());
        if (accountUserDO == null) {
            throw new BizException(NO_REGISTER);
        }
        if (!passwordEncoder.matches(dto.getPassword(), accountUserDO.getPassword())) {
            throw new BizException(ERR_USR_PWD);
        }
        List<String> tenantCodes = StrUtil.split(accountUserDO.getTenantData(), ",");
        List<TenantDO> tenantDOs = tenantDao.listAvailableByTenantCodes(tenantCodes);

        if (CollectionUtils.isEmpty(tenantDOs)) {
            throw new BizException(NO_TENANT);
        }

        return tenantDOs.stream().map(e -> {
            LoginTenantVO vo = new LoginTenantVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
