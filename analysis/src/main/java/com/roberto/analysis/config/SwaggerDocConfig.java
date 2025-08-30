package com.roberto.analysis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info =
@Info(title = "Analysis Microservice",
        version = "v1",
        description = "The documentation of Analysis Microservice"))
public class SwaggerDocConfig {

    @Bean
    public OpenAPI openAPi() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Analysis Microservice")
                        .version("v1"));
    }
}

