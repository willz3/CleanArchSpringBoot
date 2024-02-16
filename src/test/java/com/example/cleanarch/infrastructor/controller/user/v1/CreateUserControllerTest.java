package com.example.cleanarch.infrastructor.controller.user.v1;

import com.example.cleanarch.core.shared.logic.Either;
import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.domain.exception.UserAlreadyRegisteredError;
import com.example.cleanarch.domain.usecase.ICreateUserUseCase;
import com.example.cleanarch.infrastructor.controller.user.v1.mapper.CreateUserDTOMapper;
import com.example.cleanarch.infrastructor.controller.user.v1.request.CreateUserRequest;
import com.example.cleanarch.infrastructor.controller.user.v1.response.CreateUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class CreateUserControllerTest {

    @Mock
    private ICreateUserUseCase createUserUseCase;

    @Mock
    private CreateUserDTOMapper createUserDTOMapper;

    @InjectMocks
    private CreateUserController sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return 200 if the creation of user succeeds.")
    void returnOk() {
        UserEntity userEntity = makeUserEntity(null);
        UserEntity userEntityWithId = makeUserEntity(1L);
        CreateUserRequest request = makeRequest();
        CreateUserResponse response = makeResponse();

        when(createUserDTOMapper.toUser(request)).thenReturn(userEntity);
        when(createUserUseCase.execute(userEntity)).thenReturn(Either.Right(userEntityWithId));
        when(createUserDTOMapper.toResponse(userEntityWithId)).thenReturn(response);

        ResponseEntity<Object> result = sut.handle(request);

        verify(createUserDTOMapper, times(1)).toUser(request);
        verify(createUserDTOMapper, times(1)).toResponse(userEntityWithId);
        assertEquals(result.getStatusCode().value(), 200);
        assertEquals(result.getBody(), response);
    }

    @Test
    @DisplayName("should return 400 if the creation of user fails.")
    void returnBadRequest() {
        UserEntity userEntity = makeUserEntity(null);
        UserEntity userEntityWithId = makeUserEntity(1L);
        CreateUserRequest request = makeRequest();
        CreateUserResponse response = makeResponse();

        when(createUserDTOMapper.toUser(request)).thenReturn(userEntity);
        when(createUserUseCase.execute(userEntity)).thenReturn(Either.Left(new UserAlreadyRegisteredError()));

        ResponseEntity<Object> result = sut.handle(request);

        verify(createUserDTOMapper, times(0)).toResponse(userEntityWithId);
        assertEquals(result.getStatusCode().value(), 400);
        assertEquals(new UserAlreadyRegisteredError().getMessage(), result.getBody());
    }

    UserEntity makeUserEntity(Long id) {
        return new UserEntity(id, "any_user_name", "password", "email@example.com");
    }

    CreateUserRequest makeRequest() {
        return new CreateUserRequest("any_user_name", "password", "email@example.com");
    }

    CreateUserResponse makeResponse() {
        return new CreateUserResponse(1L, "any_user_name", "email@example.com");
    }

}