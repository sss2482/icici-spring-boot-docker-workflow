package com.icici.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("Banking Application API")
                                                .version("1.0.0")
                                                .description("API documentation for Banking Application")
                                                .contact(new Contact()
                                                                .name("Support Team")
                                                                .email("support@bankingapp.com")));
        }

        @Bean
        public GroupedOpenApi v1Api() {
                return GroupedOpenApi.builder()
                                .group("users")
                                .pathsToMatch("/users/**")
                                .build();
        }

        @Bean
        public GroupedOpenApi v2Api() {
                return GroupedOpenApi.builder()
                                .group("transactions")
                                .pathsToMatch("/transactions/**")
                                .build();
        }
}
