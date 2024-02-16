package com.example.cleanarch.data.usecase;

import com.example.cleanarch.core.shared.logic.Either;
import com.example.cleanarch.data.protocols.gateway.user.ICreateUserGateway;
import com.example.cleanarch.data.protocols.gateway.user.IFindUserByEmailGateway;
import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.domain.exception.UserAlreadyRegisteredError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUserUseCaseTest {

    @Mock
    private ICreateUserGateway createUserGateway;
    @Mock
    private IFindUserByEmailGateway findUserByEmailGateway;

    @InjectMocks
    private CreateUserUseCase sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return an error if user already exists.")
    void whenUserExists() {
        UserEntity userEntity = makeUserEntity();
        when(findUserByEmailGateway.findByEmail(userEntity.getEmail())).thenReturn(makeExistingUserEntity());

        Either<Error, UserEntity> result = sut.execute(userEntity);
        assertTrue(result.isLeft());
        assertTrue(result.getLeft().isPresent());
        assertInstanceOf(UserAlreadyRegisteredError.class, result.getLeft().get());
        verify(findUserByEmailGateway, times(1)).findByEmail(userEntity.getEmail());
        verifyNoInteractions(createUserGateway);
    }

    @Test
    @DisplayName("should return an user entity if user not exists.")
    void whenUserNoExists() {
        UserEntity userEntity = makeUserEntity();
        UserEntity existingUserEntity = makeExistingUserEntity();
        when(findUserByEmailGateway.findByEmail(userEntity.getEmail())).thenReturn(null);
        when(createUserGateway.create(userEntity)).thenReturn(existingUserEntity);

        Either<Error, UserEntity> result = sut.execute(userEntity);
        assertTrue(result.isRight());
        assertEquals(result.getRight(), Optional.of(existingUserEntity));
        verify(findUserByEmailGateway, times(1)).findByEmail(userEntity.getEmail());
        verify(createUserGateway, times(1)).create(userEntity);
    }

    UserEntity makeUserEntity() {
        return new UserEntity("any_user_name", "password", "email@example.com");
    }

    UserEntity makeExistingUserEntity() {
        return new UserEntity(1L,"any_user_name", "password", "email@example.com");
    }
}