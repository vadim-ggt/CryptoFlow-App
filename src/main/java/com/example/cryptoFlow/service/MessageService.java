package com.example.cryptoFlow.service;

import com.example.cryptoFlow.dto.message.CreateMessageDto;
import com.example.cryptoFlow.dto.message.MessageDto;

import java.util.List;

public interface MessageService {

    MessageDto saveMessage(CreateMessageDto createMessageDto);

    List<MessageDto> getChatMessages(Long chatId, Long currentUserId);
}
