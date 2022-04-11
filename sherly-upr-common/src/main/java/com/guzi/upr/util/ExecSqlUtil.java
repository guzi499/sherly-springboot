package com.guzi.upr.util;

import com.guzi.upr.config.ExecSqlConfig;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.Map;

/**
 * @author 谷子毅
 * @date 2022/4/2
 */
@Component
public class ExecSqlUtil {

    @Autowired
    private ExecSqlConfig execSqlConfig;

    @Autowired
    private ScriptRunner scriptRunner;

    public void execSql(String name, Map<String, String> replaceMap) {
        // 获取SQL脚本模板
        String sql = execSqlConfig.get(name);

        // 替换模板变量
        for (Map.Entry<String, String> entity : replaceMap.entrySet()) {
            sql = sql.replace(entity.getKey(), entity.getValue());
        }

        // 执行SQL
        scriptRunner.runScript(new StringReader(sql));
    }


}
