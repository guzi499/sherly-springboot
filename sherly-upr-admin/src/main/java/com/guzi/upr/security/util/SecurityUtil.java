package com.guzi.upr.security.util;

import com.guzi.upr.security.SecurityModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * @author 谷子毅
 * @date 2022/5/23
 */
public class SecurityUtil {

    public static SecurityModel getThreadLocalModel() {
        return (SecurityModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static void setThreadLocalModel(SecurityModel securityModel) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityModel, null, authorities));
    }

    public static void setOperateTenantCode(String tenantCode) {
        SecurityModel securityModel = getThreadLocalModel();
        securityModel.setOperateTenantCode(tenantCode);
        setThreadLocalModel(securityModel);
    }

    public static String getOperateTenantCode() {
        SecurityModel securityModel = (SecurityModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityModel.getOperateTenantCode();
    }

    public static String getTenantCode() {
        SecurityModel securityModel = (SecurityModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityModel.getTenantCode();
    }

    public static Long getUserId() {
        SecurityModel securityModel = (SecurityModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityModel.getUserId();
    }

    public static void clearOperateTenantCode() {
        SecurityModel securityModel = getThreadLocalModel();
        securityModel.setOperateTenantCode(null);
        setThreadLocalModel(securityModel);
    }
}
