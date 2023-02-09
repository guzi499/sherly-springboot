package com.guzi.sherly.controller;

import cn.hutool.core.util.StrUtil;
import com.guzi.sherly.common.util.SpringContextHolder;
import com.guzi.sherly.modules.log.annotation.SherlyLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.text.CharPool.LF;


/**
 * @author 谷子毅
 * @date 2023/2/9
 */
@Controller
@RequestMapping("/api/document")
@Api(tags = "开发文档相关")
public class DocumentController {

    @Resource
    private DocumentationCache documentationCache;

    private ServiceModelToSwagger2Mapper mapper;

    @PostMapping("/list_ts_model")
    @ApiOperation("列出所有的ts对象")
    @SherlyLog(noRecord = true)
    public List<String> login() {
        // 返回页列表
        List<String> pages = new ArrayList<>();
        Map<String, Object> beans = SpringContextHolder.getBeans(RestController.class);
        beans.values().forEach(bean -> {
            StringBuilder builder = new StringBuilder();

            Class<?> targetClass = AopUtils.getTargetClass(bean);
            RequestMapping requestMappingAnno = targetClass.getAnnotation(RequestMapping.class);
            String prefixUri = requestMappingAnno.value()[0];

            builder.append("访问URI：").append(prefixUri).append(LF);
            builder.append("以下为接口定义：").append(LF);

            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method : methods) {
                ApiOperation apiOperationAnno = method.getAnnotation(ApiOperation.class);
                if (apiOperationAnno == null) {
                    break;
                }
                GetMapping getMappingAnno = method.getAnnotation(GetMapping.class);
                PostMapping postMappingAnno = method.getAnnotation(PostMapping.class);
                PutMapping putMappingAnno = method.getAnnotation(PutMapping.class);
                DeleteMapping deleteMappingAnno = method.getAnnotation(DeleteMapping.class);
                String getMappingUri = getMappingAnno == null? "" : getMappingAnno.value()[0];
                String postMappingUri = postMappingAnno == null? "" : postMappingAnno.value()[0];
                String putMappingUri = putMappingAnno == null? "" : putMappingAnno.value()[0];
                String deleteMappingUri = deleteMappingAnno == null? "" : deleteMappingAnno.value()[0];
                String suffixUri = getMappingUri + postMappingUri + putMappingUri + deleteMappingUri;

                String uriWithoutApi = prefixUri + suffixUri;
                uriWithoutApi = StrUtil.subAfter(uriWithoutApi, "/api", false);

                builder.append("/** ").append(apiOperationAnno.value()).append(" */").append(LF);
                builder.append("export const ").append(method.getName()).append(" = (params?: ").append("TempDTO").append(") => {").append(LF);
                builder.append("  return http.request<").append("Result").append(">(\"").append("get").append("\", baseUrlApi(\"").append(uriWithoutApi).append("\"), {").append(LF);
                builder.append("    params").append(LF);
                builder.append("  });").append(LF);
                builder.append("};").append(LF).append(LF);
            }
            // System.out.println(builder);
            pages.add(builder.toString());
        });

        return pages;
    }
}
