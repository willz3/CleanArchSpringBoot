package com.example.cleanarch.infrastructor.db.gateway;

import com.example.cleanarch.data.protocols.gateway.user.ICreateUserGateway;
import com.example.cleanarch.data.protocols.gateway.user.IFindUserByEmailGateway;
import com.example.cleanarch.data.protocols.gateway.user.IGetUserByIdGateway;
import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.infrastructor.db.mapper.UserMapper;
import com.example.cleanarch.infrastructor.db.persistence.jpa.IUserJpaRepository;
import com.example.cleanarch.infrastructor.db.persistence.jpa.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGateway implements ICreateUserGateway, IFindUserByEmailGateway, IGetUserByIdGateway {

    private final IUserJpaRepository userRepository;
    private final UserMapper userMapper;

    public UserGateway(IUserJpaRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity create(UserEntity userEntity) {
        User user = userMapper.toPersistence(userEntity);
        User savedUser = userRepository.save(user);
        return userMapper.toEntity(savedUser);
    }

    @Override
    public UserEntity findByEmail(String email) {
         User user = userRepository.findByEmail(email);

         return user != null ? userMapper.toEntity(user) : null;
    }

    @Override
    public UserEntity findById(Long id) {
        Optional<User> foundUser = this.userRepository.findById(id);
        return foundUser.map(userMapper::toEntity).orElse(null);
    }
}
