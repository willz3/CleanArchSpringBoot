package com.example.cleanarch.infrastructor.controller.user.v1;

import com.example.cleanarch.core.shared.logic.Either;
import com.example.cleanarch.domain.entity.UserEntity;
import com.example.cleanarch.domain.usecase.IGetUserByIdUseCase;
import com.example.cleanarch.infrastructor.controller.user.v1.mapper.GetUserByIdDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetUserByIdController {
    private final IGetUserByIdUseCase getUserByIdUseCase;

    private final GetUserByIdDTOMapper getUserByIdDTOMapper;


    public GetUserByIdController(IGetUserByIdUseCase getUserByIdUseCase, GetUserByIdDTOMapper getUserByIdDTOMapper) {
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getUserByIdDTOMapper = getUserByIdDTOMapper;
    }

    @GetMapping("/v1/user/{id}")
    public ResponseEntity<Object> handle(@PathVariable(value = "id") Long id) {
        Either<Error, UserEntity> either = getUserByIdUseCase.execute(id);

        if (either.isLeft() && either.getLeft().isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(either.getLeft().get().getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(getUserByIdDTOMapper.toResponse(either.getRight().get()));
    }

}
