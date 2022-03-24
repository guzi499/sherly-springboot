package com.guzi.upr.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.guzi.upr.model.ThreadLocalModel;
import com.guzi.upr.util.ThreadLocalUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/22
 */

@Configuration
public class MybatisPlusConfig {

    private List<String> DEFAULT_TABLE = new ArrayList<>();

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 动态表名
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
            ThreadLocalModel threadLocalModel = ThreadLocalUtil.get();
            return threadLocalModel.getTenantCode() + "." + tableName;
        });
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);

        // 分页
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }
}
