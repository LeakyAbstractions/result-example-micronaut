package com.example.api;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Pet repository type.
 */
@Schema(description = "Type of pet repository", example = "LOCAL")
public enum RepositoryType {

	LOCAL,

	GITHUB,

	LOOPBACK,

	SWAGGER,

	SPECIAL
}
