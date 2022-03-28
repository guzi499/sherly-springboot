package com.guzi.upr.util;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
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

        ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
        ClassPathResource classPathResource = new ClassPathResource("template/sherly.vm");
        File file = classPathResource.getFile();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>(1);
        stringObjectHashMap.put("database", database);
        String sql = GenUtil.genCode(classPathResource.getPath(), stringObjectHashMap);
        byte[] bytes = sql.getBytes(StandardCharsets.UTF_8);
        BufferedReader bufferedReader = FileStreamUtil.getBufferedReader(bytes);
        scriptRunner.runScript(bufferedReader);
    }


}
