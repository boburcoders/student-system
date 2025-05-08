package com.company.student_management_system;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class StudentManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("University Management System Api")
                        .version("1")
                        .description("University Management System Api Documentation")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Bobur")
                                .email("boburtoshniyozov4@gmail.com")
                                .url("https://github.com/boburcoders"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc version 2")
                        .url("https://springdoc.org/v/2"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("LocalHost Server")
                )).addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components((new Components()
                        .addSecuritySchemes("basicAuth", new SecurityScheme()
                                .name("basicAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic"))));
    }
}
