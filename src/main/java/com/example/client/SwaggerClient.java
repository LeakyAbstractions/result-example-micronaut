package com.example.client;

import com.example.api.Pet;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import java.util.List;

/**
 * Swagger HTTP client.
 */
@Client("${pet-store.remote.swagger.url}")
@Header(name = "api_key", value = "special-key")
public interface SwaggerClient {

	@Get("/findByStatus?status=available,pending,sold")
	List<Pet> remoteList();

	@Get("/{id}")
	Pet remoteFindById(@PathVariable Long id);

	@Delete("/{id}")
	HttpResponse<Object> remoteDeleteById(@PathVariable Long id);

	@Post
	Pet remoteCreate(@Body Pet pet);

	@Put
	Pet remoteUpdate(@Body Pet pet);
}
