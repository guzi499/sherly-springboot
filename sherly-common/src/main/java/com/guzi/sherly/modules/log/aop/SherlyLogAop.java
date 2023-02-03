package com.guzi.sherly.modules.log.aop;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.guzi.sherly.common.enums.LogTypeEnum;
import com.guzi.sherly.common.util.IpUtil;
import com.guzi.sherly.modules.log.annotation.SherlyLog;
import com.guzi.sherly.modules.log.model.OperationLogDO;
import com.guzi.sherly.modules.log.service.OperationLogManager;
import com.guzi.sherly.modules.security.util.SecurityUtil;
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

import static com.guzi.sherly.common.enums.LogTypeEnum.EXCEPTION;
import static com.guzi.sherly.common.enums.LogTypeEnum.NORMAL;

/**
 * @author 谷子毅
 * @date 2022/7/13
 */
@Slf4j
@Aspect
@Component
public class SherlyLogAop {

    private final OperationLogManager operationLogManager;

    ThreadLocal<Long> recordTime = new ThreadLocal<>();

    public SherlyLogAop(OperationLogManager operationLogManager) {
        this.operationLogManager = operationLogManager;
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
            this.saveOne(duration, joinPoint, NORMAL, null);
            return result;
        } catch (Throwable exception) {
            Long duration = System.currentTimeMillis() - recordTime.get();
            this.saveOne(duration, joinPoint, EXCEPTION, exception);
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
     */
    private void saveOne(Long duration, ProceedingJoinPoint joinPoint, LogTypeEnum type, Throwable exception) {
        OperationLogDO operationLogDO = new OperationLogDO();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = IpUtil.getIp(request);
        String address = IpUtil.getAddress(ip);
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            UserAgent agent = UserAgentUtil.parse(userAgent);
            operationLogDO.setOs(agent.getOs().toString());
            operationLogDO.setBrowser(agent.getBrowser().toString());
        }

        String requestMethod = request.getMethod();
        String uri = request.getRequestURI();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SherlyLog sherlyLogAnnotation = method.getAnnotation(SherlyLog.class);
        ApiOperation apiOperationAnnotation = method.getAnnotation(ApiOperation.class);

        String requestParams = this.parseArgs(methodSignature, joinPoint.getArgs());

        operationLogDO.setType(type);
        operationLogDO.setDescription(sherlyLogAnnotation != null ? sherlyLogAnnotation.description() : apiOperationAnnotation.value());
        operationLogDO.setDuration(duration);
        operationLogDO.setRequestMethod(requestMethod);
        operationLogDO.setUri(uri);
        operationLogDO.setRequestParams(requestParams);
        operationLogDO.setIp(ip);
        operationLogDO.setAddress(address);

        if (exception != null) {
            List<String> list = Arrays.stream(exception.getStackTrace()).map(Objects::toString).filter(e -> e.startsWith("com.guzi")).collect(Collectors.toList());
            list.add(0, exception.getMessage());
            operationLogDO.setException(JSONUtil.toJsonStr(list));
        }

        Long createTimeMills = recordTime.get();
        operationLogDO.setCreateTime(new Date(createTimeMills));
        operationLogDO.setCreateUserId(SecurityUtil.getUserId());

        operationLogManager.saveOne(operationLogDO);
    }

    /**
     * 解析请求参数
     * @param methodSignature
     * @param args
     * @return
     */
    private String parseArgs(MethodSignature methodSignature, Object[] args) {
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

        return JSONUtil.toJsonStr(map);
    }
}
