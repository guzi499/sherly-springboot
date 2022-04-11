package com.guzi.upr.config;

import com.guzi.upr.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加登录拦截器并放行部分请求
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/webjars/**",
                        "/swagger-ui.html/**",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/doc.html/**",
                        "/favicon.ico",
                        "/",
                        "/csrf",
                        "/error");

    }
}
