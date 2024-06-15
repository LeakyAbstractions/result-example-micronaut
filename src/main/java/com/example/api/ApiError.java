package com.example.api;

import com.fasterxml.jackson.annotation.*;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.example.api.ApiErrorCode.*;

/**
 * Pet API error.
 */
@Serdeable
@Schema(description = "Pet API Error")
public class ApiError {

	@JsonProperty
	@Schema(description = "Error code")
	public ApiErrorCode code;

	@JsonProperty
	@Schema(description = "Error message")
	public String message;

	@JsonCreator
	public ApiError() {
	}

	public ApiError(ApiErrorCode code, String message) {
		setCode(code);
		setMessage(message);
	}

	public static ApiError notFound(Long id) {
		return new ApiError(PET_NOT_FOUND, "Pet not found: " + id);
	}

	public static ApiError unexpected(Exception ex) {
		return new ApiError(UNEXPECTED_ERROR, ex.getMessage());
	}

	public static ApiError remote(Exception ex) {
		if (ex instanceof HttpClientResponseException http && http.getStatus().getCode() == 404) {
			return new ApiError(REMOTE_PET_NOT_FOUND, "Remote pet not found");
		}
		return new ApiError(UNEXPECTED_REMOTE_ERROR, "Remote error: " + ex.getMessage());
	}

	public static ApiError unavailable(RepositoryType type) {
		return new ApiError(PET_STORE_UNAVAILABLE, type.toString() + " is unavailable");
	}

	@JsonGetter
	public ApiErrorCode getCode() {
		return code;
	}

	@JsonSetter
	public void setCode(ApiErrorCode code) {
		this.code = code;
	}

	@JsonGetter
	public String getMessage() {
		return message;
	}

	@JsonSetter
	public void setMessage(String message) {
		this.message = message;
	}
}
