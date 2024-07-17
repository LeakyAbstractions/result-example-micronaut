package com.example.repository;

import com.example.api.ApiError;
import com.example.api.Pet;
import com.example.client.LoopbackClient;
import com.leakyabstractions.result.api.Result;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.util.Collection;

import static com.example.api.RepositoryType.LOOPBACK;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implements the loopback pet repository.
 *
 * <p>Interacts with the local pet store via HTTP.</p>
 */
@Singleton
class LoopbackRepository extends RemoteRepository implements PetRepository {

	static final Logger log = getLogger(LoopbackRepository.class);

	final LoopbackClient loopback;

	LoopbackRepository(LoopbackClient loopback) {
		super(LOOPBACK);
		this.loopback = loopback;
	}

	@Override
	public Result<Pet, ApiError> createPet(Pet pet) {
		log.info("Create Loopback pet: {}", pet);
		return evaluate(() -> loopback.remoteCreate(pet));
	}

	@Override
	public Result<Collection<Pet>, ApiError> listPets() {
		log.info("List Loopback pets");
		return evaluate(loopback::remoteList);
	}

	@Override
	public Result<Pet, ApiError> updatePet(Pet pet) {
		log.info("Update Loopback pet: {}", pet);
		return evaluate(() -> loopback.remoteUpdate(pet));
	}

	@Override
	public Result<Pet, ApiError> findPet(Long id) {
		log.info("Find Loopback pet by ID: {}", id);
		return evaluate(() -> loopback.remoteFindById(id));
	}

	@Override
	public Result<Boolean, ApiError> deletePet(Long id) {
		log.info("Delete Loopback pet by ID: {}", id);
		return evaluate(() -> loopback.remoteDeleteById(id));
	}
}
