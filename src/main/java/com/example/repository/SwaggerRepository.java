package com.example.repository;

import com.example.api.ApiError;
import com.example.api.Pet;
import com.example.client.SwaggerClient;
import com.leakyabstractions.result.api.Result;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.util.Collection;

import static com.example.api.RepositoryType.SWAGGER;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implements the Swagger pet repository.
 *
 * <p>Interacts with Swagger's sample pet store server.</p>
 */
@Singleton
class SwaggerRepository extends RemoteRepository implements PetRepository {

	static final Logger log = getLogger(SwaggerRepository.class);

	final SwaggerClient swagger;

	SwaggerRepository(SwaggerClient swagger) {
		super(SWAGGER);
		this.swagger = swagger;
	}

	@Override
	public Result<Collection<Pet>, ApiError> listPets() {
		log.info("List pets");
		return evaluateAny(swagger::remoteList);
	}

	@Override
	public Result<Pet, ApiError> createPet(Pet pet) {
		log.info("Create pet: {}", pet);
		return evaluateAny(() -> swagger.remoteCreate(pet));
	}

	@Override
	public Result<Pet, ApiError> updatePet(Pet pet) {
		log.info("Update pet: {}", pet);
		return evaluateAny(() -> swagger.remoteUpdate(pet));
	}

	@Override
	public Result<Pet, ApiError> findPet(Long id) {
		log.info("Find pet by ID: {}", id);
		return evaluateAny(() -> swagger.remoteFindById(id));
	}

	@Override
	public Result<Boolean, ApiError> deletePet(Long id) {
		log.info("Delete pet by ID: {}", id);
		return evaluateAny(() -> swagger.remoteDeleteById(id))
			.mapSuccess(HttpResponse::status)
			.mapSuccess(HttpStatus.OK::equals);
	}
}
