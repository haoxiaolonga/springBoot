package com.spring.demo.springbootexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Contact;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // 接口版本号
    private final String version = "1.0";
    // 接口大标题
    private final String title = "SpringBoot示例工程";
    // 具体的描述
    private final String description = "API文档自动生成示例";
    // 服务说明url
    private final String termsOfServiceUrl = "http://www.baidu.com";
    // licence
    private final String license = "xxx";
    // licnce url
    private final String licenseUrl = "xxxx";
    // 接口作者联系方式
    private final  Contact contact = new Contact("xxx", "https://github.com/xxx", "xxx@gmail.com");

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder().title(title).termsOfServiceUrl(termsOfServiceUrl).description(description)
                .version(version).license(license).licenseUrl(licenseUrl).contact(contact).build();

    }

}

