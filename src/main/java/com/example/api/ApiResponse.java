package com.example.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.leakyabstractions.result.api.Result;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Pet API response.
 *
 * @param <S> the type of success
 */
@Serdeable
@Schema(description = "Pet API Response")
public class ApiResponse<S> {

	@Schema(description = "API version number", example = "1.0")
	@JsonProperty
	String version;

	@Schema(description = "Generation date/time", example = "1980-07-07T12:30:00.123Z")
	@JsonProperty
	Instant generatedOn;

	@Schema(description = "Contains either a success value or a failure value")
	@JsonProperty
	Result<S, ApiError> result;

	@JsonCreator
	public ApiResponse() {
	}

	public ApiResponse(String version, Result<S, ApiError> result) {
		setVersion(version);
		setGeneratedOn(Instant.now());
		setResult(result);
	}

	@JsonGetter
	public String getVersion() {
		return version;
	}

	@JsonSetter
	public void setVersion(String version) {
		this.version = version;
	}

	@JsonGetter
	public Instant getGeneratedOn() {
		return generatedOn;
	}

	@JsonSetter
	public void setGeneratedOn(Instant generatedOn) {
		this.generatedOn = generatedOn;
	}

	@JsonGetter
	public Result<S, ApiError> getResult() {
		return result;
	}

	@JsonSetter
	public void setResult(Result<S, ApiError> result) {
		this.result = result;
	}
}
