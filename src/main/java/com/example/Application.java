package com.example;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "Result Petstore",
                description = "Example Micronaut application using results",
                version = "1.0",
                contact = @Contact(
                        url = "https://guillermo.dev",
                        name = "Guillermo Calvo",
                        email = "hello@guillermo.dev"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Find out more about Result library for Java",
                url = "https://dev.leakyabstractions.com/result/"
        ),
        tags = @Tag(
                name = "pet",
                description = "Everything about your Pets"
        ),
        servers = @Server(
                url = "http://localhost:8080",
                description = "DEV"
        )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}