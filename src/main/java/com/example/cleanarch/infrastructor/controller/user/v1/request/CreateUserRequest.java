package com.example.cleanarch.infrastructor.controller.user.v1.request;

public record CreateUserRequest(String userName, String password, String email) {
}
