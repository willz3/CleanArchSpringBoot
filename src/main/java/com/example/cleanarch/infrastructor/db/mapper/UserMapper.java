package com.example.cleanarch.infrastructor.db.mapper;

import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.infrastructor.db.persistence.jpa.model.User;

public class UserMapper {

    public UserEntity toEntity (User user) {
        return new UserEntity(user.getId(), user.getUserName(), user.getPassword(), user.getEmail());
    }

    public User toPersistence (UserEntity user) {
        return new User(user.getId(), user.getUserName(), user.getPassword(), user.getEmail());
    }

}
