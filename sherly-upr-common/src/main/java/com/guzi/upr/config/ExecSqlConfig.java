package com.guzi.upr.config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
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
        //1.创建Reader对象
        SAXReader reader = new SAXReader();
        //2.加载xml
        InputStream inputStream = new ClassPathResource("sql-script.xml").getInputStream();
        Document document = reader.read(inputStream);
        //3.获取根节点
        Element root = document.getRootElement();

        Iterator<Element> item = root.elementIterator("statement");

        while (item.hasNext()){
            Element one = item.next();
            Iterator<Element> detail = one.elementIterator();
            String name = null;
            String sql = null;
            while (detail.hasNext()) {
                Element ele = detail.next();
                if ("name".equals(ele.getName())) {
                    name = ele.getText();
                } else if ("script".equals(ele.getName())) {
                    sql = ele.getText();
                }
            }
            sqlContainer.put(name, sql);
        }
    }

    public String get(String key) {
        return sqlContainer.getOrDefault(key, null);
    }
}
