package com.guna.expensight.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI expenseAnalyzerOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Expensight API")
                        .description("REST APIs for managing users, categories, and expenses")
                        .version("1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation"));
    }
}