package com.guzi.upr.util;

import com.guzi.upr.config.ExecSqlConfig;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.Map;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/4/2
 */
@Component
public class ExecSqlUtil {

    @Autowired
    private ExecSqlConfig execSqlConfig;

    @Autowired
    private ScriptRunner scriptRunner;

    public void execSql(String key, Map<String, String> replaceMap) {
        String sql = execSqlConfig.get(key);
        for (Map.Entry<String, String> entity : replaceMap.entrySet()) {
            sql = sql.replace(entity.getKey(), entity.getValue());
        }
        scriptRunner.runScript(new StringReader(sql));
    }


}
