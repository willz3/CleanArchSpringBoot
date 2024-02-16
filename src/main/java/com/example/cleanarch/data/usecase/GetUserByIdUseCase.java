package com.example.cleanarch.data.usecase;

import com.example.cleanarch.core.shared.logic.Either;
import com.example.cleanarch.data.protocols.gateway.user.IGetUserByIdGateway;
import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.domain.exception.UserNotFoundError;
import com.example.cleanarch.domain.usecase.IGetUserByIdUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdUseCase implements IGetUserByIdUseCase {

    private final IGetUserByIdGateway userGateway;

    public GetUserByIdUseCase(IGetUserByIdGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Either<Error, UserEntity> execute(Long id) {
        UserEntity foundUser = userGateway.findById(id);

        if (foundUser == null) {
            return Either.Left(new UserNotFoundError());
        }

        return Either.Right(foundUser);
    }
}
