package com.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Hidden;
import java.net.URI;

/**
 * Redirects to Swagger UI.
 */
@Hidden
@Controller("/")
public class IndexController {

	@Get
	public HttpResponse<?> index() {
		return HttpResponse.redirect(URI.create("/swagger/views/swagger-ui/"));
	}
}