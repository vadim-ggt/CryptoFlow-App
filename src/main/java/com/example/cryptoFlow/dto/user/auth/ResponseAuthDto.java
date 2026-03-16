package com.example.cryptoFlow.dto.user.auth;

public record ResponseAuthDto(
        String accessToken,
        String refreshToken
) {}
