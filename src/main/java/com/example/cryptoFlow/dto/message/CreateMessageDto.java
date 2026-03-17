package com.example.cryptoFlow.dto.message;

public record CreateMessageDto(
        Long chatId,
        Long senderId,
        String content
) {}
