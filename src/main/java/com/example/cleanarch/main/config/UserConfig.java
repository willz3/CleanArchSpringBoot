package com.example.cleanarch.main.config;

import com.example.cleanarch.data.protocols.gateway.user.ICreateUserGateway;
import com.example.cleanarch.data.protocols.gateway.user.IFindUserByEmailGateway;
import com.example.cleanarch.data.protocols.gateway.user.IGetUserByIdGateway;
import com.example.cleanarch.data.usecase.CreateUserUseCase;
import com.example.cleanarch.data.usecase.GetUserByIdUseCase;
import com.example.cleanarch.infrastructor.controller.user.v1.mapper.CreateUserDTOMapper;
import com.example.cleanarch.infrastructor.controller.user.v1.mapper.GetUserByIdDTOMapper;
import com.example.cleanarch.infrastructor.db.gateway.UserGateway;
import com.example.cleanarch.infrastructor.db.mapper.UserMapper;
import com.example.cleanarch.infrastructor.db.persistence.jpa.IUserJpaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    ICreateUserGateway createUserGateway(IUserJpaRepository userRepository, UserMapper userMapper) {
        return new UserGateway(userRepository, userMapper);
    }

    @Bean
    IFindUserByEmailGateway findUserByEmailGateway(IUserJpaRepository userRepository, UserMapper userMapper) {
        return new UserGateway(userRepository, userMapper);
    }

    @Bean
    CreateUserUseCase createUserUseCase(@Qualifier("createUserGateway") ICreateUserGateway createUserGateway, @Qualifier("findUserByEmailGateway") IFindUserByEmailGateway findUserByEmailGateway) {
        return new CreateUserUseCase(createUserGateway, findUserByEmailGateway);
    }

    @Bean
    UserMapper createUserMapper() {
        return new UserMapper();
    }

    @Bean
    CreateUserDTOMapper createUserDTOMapper() {
        return new CreateUserDTOMapper();
    }

    @Bean
    GetUserByIdDTOMapper getUserByIdDTOMapper() {
        return new GetUserByIdDTOMapper();
    }

    @Bean
    IGetUserByIdGateway getUserByIdGateway(IUserJpaRepository userRepository, UserMapper userMapper) {
        return new UserGateway(userRepository, userMapper);
    }

    @Bean
    GetUserByIdUseCase getUserByIdUseCase(@Qualifier("getUserByIdGateway") IGetUserByIdGateway getUserByIdGateway) {
        return new GetUserByIdUseCase(getUserByIdGateway);
    }
}
