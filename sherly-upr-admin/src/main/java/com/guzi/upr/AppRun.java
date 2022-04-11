package com.guzi.upr;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author 谷子毅
 * @date 2022/3/18
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.guzi.upr.mapper.admin")
public class AppRun {
    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AppRun.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        String port = environment.getProperty("server.port");
        String serviceName = environment.getProperty("spring.application.name");

        log.info("\n----------------------------------------------------------\n\t" +
                serviceName + " is running! There are some useful urls:\n\t" +
                "System:\t http://127.0.0.1:" + port + "\n\t" +
                "knife4j: http://127.0.0.1" + ":" + port + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
