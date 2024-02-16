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

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    @DisplayName("should find an user by email and return it as entity.")
    void findByEmail() {
        UserEntity userEntity = makeUserEntity();
        User user = makeUser();

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(user);
        when(userMapper.toEntity(user)).thenReturn(userEntity);

        UserEntity result = sut.findByEmail(userEntity.getEmail());

        verify(userMapper, times(1)).toEntity(user);
        verify(userRepository, times(1)).findByEmail(userEntity.getEmail());
        assertEquals(result, userEntity);
    }

    @Test
    @DisplayName("should return null if user does not exists on findByEmail.")
    void findByEmailAndReturnNull() {
        UserEntity userEntity = makeUserEntity();

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(null);

        UserEntity result = sut.findByEmail(userEntity.getEmail());

        verifyNoInteractions(userMapper);
        verify(userRepository, times(1)).findByEmail(userEntity.getEmail());
        assertNull(result);
    }

    @Test
    @DisplayName("should find an user by id and return it as entity.")
    void findById() {
        UserEntity userEntity = makeUserEntity();
        User user = makeUser();

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(user));
        when(userMapper.toEntity(user)).thenReturn(userEntity);

        UserEntity result = sut.findById(userEntity.getId());

        verify(userMapper, times(1)).toEntity(user);
        verify(userRepository, times(1)).findById(userEntity.getId());
        assertEquals(result, userEntity);
    }

    @Test
    @DisplayName("should return null if user does not exists on findById.")
    void findByIdAndReturnNull() {
        UserEntity userEntity = makeUserEntity();

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.empty());

        UserEntity result = sut.findById(userEntity.getId());

        verifyNoInteractions(userMapper);
        verify(userRepository, times(1)).findById(userEntity.getId());
        assertNull(result);
    }

    UserEntity makeUserEntity() {
        return new UserEntity(1L, "any_user_name", "password", "email@example.com");
    }
    User makeUser() {
        return new User(1L, "any_user_name", "password", "email@example.com");
    }
}