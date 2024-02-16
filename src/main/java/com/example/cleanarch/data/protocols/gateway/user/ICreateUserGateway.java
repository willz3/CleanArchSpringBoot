package com.example.cleanarch.data.protocols.gateway.user;

import com.example.cleanarch.domain.entity.UserEntity;

public interface ICreateUserGateway {
    UserEntity create(UserEntity user);
}
