package com.guzi.sherly.modules.log.aop;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.sherly.modules.log.annotation.SherlyLog;
import com.guzi.sherly.modules.log.model.OperationLog;
import com.guzi.sherly.modules.log.service.OperationLogService;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import com.guzi.sherly.util.IpUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.EXCEPTION_LOG;
import static com.guzi.sherly.model.contants.CommonConstants.NORMAL_LOG;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@Slf4j
@Aspect
@Component
public class SherlyLogAop {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    private final OperationLogService operationLogService;

    ThreadLocal<Long> recordTime = new ThreadLocal<>();

    public SherlyLogAop(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @Pointcut("execution(* com.guzi.sherly..*Controller.*(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        SherlyLog annotation = methodSignature.getMethod().getAnnotation(SherlyLog.class);
        // 如果controller方法上有注解 且 标注为不记录 那么就不记录日志
        if (annotation != null && annotation.noRecord()) {
            return joinPoint.proceed();
        }

        try {
            recordTime.set(System.currentTimeMillis());
            Object result = joinPoint.proceed();
            Long duration = System.currentTimeMillis() - recordTime.get();
            this.saveOne(duration, joinPoint, NORMAL_LOG, null);
            return result;
        } catch (Throwable exception) {
            Long duration = System.currentTimeMillis() - recordTime.get();
            this.saveOne(duration, joinPoint, EXCEPTION_LOG, exception);
            throw exception;
        } finally {
            recordTime.remove();
        }
    }

    /**
     * 操作日志参数组装并保存
     * @param duration
     * @param joinPoint
     * @param type
     * @param exception
     * @throws Exception
     */
    private void saveOne(Long duration, ProceedingJoinPoint joinPoint, Integer type, Throwable exception) throws Exception {
        OperationLog operationLog = new OperationLog();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = IpUtil.getIp(request);
        String address = IpUtil.getAddress(ip);
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            UserAgent agent = UserAgentUtil.parse(userAgent);
            operationLog.setOs(agent.getOs().toString());
            operationLog.setBrowser(agent.getBrowser().toString());
        }

        String requestMethod = request.getMethod();
        String uri = request.getRequestURI();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SherlyLog sherlyLogAnnotation = method.getAnnotation(SherlyLog.class);
        ApiOperation apiOperationAnnotation = method.getAnnotation(ApiOperation.class);

        String requestParams = this.parseArgs(methodSignature, joinPoint.getArgs());

        operationLog.setType(type);
        operationLog.setDescription(sherlyLogAnnotation != null ? sherlyLogAnnotation.description() : apiOperationAnnotation.value());
        operationLog.setDuration(duration);
        operationLog.setRequestMethod(requestMethod);
        operationLog.setUri(uri);
        operationLog.setRequestParams(requestParams);
        operationLog.setIp(ip);
        operationLog.setAddress(address);

        if (exception != null) {
            List<String> list = Arrays.stream(exception.getStackTrace()).map(Objects::toString).filter(e -> e.startsWith("com.guzi")).collect(Collectors.toList());
            list.add(0, exception.getMessage());
            operationLog.setException(OBJECTMAPPER.writeValueAsString(list));
        }

        Long createTimeMills = recordTime.get();
        operationLog.setCreateTime(new Date(createTimeMills));
        operationLog.setCreateUserId(SecurityUtil.getUserId());

        operationLogService.saveOne(operationLog);
    }

    /**
     * 解析请求参数
     * @param methodSignature
     * @param args
     * @return
     * @throws Exception
     */
    private String parseArgs(MethodSignature methodSignature, Object[] args) throws Exception {
        String[] paramNames = methodSignature.getParameterNames();

        Map<String, Object> map = new HashMap<>(args.length);
        for (int i = 0; i < args.length; i++) {
            String paramName = paramNames[i];
            Object arg = args[i];
            if (!(arg instanceof MultipartFile || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse)) {
                map.put(paramName, arg);
            }
        }

        if (map.isEmpty()) {
            return "";
        }

        return OBJECTMAPPER.writeValueAsString(map);
    }
}
