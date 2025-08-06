package com.example.repository;

import com.example.api.ApiError;
import com.example.api.Pet;
import com.example.api.RepositoryType;
import com.leakyabstractions.result.api.Result;

import java.util.Collection;

/**
 * Represents a pet repository.
 */
public interface PetRepository {

	/**
	 * Returns the type of this repository.
	 *
	 * @return the type of this repository
	 */
	RepositoryType getType();

	/**
	 * Returns all pets in this repository.
	 *
	 * @return all pets in this repository
	 */
	Result<Collection<Pet>, ApiError> listPets();

	/**
	 * Adds a new pet to this repository
	 *
	 * @param pet The pet to add
	 * @return The added pet
	 */
	Result<Pet, ApiError> createPet(Pet pet);

	/**
	 * Updates an existing pet in this repository.
	 *
	 * @param pet The pet to update
	 * @return The updated pet
	 */
	Result<Pet, ApiError> updatePet(Pet pet);

	/**
	 * Returns a single pet by ID.
	 *
	 * @param id The pet ID to find
	 * @return A single pet
	 */
	Result<Pet, ApiError> findPet(Long id);

	/**
	 * Deletes a pet by ID.
	 *
	 * @param id The pet ID to delete
	 * @return `true` if the pet was deleted
	 */
	Result<Boolean, ApiError> deletePet(Long id);
}
