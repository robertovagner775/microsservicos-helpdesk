package com.roberto.ticket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info =
        @Info(title = "Ticket Microservice",
        version = "v1",
        description = "The documentation of Ticket Microservice API"))
public class DocConfig {

    @Bean
    public OpenAPI openAPi() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Ticket Microservice")
                        .version("v1"));
    }
}
