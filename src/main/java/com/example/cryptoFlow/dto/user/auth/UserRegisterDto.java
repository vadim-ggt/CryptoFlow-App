package com.example.cryptoFlow.dto.user.auth;

public record UserRegisterDto(
        String email,
        String nickname,
        String password
) {}
