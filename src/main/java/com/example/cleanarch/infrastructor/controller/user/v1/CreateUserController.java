package com.example.cleanarch.infrastructor.controller.user.v1;


import com.example.cleanarch.core.shared.logic.Either;
import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.domain.usecase.ICreateUserUseCase;
import com.example.cleanarch.infrastructor.controller.user.v1.mapper.CreateUserDTOMapper;
import com.example.cleanarch.infrastructor.controller.user.v1.request.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUserController {

    private final ICreateUserUseCase createUserUseCase;
    private final CreateUserDTOMapper createUserDTOMapper;

    public CreateUserController(ICreateUserUseCase createUserUseCase, CreateUserDTOMapper createUserDTOMapper) {
        this.createUserUseCase = createUserUseCase;
        this.createUserDTOMapper = createUserDTOMapper;
    }

    @PostMapping("/v1/user")
    public ResponseEntity<Object> handle(@RequestBody CreateUserRequest request) {
        Either<Error, UserEntity> either = createUserUseCase.execute(createUserDTOMapper.toUser(request));

        if (either.isLeft() && either.getLeft().isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(either.getLeft().get().getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(createUserDTOMapper.toResponse(either.getRight().get()));
    }
}
