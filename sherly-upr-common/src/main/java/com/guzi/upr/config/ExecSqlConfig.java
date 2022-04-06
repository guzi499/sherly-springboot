package com.guzi.upr.config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/4/2
 */
@Component
public class ExecSqlConfig {

    private Map<String, String> sqlContainer = new HashMap<>();

    private ExecSqlConfig() throws Exception {
        // 1.创建Reader对象
        SAXReader reader = new SAXReader();
        // 2.加载xml
        InputStream inputStream = new ClassPathResource("sql-script.xml").getInputStream();
        Document document = reader.read(inputStream);
        // 3.获取根节点
        Element root = document.getRootElement();

        // 4.遍历每个statement
        List<Element> statements = root.elements("statement");
        for (Element statement : statements) {
            String name = null;
            String sql = null;
            List<Element> elements = statement.elements();
            // 5.拿到name和script加载到内存中管理
            for (Element element : elements) {
                if ("name".equals(element.getName())) {
                    name = element.getText();
                } else if ("script".equals(element.getName())) {
                    sql = element.getText();
                }
            }
            sqlContainer.put(name, sql);
        }
    }

    public String get(String name) {
        return sqlContainer.get(name);
    }
}
