package com.vasanth.EcommerceBackend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ecommerceOpenAPI(){
        return new OpenAPI().info(new Info().title("E-Commerce")
                .description("Backend for E-Commerce")
                .version("1.0")
                .contact(new Contact().name("Vasanth Srikanth").url("https://github.com/vasanthmax").email("vasanthmaxwell325@gmail.com"))
                .license(new License().name("License").url("/"))
                )
                .externalDocs(new ExternalDocumentation().description("E-Commerce Documentation")
                        .url("http://localhost:8080/swagger-ui/index.html"));
    }
}
