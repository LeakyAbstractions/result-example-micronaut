package com.example;

import com.example.api.ApiResponse;
import com.example.api.Pet;
import com.example.client.LoopbackClient;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Primary;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.example.api.ApiErrorCode.PET_STORE_UNAVAILABLE;
import static com.example.api.RepositoryType.*;
import static com.leakyabstractions.result.assertj.InstanceOfResultAssertFactories.LIST;
import static com.leakyabstractions.result.assertj.InstanceOfResultAssertFactories.RESULT;
import static io.micronaut.http.HttpRequest.GET;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class ResultExampleMicronautTest {

    static final Argument<ApiResponse<Pet>> PET = Argument.of(
            (Class<ApiResponse<Pet>>) ((Class) ApiResponse.class),
            Argument.of(Pet.class, "S")
    );

    static final Argument<ApiResponse<List<Pet>>> PETS = Argument.ofTypeVariable(
            (Class<ApiResponse<List<Pet>>>) ((Class) ApiResponse.class),
            null,
            AnnotationMetadata.EMPTY_METADATA,
            Argument.ofTypeVariable((Class<List<Pet>>) ((Class) List.class), "S", AnnotationMetadata.EMPTY_METADATA, Argument.ofTypeVariable(Pet.class, "E"))
    );


    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    HttpClient rest;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }


    @Test
    void testIndexController() {
        assertThat(rest.toBlocking().exchange("/", String.class).body())
                .contains("swagger-ui.css");
    }

    @Test
    void testPetControllerLocal() {
        assertThat(rest.toBlocking().exchange(GET("/pet").headers(Map.of("X-Type", LOCAL.name())), PETS))
                .extracting(HttpResponse::body)
                .extracting(ApiResponse::getResult)
                .asInstanceOf(RESULT)
                .hasSuccessThat(LIST)
                .hasSize(3);
    }

    @Test
    void testPetControllerLoopback() {
        assertThat(rest.toBlocking().exchange(GET("/pet/0").header("X-Type", LOOPBACK.name()), PET))
                .extracting(HttpResponse::body)
                .extracting(ApiResponse::getResult)
                .asInstanceOf(RESULT)
                .hasSuccessSatisfying(pet -> assertThat(pet).hasFieldOrPropertyWithValue("name", "Rocky"));
    }

    @Test
    void testPetControllerSpecial() {
        assertThat(rest.toBlocking().exchange(GET("/pet").header("X-Type", SPECIAL.name()), PETS))
                .extracting(HttpResponse::body)
                .extracting(ApiResponse::getResult)
                .asInstanceOf(RESULT)
                .hasFailureSatisfying(error -> assertThat(error).hasFieldOrPropertyWithValue("code", PET_STORE_UNAVAILABLE));
    }

    @Primary
    @Bean
    LoopbackClient loopbackClient() {
        return new LoopbackClient() {
            @Override
            public ApiResponse<Pet> remoteFindById(Long id) {
                return rest
                        .toBlocking()
                        .exchange(GET("/pet/" + id).header("X-Type", LOCAL.name()), PET)
                        .body();
            }

            @Override
            public ApiResponse<Collection<Pet>> remoteList() {
                throw new RuntimeException("Not implemented");
            }

            @Override
            public ApiResponse<Boolean> remoteDeleteById(Long id) {
                throw new RuntimeException("Not implemented");
            }

            @Override
            public ApiResponse<Pet> remoteCreate(Pet pet) {
                throw new RuntimeException("Not implemented");
            }

            @Override
            public ApiResponse<Pet> remoteUpdate(Pet pet) {
                throw new RuntimeException("Not implemented");
            }
        };
    }
}
