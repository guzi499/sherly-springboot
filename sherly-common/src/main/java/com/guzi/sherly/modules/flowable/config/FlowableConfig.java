package com.guzi.sherly.modules.flowable.config;

import cn.hutool.core.util.IdUtil;
import com.guzi.sherly.modules.flowable.interceptor.FlowableSqlInterceptor;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 谷子毅
 * @date 2022/9/1
 */
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        // flowable的id生成规则改为雪花算法
        springProcessEngineConfiguration.setIdGenerator(() -> IdUtil.getSnowflake().nextIdStr());
        List list = new ArrayList<>();
        list.add(new FlowableSqlInterceptor());
        springProcessEngineConfiguration.setCustomMybatisInterceptors(list);
    }
}
