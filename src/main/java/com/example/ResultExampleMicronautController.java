package com.example;

import io.micronaut.http.annotation.*;

@Controller("/result-example-micronaut")
public class ResultExampleMicronautController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}