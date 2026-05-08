package br.com.evernot.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("MindScribe API")
                        .version("1.0.0")
                        .description("""
                                API RESTful do MindScribe — sistema de gerenciamento de notas pessoais.

                                **Como autenticar:**
                                1. Registre um usuário em `POST /users`
                                2. Faça login em `POST /auth/login` para obter o token JWT
                                3. Clique no botão **Authorize** acima e cole o token

                                **Endpoints públicos (sem token):**
                                - POST /users — Registrar novo usuário
                                - POST /auth/login — Fazer login

                                **Endpoints protegidos (precisam de token):**
                                - GET/POST/PUT/DELETE /notes — Gerenciar notas do usuário autenticado
                                """)
                        .contact(new Contact()
                                .name("Luiz Otávio")
                                .url("https://github.com/Luiz12-dev"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Cole aqui o token JWT obtido no login. Não precisa colocar 'Bearer ' na frente.")));
    }
}
