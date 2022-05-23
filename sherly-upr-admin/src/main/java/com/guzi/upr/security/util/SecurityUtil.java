package com.guzi.upr.security.util;

import com.guzi.upr.security.model.SecurityModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * @author 谷子毅
 * @date 2022/5/23
 */
public final class SecurityUtil {

    /**
     * 获取SecurityModel
     *
     * @return
     */
    private static SecurityModel getThreadLocalModel() {
        return (SecurityModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 设置SecurityModel
     */
    private static void setThreadLocalModel(SecurityModel securityModel) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityModel, null, authorities));
    }


    /**
     * 设置特殊操作数据库code
     *
     * @param tenantCode
     */
    public static void setOperateTenantCode(String tenantCode) {
        SecurityModel securityModel = getThreadLocalModel();
        securityModel.setOperateTenantCode(tenantCode);
        setThreadLocalModel(securityModel);
    }

    /**
     * 获取特殊操作数据库code
     *
     * @return
     */
    public static String getOperateTenantCode() {
        SecurityModel securityModel = (SecurityModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityModel.getOperateTenantCode();
    }

    /**
     * 获取租户id
     *
     * @return
     */
    public static String getTenantCode() {
        SecurityModel securityModel = (SecurityModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityModel.getTenantCode();
    }

    /**
     * 获取userId
     *
     * @return
     */
    public static Long getUserId() {
        SecurityModel securityModel = (SecurityModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityModel.getUserId();
    }

    /**
     * 获取手机号
     *
     * @return
     */
    public static String getPhone() {
        SecurityModel securityModel = (SecurityModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityModel.getPhone();
    }

    /**
     * 清空特殊操作数据库code
     */
    public static void clearOperateTenantCode() {
        SecurityModel securityModel = getThreadLocalModel();
        securityModel.setOperateTenantCode(null);
        setThreadLocalModel(securityModel);
    }
}
