package com.example.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Pet in a store.
 */
@Serdeable
@Schema(name = "Pet", description = "Pet in a store")
public class Pet {

	@Schema(description = "Pet identifier", example = "123")
	Long id;

	@Schema(description = "Pet name", example = "Doggie")
	String name;

	@Schema(description = "Pet status", example = "available")
	PetStatus status;

	@JsonCreator
	public Pet() {
	}

	public Pet(Long id, String name, PetStatus status) {
		setId(id);
		setName(name);
		setStatus(status);
	}

	@JsonGetter
	public Long getId() {
		return id;
	}

	@JsonSetter
	public void setId(Long id) {
		this.id = id;
	}

	@JsonGetter
	public String getName() {
		return name;
	}

	@JsonSetter
	public void setName(String name) {
		this.name = name;
	}

	@JsonGetter
	public PetStatus getStatus() {
		return status;
	}

	@JsonSetter
	public void setStatus(PetStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Pet#" + id + "(" + name + "/" + status + ")";
	}
}
