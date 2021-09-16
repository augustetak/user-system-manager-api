package com.airfrance.ums;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";
    @Bean
    public Docket api(){

        Contact contact = new Contact(
                "Augustin",
                "https://localhost",
                "augustetak@gmail.com");

        List<VendorExtension> vext = new ArrayList<>();
        ApiInfo apiInfo = new ApiInfo(
                "UserSystemManager REST API",
                "Spring Boot REST API for Managing Users",
                "1.0.0",
                "",
                contact,
                "",
                "",
                vext);
        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .apiInfo(ApiInfo.DEFAULT)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }




}
