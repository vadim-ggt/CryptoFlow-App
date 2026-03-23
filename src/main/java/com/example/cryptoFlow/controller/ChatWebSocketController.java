package com.example.cryptoFlow.controller;

import com.example.cryptoFlow.dto.message.CreateMessageDto;
import com.example.cryptoFlow.dto.message.MessageDto;
import com.example.cryptoFlow.entity.User;
import com.example.cryptoFlow.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload CreateMessageDto dto, Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        User currentUser = (User) token.getPrincipal();

        CreateMessageDto secureDto = new CreateMessageDto(
                dto.chatId(),
                currentUser.getId(),
                dto.content()
        );

        MessageDto savedMessage = messageService.saveMessage(secureDto);

        messagingTemplate.convertAndSend("/topic/chat/" + savedMessage.chatId(), savedMessage);
    }
}
