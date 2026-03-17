package com.example.cryptoFlow.controller;

import com.example.cryptoFlow.dto.chat.ChatDto;
import com.example.cryptoFlow.dto.message.CreateMessageDto;
import com.example.cryptoFlow.dto.message.MessageDto;
import com.example.cryptoFlow.entity.User;
import com.example.cryptoFlow.service.ChatService;
import com.example.cryptoFlow.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<ChatDto>> getMyChats(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(chatService.getUserChats(currentUser.getId()));
    }

    @PostMapping("/direct/{targetUserId}")
    public ResponseEntity<ChatDto> getOrCreateDirectChat(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(chatService.getOrCreateDirectChat(currentUser.getId(), targetUserId));
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<MessageDto>> getChatMessages(
            @PathVariable Long chatId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(messageService.getChatMessages(chatId, currentUser.getId()));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDto> sendMessage(
            @RequestBody CreateMessageDto dto,
            @AuthenticationPrincipal User currentUser) {

        // Создаем безопасный DTO, принудительно вшивая ID реального отправителя из токена
        CreateMessageDto secureDto = new CreateMessageDto(dto.chatId(), currentUser.getId(), dto.content());
        return ResponseEntity.ok(messageService.saveMessage(secureDto));
    }
}
