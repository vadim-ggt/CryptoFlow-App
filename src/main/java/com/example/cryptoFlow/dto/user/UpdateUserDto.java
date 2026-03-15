package com.example.cryptoFlow.dto.user;

public record UpdateUserDto(
        String nickname,
        String bio,
        String favoriteCoin
) {}
