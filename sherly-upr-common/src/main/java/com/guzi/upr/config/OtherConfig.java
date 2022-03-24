package com.guzi.upr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Configuration
public class OtherConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
