package com.guzi.upr.util;

import com.guzi.upr.config.ExecSqlConfig;
import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.sql.DataSource;
import java.io.StringReader;
import java.util.Map;

/**
 * sql脚本执行工具
 * @author 谷子毅
 * @date 2022/4/2
 */
public class ExecSqlUtil {

    private static final ExecSqlConfig EXEC_SQL_CONFIG = SpringContextHolder.getBean(ExecSqlConfig.class);

    private static final DataSource DATA_SOURCE = SpringContextHolder.getBean(DataSource.class);

    @SneakyThrows
    public static void execSql(String name, Map<String, String> replaceMap) {
        // 获取SQL脚本模板
        String sql = EXEC_SQL_CONFIG.get(name);

        // 替换模板变量
        for (Map.Entry<String, String> entity : replaceMap.entrySet()) {
            sql = sql.replace(entity.getKey(), entity.getValue());
        }
        ScriptRunner scriptRunner = new ScriptRunner(DATA_SOURCE.getConnection());
        // 执行SQL
        scriptRunner.runScript(new StringReader(sql));
    }


}
