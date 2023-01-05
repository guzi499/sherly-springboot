package com.guzi.sherly.modules.demo.aop;

import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.common.util.GlobalPropertiesUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.guzi.sherly.common.contants.CommonConstants.FALSE;
import static com.guzi.sherly.common.exception.enums.CommonErrorEnum.DEMO_ENV;

/**
 * 演示环境aop
 * @author 谷子毅
 * @date 2022/8/1
 */
@Aspect
@Component
public class TestAop {


    @Pointcut("execution(* com.guzi.sherly..*Controller.*(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if (Objects.equals(FALSE, GlobalPropertiesUtil.SHERLY_PROPERTIES.getDemoEnv())) {
            return joinPoint.proceed();
        }
        List<String> enableUris = new ArrayList<>();
        enableUris.add("/api/login");
        enableUris.add("/api/logout");
        enableUris.add("/api/available_list_check");
        enableUris.add("/api/login_change");
        enableUris.add("/api/login");

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        if ("GET".equals(requestMethod) || enableUris.contains(requestURI)) {
            return joinPoint.proceed();
        }
        throw new BizException(DEMO_ENV);
    }
}
