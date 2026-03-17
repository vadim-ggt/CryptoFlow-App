package com.example.cryptoFlow.dto.chat;

import com.example.cryptoFlow.entity.app_enum.ChatType;

public record ChatDto(
   Long id,
   String name,
   ChatType chatType

) {}
