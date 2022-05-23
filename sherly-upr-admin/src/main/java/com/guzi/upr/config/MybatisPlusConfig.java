package com.guzi.upr.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.guzi.upr.security.util.SecurityUtil;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author 谷子毅
 * @date 2022/3/22
 */

@Configuration
public class MybatisPlusConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 动态表名
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {

            String operateTenantCode = SecurityUtil.getOperateTenantCode();
            if (operateTenantCode != null) {
                return operateTenantCode + "." + tableName;
            }
            return SecurityUtil.getTenantCode() + "." + tableName;
        });
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);

        // 分页
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }

    @Bean
    public ScriptRunner scriptRunner() throws SQLException {
        return new ScriptRunner(dataSource.getConnection());
    }
}
