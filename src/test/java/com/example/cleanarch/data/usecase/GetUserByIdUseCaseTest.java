package com.example.cleanarch.data.usecase;

import com.example.cleanarch.core.shared.logic.Either;
import com.example.cleanarch.data.protocols.gateway.user.IGetUserByIdGateway;
import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.domain.exception.UserNotFoundError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetUserByIdUseCaseTest {

    @Mock
    private IGetUserByIdGateway userGateway;

    @InjectMocks
    private GetUserByIdUseCase sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return an user if it exists.")
    void getUserWhenUserExists() {
        UserEntity userEntity = makeUserEntity();
        when(userGateway.findById(userEntity.getId())).thenReturn(userEntity);

        Either<Error, UserEntity> result = sut.execute(userEntity.getId());

        assertTrue(result.isRight());
        assertEquals(result.getRight(), Optional.of(userEntity));
    }

    @Test
    @DisplayName("should return an error if user does not exists.")
    void getErrorWhenUserDoesNotExists() {
        UserEntity userEntity = makeUserEntity();
        when(userGateway.findById(userEntity.getId())).thenReturn(null);

        Either<Error, UserEntity> result = sut.execute(userEntity.getId());

        assertTrue(result.isLeft());
        assertTrue(result.getLeft().isPresent());
        assertInstanceOf(UserNotFoundError.class, result.getLeft().get());
    }

    UserEntity makeUserEntity() {
        return new UserEntity(1L, "any_user_name", "password", "email@example.com");
    }

}