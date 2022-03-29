package com.guzi.upr.util;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/28 21:26
 */
public class ExecSqlUtil {

    /**
     * @param dataSource 数据源
     * @author 付东辉
     * @date 2022/3/28 21:40
     * 执行sql文件操作创建库
     */
    public static void createDatabase(DataSource dataSource, String database) throws SQLException, IOException {
        // 模板参数添加
        HashMap<String, Object> stringObjectHashMap = new HashMap<>(1);
        stringObjectHashMap.put("database", database);
        // 模板渲染后执行sql
        new ScriptRunner(dataSource.getConnection())
                .runScript(FileStreamUtil
                        .getBufferedReader(GenUtil.genCode(new ClassPathResource("template/sherly.vm").getPath(), stringObjectHashMap)
                                .getBytes(StandardCharsets.UTF_8)));
    }


}
