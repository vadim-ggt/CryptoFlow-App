package com.example.cryptoFlow.dto.user;

public record ResponseUserDto(
        Long id,
        String email,
        String nickname,
        String bio,
        String favoriteCoin
) {}
