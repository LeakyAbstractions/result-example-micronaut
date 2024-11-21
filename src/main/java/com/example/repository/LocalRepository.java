package com.example.repository;

import com.example.api.ApiError;
import com.example.api.Pet;
import com.example.api.RepositoryType;
import com.example.config.PetStoreConfig;
import com.leakyabstractions.result.api.Result;
import com.leakyabstractions.result.core.Results;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Map;

import static com.example.api.RepositoryType.LOCAL;
import static java.lang.Boolean.TRUE;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implements the local pet repository.
 *
 * <p>Maintains an in-memory collection of pets that can be manipulated.</p>
 */
@Singleton
class LocalRepository implements PetRepository {

	static final Logger log = getLogger(LocalRepository.class);

	final Result<? extends Map<Long, Pet>, ApiError> pets;

	public LocalRepository(PetStoreConfig config) {
		pets = Results.<PetStoreConfig, ApiError>success(config)
			.filter(PetStoreConfig::isLocalEnabled, x -> ApiError.unavailable(LOCAL))
			.mapSuccess(PetStoreConfig::getPetsAsMap);
	}

	@Override
	public RepositoryType getType() {
		return LOCAL;
	}

	@Override
	public Result<Collection<Pet>, ApiError> listPets() {
		log.info("List Local pets");
		return pets.mapSuccess(Map::values);
	}

	@Override
	public Result<Pet, ApiError> createPet(Pet pet) {
		log.info("Create Local pet: {}", pet);
		return pets.flatMapSuccess(db -> {
			final Long id = db.keySet().stream().mapToLong(Long.class::cast).max().orElse(0L) + 1;
			pet.setId(id);
			return Results.ofCallable(() -> db.put(id, pet) != null)
				.map(x -> pet, ApiError::unexpected);
		});
	}

	@Override
	public Result<Pet, ApiError> updatePet(Pet pet) {
		final Long id = pet.getId();
		log.info("Update Local pet #{}: {}", id, pet);
		return pets.flatMapSuccess(db -> Results.ofCallable(() -> db.replace(id, pet) != null)
			.mapFailure(ApiError::unexpected)
			.filter(TRUE::equals, x -> ApiError.notFound(id))
			.mapSuccess(x -> pet));
	}

	@Override
	public Result<Pet, ApiError> findPet(Long id) {
		log.info("Find Local pet by ID: {}", id);
		return pets.flatMapSuccess(db -> Results.ofNullable(db.get(id), () -> ApiError.notFound(id)));
	}

	@Override
	public Result<Boolean, ApiError> deletePet(Long id) {
		log.info("Delete Local pet by ID: {}", id);
		return pets.flatMapSuccess(db -> Results.ofCallable(() -> db.remove(id) != null)
			.mapFailure(ApiError::unexpected)
			.filter(TRUE::equals, x -> ApiError.notFound(id)));
	}
}
