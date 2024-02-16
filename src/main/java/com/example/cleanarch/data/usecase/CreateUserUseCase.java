package com.example.cleanarch.data.usecase;

import com.example.cleanarch.core.shared.logic.Either;
import com.example.cleanarch.data.protocols.gateway.user.ICreateUserGateway;
import com.example.cleanarch.data.protocols.gateway.user.IFindUserByEmailGateway;
import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.domain.exception.UserAlreadyRegisteredError;
import com.example.cleanarch.domain.usecase.ICreateUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase implements ICreateUserUseCase {

    private final ICreateUserGateway createUserGateway;
    private final IFindUserByEmailGateway findUserByEmailGateway;

    public CreateUserUseCase(ICreateUserGateway createUserGateway, IFindUserByEmailGateway findUserByEmailGateway ) {
        this.createUserGateway = createUserGateway;
        this.findUserByEmailGateway = findUserByEmailGateway;
    }

    @Override
    public Either<Error, UserEntity> execute(UserEntity userEntity) {
        UserEntity foundUser = findUserByEmailGateway.findByEmail(userEntity.getEmail());

        if (foundUser != null) {
            return Either.Left(new UserAlreadyRegisteredError());
        }

        UserEntity newUserEntity =  createUserGateway.create(userEntity);

        return Either.Right(newUserEntity);
    }
}
