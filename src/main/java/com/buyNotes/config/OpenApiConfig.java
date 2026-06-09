package com.buyNotes.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


//Archivo de configuracion de Swagger para la documentacion de la api
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI buyNotesOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("BuyNotes API")
                        .version("1.0.0")
                        .description("""
                                API REST para la aplicación de lista de la compra BuyNotes.

                                **Autenticación:** la mayoría de endpoints requieren un JWT obtenido
                                mediante `/usuarios/login` o `/auth/google`. Pulsa el botón
                                **Authorize** y pega el token (sin el prefijo `Bearer `).
                                """)
                        .contact(new Contact()
                                .name("Jorge Antequera Vega")
                                .email("jorgeav2212@gmail.com"))
                        .license(new License()
                                .name("TFG - Uso académico")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local"),
                        new Server().url("https://proyectointegradorapi-production.up.railway.app").description("Producción (Railway)")
                ))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
