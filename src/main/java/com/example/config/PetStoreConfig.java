package com.example.config;

import com.example.api.Pet;
import io.micronaut.context.annotation.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

/**
 * Local pet store configuration.
 */
@ConfigurationProperties("pet-store")
public class PetStoreConfig {

	String apiVersion;
	boolean enabled;
	List<Pet> pets;

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public boolean isLocalEnabled() {
		return enabled;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public Map<Long, Pet> getPetsAsMap() {
		return pets.stream().collect(Collectors.toMap(Pet::getId, identity()));
	}
}
