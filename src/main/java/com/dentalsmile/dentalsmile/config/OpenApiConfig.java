package com.dentalsmile.dentalsmile.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HappySmile – API de Clínica Dental")
                        .description("""
                                API REST para el sistema de gestión del consultorio dental HappySmile.
                                
                                **Autenticación:** Obtén un token en `POST /api/auth/login`
                                y úsalo en el botón **Authorize** (esquema Bearer).
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("HappySmile Dev Team")
                                .email("dev@happysmile.pe")))
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}

