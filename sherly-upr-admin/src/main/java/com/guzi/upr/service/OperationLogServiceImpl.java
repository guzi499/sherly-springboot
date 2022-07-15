package com.guzi.upr.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.log.annotation.SherlyLog;
import com.guzi.upr.log.model.OperationLog;
import com.guzi.upr.log.service.OperationLogService;
import com.guzi.upr.manager.OperationLogManager;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.dto.OperationLogPageDTO;
import com.guzi.upr.model.vo.OperationLogPageVO;
import com.guzi.upr.model.vo.OperationLogVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/7/14
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private OperationLogManager operationLogManager;

    @Override
    public void saveOne(Long duration, ProceedingJoinPoint joinPoint, String type, Throwable exception) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String requestMethod = request.getMethod();
        String uri = request.getRequestURI();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SherlyLog annotation = method.getAnnotation(SherlyLog.class);

        String requestParams = this.parseArgs(methodSignature, joinPoint.getArgs());

        OperationLog operationLog = new OperationLog();
        operationLog.setType(type);
        operationLog.setDescription(annotation != null ? annotation.description() : null);
        operationLog.setDuration(duration);
        operationLog.setRequestMethod(requestMethod);
        operationLog.setUri(uri);
        operationLog.setRequestParams(requestParams);

        if (exception != null) {
            List<String> list = Arrays.stream(exception.getStackTrace()).map(Objects::toString).filter(e -> e.startsWith("com.guzi")).collect(Collectors.toList());
            list.add(0, exception.getMessage());
            operationLog.setException(OBJECTMAPPER.writeValueAsString(list));
        }

        operationLogManager.save(operationLog);
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

    @Override
    public PageResult<OperationLogPageVO> listPage(OperationLogPageDTO dto) {
        Page<OperationLog> page = operationLogManager.listPage(dto);

        List<OperationLogPageVO> result = page.getRecords().stream().map(e -> {
            OperationLogPageVO vo = new OperationLogPageVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public OperationLogVO getOne(Long logId) {
        OperationLog log = operationLogManager.getById(logId);
        OperationLogVO vo = new OperationLogVO();
        BeanUtils.copyProperties(log, vo);
        return vo;
    }

    @Override
    public void removeAll() {
        operationLogManager.removeAll();
    }
}
