package com.example.cleanarch.infrastructor.controller.user.v1;

import com.example.cleanarch.core.shared.logic.Either;
import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.domain.exception.UserNotFoundError;
import com.example.cleanarch.domain.usecase.IGetUserByIdUseCase;
import com.example.cleanarch.infrastructor.controller.user.v1.mapper.GetUserByIdDTOMapper;
import com.example.cleanarch.infrastructor.controller.user.v1.request.CreateUserRequest;
import com.example.cleanarch.infrastructor.controller.user.v1.response.CreateUserResponse;
import com.example.cleanarch.infrastructor.controller.user.v1.response.GetUserByIdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetUserByIdControllerTest {

    @Mock
    private IGetUserByIdUseCase getUserByIdUseCase;

    @Mock
    private GetUserByIdDTOMapper getUserByIdDTOMapper;

    @InjectMocks
    private GetUserByIdController sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return 200 if the operation succeeds.")
    void returnOk() {
        UserEntity userEntityWithId = makeUserEntity(1L);
        Long request = makeRequest();
        GetUserByIdResponse response = makeResponse();

        when(getUserByIdUseCase.execute(request)).thenReturn(Either.Right(userEntityWithId));
        when(getUserByIdDTOMapper.toResponse(userEntityWithId)).thenReturn(response);

        ResponseEntity<Object> result = sut.handle(request);

        verify(getUserByIdDTOMapper, times(1)).toResponse(userEntityWithId);
        assertEquals(result.getStatusCode().value(), 200);
        assertEquals(result.getBody(), response);
    }

    @Test
    @DisplayName("should return 404 if the operation fails.")
    void returnNotFound() {
        UserEntity userEntityWithId = makeUserEntity(1L);
        Long request = makeRequest();
        GetUserByIdResponse response = makeResponse();
        UserNotFoundError userNotFoundError = new UserNotFoundError();
        when(getUserByIdUseCase.execute(request)).thenReturn(Either.Left(userNotFoundError));
        when(getUserByIdDTOMapper.toResponse(userEntityWithId)).thenReturn(response);

        ResponseEntity<Object> result = sut.handle(request);

        verify(getUserByIdDTOMapper, times(0)).toResponse(userEntityWithId);
        assertEquals(result.getStatusCode().value(), 404);
        assertEquals(result.getBody(), userNotFoundError.getMessage());
    }

    UserEntity makeUserEntity(Long id) {
        return new UserEntity(id, "any_user_name", "password", "email@example.com");
    }

    Long makeRequest() {
        return 1L;
    }

    GetUserByIdResponse makeResponse() {
        return new GetUserByIdResponse(1L, "any_user_name", "email@example.com");
    }


}