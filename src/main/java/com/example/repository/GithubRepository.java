package com.example.repository;

import com.example.api.ApiError;
import com.example.api.Pet;
import com.example.client.GithubClient;
import com.leakyabstractions.result.api.Result;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.util.Collection;

import static com.example.api.RepositoryType.GITHUB;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implements the GitHub pet repository.
 *
 * <p>Accesses static Github pages.</p>
 */
@Singleton
class GithubRepository extends RemoteRepository {

	static final Logger log = getLogger(GithubRepository.class);

	final GithubClient github;

	GithubRepository(GithubClient github) {
		super(GITHUB);
		this.github = github;
	}

	@Override
	public Result<Collection<Pet>, ApiError> listPets() {
		log.info("List GitHub pets");
		return evaluate(github::remoteList);
	}

	@Override
	public Result<Pet, ApiError> createPet(Pet pet) {
		log.info("Create GitHub pet: {}", pet);
		return evaluate(() -> github.remoteCreate(pet));
	}

	@Override
	public Result<Pet, ApiError> updatePet(Pet pet) {
		log.info("Update GitHub pet: {}", pet);
		return evaluate(() -> github.remoteUpdate(pet));
	}

	@Override
	public Result<Pet, ApiError> findPet(Long id) {
		log.info("Find GitHub pet by ID: {}", id);
		return evaluate(() -> github.remoteFindById(id));
	}

	@Override
	public Result<Boolean, ApiError> deletePet(Long id) {
		log.info("Delete GitHub pet by ID: {}", id);
		return evaluate(() -> github.remoteDeleteById(id));
	}
}
