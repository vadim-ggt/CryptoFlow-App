package com.example.cryptoFlow.service;

import com.example.cryptoFlow.dto.chat.ChatDto;

import java.util.List;

public interface ChatService {
    ChatDto getOrCreateDirectChat(Long currentUserId, Long targetUserId);
    List<ChatDto> getUserChats(Long userId);
}
