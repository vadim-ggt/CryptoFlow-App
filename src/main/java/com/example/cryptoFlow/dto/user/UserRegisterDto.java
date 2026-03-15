package com.example.cryptoFlow.dto.user;

public record UserRegisterDto(
        String email,
        String nickname,
        String password
) {}
