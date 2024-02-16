package com.example.cleanarch.infrastructor.controller.user.v1.mapper;

import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.infrastructor.controller.user.v1.request.CreateUserRequest;
import com.example.cleanarch.infrastructor.controller.user.v1.response.CreateUserResponse;

public class CreateUserDTOMapper {

    public CreateUserResponse toResponse(UserEntity user) {
        return new CreateUserResponse(user.getId(), user.getUserName(), user.getEmail());
    }

    public UserEntity toUser(CreateUserRequest dto) {
        return new UserEntity(dto.userName(), dto.password(), dto.email());
    }
}
