package com.example.cleanarch.infrastructor.controller.user.mapper;

import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.infrastructor.controller.user.request.CreateUserRequest;
import com.example.cleanarch.infrastructor.controller.user.response.GetUserByIdResponse;

public class GetUserByIdDTOMapper {
    public GetUserByIdResponse toResponse(UserEntity entity) {
        return new GetUserByIdResponse(entity.getId(), entity.getUserName(), entity.getEmail());
    }
}
