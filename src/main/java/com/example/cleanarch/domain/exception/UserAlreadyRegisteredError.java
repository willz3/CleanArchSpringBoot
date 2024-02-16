package com.example.cleanarch.domain.exception;

public class UserAlreadyRegisteredError extends Error {
    public UserAlreadyRegisteredError() {
        super("User already registered.");
    }
}
