package com.example.client;

import com.example.api.ApiResponse;
import com.example.api.Pet;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import java.util.Collection;

/**
 * Loopback HTTP client.
 */
@Client("${pet-store.remote.loopback.url}")
@Header(name = "X-Type", value = "LOCAL")
public interface LoopbackClient {

	@Get
	ApiResponse<Collection<Pet>> remoteList();

	@Get("/{id}")
	ApiResponse<Pet> remoteFindById(@PathVariable Long id);

	@Delete("/{id}")
	ApiResponse<Boolean> remoteDeleteById(@PathVariable Long id);

	@Post
	ApiResponse<Pet> remoteCreate(@Body Pet pet);

	@Put
	ApiResponse<Pet> remoteUpdate(@Body Pet pet);
}
