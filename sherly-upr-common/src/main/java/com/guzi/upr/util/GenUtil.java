package com.guzi.upr.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/3/28 21:53
 */
public class GenUtil {

    /**
     * @param template 模板位置 resource目录
     * @param data     模板参数
     * @return 模板替换后sql
     * @author 付东辉
     * @date 2022/3/28 22:35
     */
    public static String genCode(String template, Map<String, Object> data) throws IOException {
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        VelocityContext context = new VelocityContext(data);
        //渲染模板
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template, "UTF-8");
        tpl.merge(context, sw);
        return sw.toString();
    }
}
