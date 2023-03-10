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
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * @author ?????????
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ??????
     * @param dto
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginVO login(LoginDTO dto, HttpServletRequest request) throws Exception {

        // ????????????????????????Authentication??????????????????????????????????????????code???
        SecurityModel securityModel = new SecurityModel();
        securityModel.setTenantCode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityModel, null));

        // ????????????????????????????????????????????????
        this.dealWithTenantCode(dto);

        // ??????????????????
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getPhone(), dto.getPassword());

        // ????????????
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);

        // ????????????????????????
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        this.updateCache(loginUserDetails, request);

        // ??????key????????????token??????
        String keyLabel = dto.getPhone() + "#" + System.currentTimeMillis();

        // ??????token????????????
        LoginVO loginVO = new LoginVO(JwtUtil.generateToken(keyLabel));
        // ????????????
        logRecordUtil.recordLoginLog(request, dto.getPhone(), LOGIN_LOG_SUCCESS, LOGIN_TYPE_PASSWORD);

        return loginVO;
    }

    /**
     * ???????????????????????????code??????
     * @param dto
     */
    private void dealWithTenantCode(LoginDTO dto) {
        if (StrUtil.isNotBlank(dto.getTenantCode())) {
            Tenant tenant = tenantManager.getByTenantCode(dto.getTenantCode());
            // ?????????????????????
            if (tenant == null) {
                throw new BizException(TENANT_MISS);
            }

            // ????????????????????????????????????
            AccountUser accountUser = accountUserManager.getByPhone(dto.getPhone());
            List<String> split = StrUtil.split(accountUser.getTenantData(), ",");
            if (!split.contains(dto.getTenantCode())) {
                throw new BizException(NOT_IN_ACCOUNT);
            }

            // ??????????????????????????????
            if (tenant.getExpireTime().getTime() <= System.currentTimeMillis()) {
                throw new BizException(TENANT_EXPIRED, tenant.getTenantName());
            }
            // ????????????????????????????????????????????????????????????????????????????????????code
            accountUser.setLastLoginTenantCode(dto.getTenantCode());
            accountUserManager.updateById(accountUser);
        }
    }

    /**
     * ????????????????????????
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
     * ??????
     */
    public void logout() {

        redisTemplate.delete(RedisKey.GENERATE_USER + SecurityUtil.getPhone());

    }

    /**
     * ??????????????????
     * @param phone
     * @return
     */
    public List<LoginTenantVO> availableList(String phone) {
        SecurityUtil.setOperateTenantCode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb());

        AccountUser accountUser = accountUserManager.getByPhone(phone);
        List<String> tenantCodes = StrUtil.split(accountUser.getTenantData(), ",");
        List<Tenant> tenants = tenantManager.listAvailableByTenantCodes(tenantCodes);
        if (CollectionUtils.isEmpty(tenants)) {
            throw new BizException(NO_TENANT);
        }

        SecurityUtil.clearOperateTenantCode();
        return tenants.stream().map(e -> {
            LoginTenantVO vo = new LoginTenantVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

    }

    /**
     * ??????????????????
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
        // redis????????????????????????
        RedisSecurityModel redisSecurityModel = loginUserDetails.getRedisSecurityModel(request);
        UserOnline userOnline = redisSecurityModel.getUserOnline();
        userOnline.setAddress(ip2regionSearcher.getAddress(userOnline.getIp()));
        redisSecurityModel.setUserOnline(userOnline);

        // ?????????????????????redis
        String redisString = OBJECTMAPPER.writeValueAsString(redisSecurityModel);
        redisTemplate.opsForValue().set(RedisKey.GENERATE_USER + loginUserDetails.getUsername(), redisString, 6, TimeUnit.HOURS);

        // ??????????????????
        this.updateUser(loginUserDetails.getUser(), request);
    }

    /**
     * ??????????????????
     * @param dto
     * @return
     */
    public List<LoginTenantVO> availableListCheck(LoginDTO dto) {
        SecurityUtil.setOperateTenantCode(GlobalPropertiesUtil.SHERLY_PROPERTIES.getDefaultDb());
        AccountUser accountUser = accountUserManager.getByPhone(dto.getPhone());
        if (accountUser == null) {
            throw new BizException(NO_REGISTER);
        }
        if (!passwordEncoder.matches(dto.getPassword(), accountUser.getPassword())) {
            throw new BizException(ERR_USR_PWD);
        }
        List<String> tenantCodes = StrUtil.split(accountUser.getTenantData(), ",");
        List<Tenant> tenants = tenantManager.listAvailableByTenantCodes(tenantCodes);
        SecurityUtil.clearOperateTenantCode();

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
