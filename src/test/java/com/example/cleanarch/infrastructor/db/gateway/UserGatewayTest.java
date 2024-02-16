package com.example.cleanarch.infrastructor.db.gateway;

import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.infrastructor.db.mapper.UserMapper;
import com.example.cleanarch.infrastructor.db.persistence.jpa.IUserJpaRepository;
import com.example.cleanarch.infrastructor.db.persistence.jpa.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class UserGatewayTest {

    @Mock
    private IUserJpaRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserGateway sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should create an user and return it as entity.")
    void createUser() {
        UserEntity userEntity = makeUserEntity();
        User user = makeUser();
        when(userMapper.toPersistence(userEntity)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toEntity(user)).thenReturn(userEntity);

        UserEntity result = sut.create(userEntity);
        verify(userMapper, times(1)).toPersistence(userEntity);
        verify(userMapper, times(1)).toEntity(user);
        verify(userRepository, times(1)).save(user);
        assertEquals(result, userEntity);
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findById() {
    }

    UserEntity makeUserEntity() {
        return new UserEntity(1L, "any_user_name", "password", "email@example.com");
    }
    User makeUser() {
        return new User(1L, "any_user_name", "password", "email@example.com");
    }
}