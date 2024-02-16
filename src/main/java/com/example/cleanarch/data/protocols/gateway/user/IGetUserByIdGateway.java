package com.example.cleanarch.data.protocols.gateway.user;

import com.example.cleanarch.domain.entity.UserEntity;

public interface IGetUserByIdGateway {
    UserEntity findById(Long id);
}
