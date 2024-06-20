package com.example.repository;

import com.example.api.ApiError;
import com.example.api.ApiResponse;
import com.example.api.RepositoryType;
import com.leakyabstractions.result.api.Result;
import com.leakyabstractions.result.core.Results;
import com.leakyabstractions.result.lazy.LazyResults;
import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Base class for all remote pet repositories.
 *
 * <p>Interacts with a remote pet store server via HTTP.</p>
 */
@Singleton
abstract class RemoteRepository implements PetRepository {

	final RepositoryType type;

	RemoteRepository(RepositoryType type) {
		this.type = type;
	}

	@Override
	public RepositoryType getType() {
		return type;
	}

	protected <S> Result<S, ApiError> evaluate(Supplier<ApiResponse<S>> call) {
		return LazyResults.ofSupplier(
				() -> Results.ofCallable(() -> Optional.ofNullable(call.get()).orElseGet(ApiResponse::new)))
			.mapFailure(ApiError::remote)
			.flatMapSuccess(ApiResponse::getResult);
	}

	protected <S> Result<S, ApiError> evaluateAny(Supplier<S> call) {
		return LazyResults.ofSupplier(
				() -> Results.ofCallable(() -> Optional.ofNullable(call.get()).orElseThrow()))
			.mapFailure(ApiError::remote);
	}
}
