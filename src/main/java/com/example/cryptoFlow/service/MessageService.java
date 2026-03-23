package com.example.cryptoFlow.service;

import com.example.cryptoFlow.dto.message.CreateMessageDto;
import com.example.cryptoFlow.dto.message.MessageDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {

    MessageDto saveMessage(CreateMessageDto createMessageDto);

    Page<MessageDto> getChatMessages(Long chatId, Long currentUserId, int page, int size);
}
