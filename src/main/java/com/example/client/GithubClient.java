package com.example.client;

import com.example.api.ApiResponse;
import com.example.api.Pet;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import java.util.Collection;

/**
 * Github HTTP client.
 */
@Client("${pet-store.remote.github.url}")
public interface GithubClient {

	@Get("/all.json")
	ApiResponse<Collection<Pet>> remoteList();

	@Get("/{id}.json")
	ApiResponse<Pet> remoteFindById(@PathVariable Long id);

	@Delete("/{id}.json")
	ApiResponse<Boolean> remoteDeleteById(@PathVariable Long id);

	@Post
	ApiResponse<Pet> remoteCreate(@Body Pet pet);

	@Put
	ApiResponse<Pet> remoteUpdate(@Body Pet pet);
}
