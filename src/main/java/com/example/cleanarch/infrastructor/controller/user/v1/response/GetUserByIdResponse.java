package com.example.cleanarch.infrastructor.controller.user.v1.response;

public record GetUserByIdResponse (Long id, String userName, String email){}