package com.example.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Pet status in a store.
 */
@Schema(description = "Pet status in the store", example = "available")
@SuppressWarnings("unused")
public enum PetStatus {

	@JsonProperty("available")
	AVAILABLE,

	@JsonProperty("pending")
	PENDING,

	@JsonProperty("sold")
	SOLD
}
