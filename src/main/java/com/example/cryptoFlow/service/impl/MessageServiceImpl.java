package com.example.cryptoFlow.service.impl;

import com.example.cryptoFlow.dao.ChatMemberRepository;
import com.example.cryptoFlow.dao.ChatRepository;
import com.example.cryptoFlow.dao.MessageRepository;
import com.example.cryptoFlow.dao.UserRepository;
import com.example.cryptoFlow.dto.message.CreateMessageDto;
import com.example.cryptoFlow.dto.message.MessageDto;
import com.example.cryptoFlow.entity.Chat;
import com.example.cryptoFlow.entity.Message;
import com.example.cryptoFlow.entity.User;
import com.example.cryptoFlow.exception.NotFoundException;
import com.example.cryptoFlow.mapper.message.MessageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authorization.AuthorizationDeniedException;
import com.example.cryptoFlow.service.ChatService;
import com.example.cryptoFlow.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {


    private final ChatMemberRepository chatMemberRepository;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageDto saveMessage(CreateMessageDto createMessageDto) {
        Chat chat = chatRepository.findById(createMessageDto.chatId())
                .orElseThrow(() -> new NotFoundException("Chat not found"));
        User sender = userRepository.findById(createMessageDto.senderId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!chatMemberRepository.existsByChatIdAndUserId(chat.getId(), sender.getId())) {
            throw new AuthorizationDeniedException("You are not a member of this chat");
        }

        Message message = Message.builder()
                .chat(chat)
                .sender(sender)
                .content(createMessageDto.content())
                .build();

        message = messageRepository.save(message);

        return messageMapper.toDto(message);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<MessageDto> getChatMessages(Long chatId, Long currentUserId, int page, int size) {
        if (!chatMemberRepository.existsByChatIdAndUserId(chatId, currentUserId)) {
            throw new AuthorizationDeniedException("You are not a member of this chat");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Message> messagesPage = messageRepository.findAllByChatIdOrderByCreatedAtDesc(chatId, pageable);

        return messagesPage.map(messageMapper::toDto);
    }
}
