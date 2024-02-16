package com.example.cleanarch.domain.usecase;

import com.example.cleanarch.core.shared.logic.Either;
import com.example.cleanarch.domain.entity.UserEntity;

public interface IGetUserByIdUseCase {
    Either<Error, UserEntity> execute(Long id);
}
