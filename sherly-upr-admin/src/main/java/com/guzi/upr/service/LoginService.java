package com.guzi.upr.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.constants.RedisKey;
import com.guzi.upr.manager.AccountUserManager;
import com.guzi.upr.manager.TenantManager;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.model.admin.AccountUser;
import com.guzi.upr.model.admin.Tenant;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.admin.UserOnline;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.exception.BizException;
import com.guzi.upr.model.vo.LoginTenantVO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.security.model.LoginUserDetails;
import com.guzi.upr.security.model.RedisSecurityModel;
import com.guzi.upr.security.model.SecurityModel;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.util.GlobalPropertiesUtil;
import com.guzi.upr.util.JwtUtil;
import com.guzi.upr.util.LogRecordUtil;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.guzi.upr.model.contants.CommonConstants.LOGIN_LOG_SUCCESS;
import static com.guzi.upr.model.contants.CommonConstants.LOGIN_TYPE_PASSWORD;
import static com.guzi.upr.model.exception.enums.AdminErrorEnum.*;

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
    private AccountUserManager accountUserManager;

    @Autowired
    private TenantManager tenantManager;

    @Autowired
    private LogRecordUtil logRecordUtil;

    @Autowired
    private Ip2regionSearcher ip2regionSearcher;

    @Autowired
    private UserDetailsService userDetailsService;

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
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);

        // 获取登录用户信息
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        this.updateCache(loginUserDetails, request);

        // 生成key标签作为token内容
        String keyLabel = dto.getPhone() + "#" + System.currentTimeMillis();

        // 生成token返回前端
        LoginVO loginVO = new LoginVO(JwtUtil.generateToken(keyLabel));
        // 记录日志
        logRecordUtil.recordLoginLog(request, dto.getPhone(), LOGIN_LOG_SUCCESS, LOGIN_TYPE_PASSWORD);

        return loginVO;
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

        redisTemplate.delete(RedisKey.GENERATE_USER + SecurityUtil.getPhone());

    }

    /**
     * 可用租户列表
     * @param phone
     * @return
     */
    public List<LoginTenantVO> availableList(String phone) {
        SecurityUtil.setOperateTenantCode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb());

        AccountUser accountUser = accountUserManager.getByPhone(phone);
        List<String> tenantCodes = StrUtil.split(accountUser.getTenantData(), ",");
        List<Tenant> tenants = tenantManager.listAvailableByTenantCodes(tenantCodes);
        if (CollectionUtils.isEmpty(tenants)) {
            throw new BizException(TENANT_UNABLE);
        }

        SecurityUtil.clearOperateTenantCode();
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

        SecurityModel securityModel = new SecurityModel();
        securityModel.setTenantCode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityModel, null));
        AccountUser accountUser = accountUserManager.getByPhone(phone);
        accountUser.setLastLoginTenantCode(tenantCode);
        accountUserManager.updateById(accountUser);

        LoginUserDetails loginUserDetails = (LoginUserDetails)userDetailsService.loadUserByUsername(phone);
        this.updateCache(loginUserDetails, request);
    }

    public void updateCache(LoginUserDetails loginUserDetails, HttpServletRequest request) throws JsonProcessingException {
        // redis缓存登录用户信息
        RedisSecurityModel redisSecurityModel = loginUserDetails.getRedisSecurityModel(request);
        UserOnline userOnline = redisSecurityModel.getUserOnline();
        userOnline.setAddress(ip2regionSearcher.getAddress(userOnline.getIp()));
        redisSecurityModel.setUserOnline(userOnline);

        // 权限信息更新到redis
        String redisString = OBJECTMAPPER.writeValueAsString(redisSecurityModel);
        redisTemplate.opsForValue().set(RedisKey.GENERATE_USER + loginUserDetails.getUsername(), redisString, 6, TimeUnit.HOURS);

        // 更新用户信息
        this.updateUser(loginUserDetails.getUser(), request);
    }
}
