package com.example.cleanarch.domain.exception;

public class UserNotFoundError extends Error {
    public UserNotFoundError() {
        super("User not found.");
    }
}
