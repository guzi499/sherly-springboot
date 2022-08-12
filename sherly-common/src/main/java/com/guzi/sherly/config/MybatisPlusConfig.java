package com.guzi.sherly.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author 谷子毅
 * @date 2022/3/22
 */

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(SherlyProperties sherlyProperties) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        List<String> commonDbs = sherlyProperties.getCommonDbs();
        String defaultDb = sherlyProperties.getDefaultDb();

        // 动态表名
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
            // 1.如果是公用表，直接拼上默认数据库名
            if (commonDbs.contains(tableName)) {
                return defaultDb + "." + tableName;
            }
            // 2.如果指定了操作数据库，那么拼上指定的数据库名
            String operateTenantCode = SecurityUtil.getOperateTenantCode();
            if (operateTenantCode != null) {
                return operateTenantCode + "." + tableName;
            }
            // 3.否则拼上当前租户的数据库名
            return SecurityUtil.getTenantCode() + "." + tableName;
        });
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);

        // 分页
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }
}
