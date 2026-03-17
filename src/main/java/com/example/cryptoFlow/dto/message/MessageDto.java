package com.example.cryptoFlow.dto.message;

import java.time.LocalDateTime;

public record MessageDto(
        Long id,
        Long chatId,
        Long senderId,
        String senderNickname,
        String content,
        LocalDateTime createdAt
) {}
