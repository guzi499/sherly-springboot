package com.guzi.sherly.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.guzi.sherly.util.GlobalPropertiesUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    private final OpenApiExtensionResolver openApiExtensionResolver;

    public Knife4jConfiguration(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        // 设置token请求头为必填
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> params = new ArrayList<>();
        ticketPar.name("token").description("Authorization")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        params.add(ticketPar.build());

        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title(GlobalPropertiesUtil.PROJECT_PROPERTIES.getProjectName())
                        .build())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.guzi.sherly.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(params)
                .extensions(openApiExtensionResolver.buildExtensions("default"));

        return docket;
    }
}
