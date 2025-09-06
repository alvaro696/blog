package com.blog.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//swagger
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog Management API")
                        .version("1.0.0")
                        .description("API REST para la gestión de blogs, autores y comentarios")
                        .contact(new Contact()
                                .name("Blog API")
                                .email("contacto@blog.com")));
    }
}
