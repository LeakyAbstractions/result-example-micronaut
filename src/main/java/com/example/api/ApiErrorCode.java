package com.example.api;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Pet API error code.
 */
@Schema(description = "Pet API error code", example = "PET_NOT_FOUND")
public enum ApiErrorCode {

	PET_NOT_FOUND,
	REMOTE_PET_NOT_FOUND,
	PET_STORE_UNAVAILABLE,
	UNEXPECTED_ERROR,
	UNEXPECTED_REMOTE_ERROR;
}
