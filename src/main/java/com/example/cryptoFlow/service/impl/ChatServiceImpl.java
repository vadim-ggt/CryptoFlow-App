package com.example.cryptoFlow.service.impl;

import com.example.cryptoFlow.dao.ChatMemberRepository;
import com.example.cryptoFlow.dao.ChatRepository;
import com.example.cryptoFlow.dao.UserRepository;
import com.example.cryptoFlow.dto.chat.ChatDto;
import com.example.cryptoFlow.entity.Chat;
import com.example.cryptoFlow.entity.ChatMember;
import com.example.cryptoFlow.entity.ChatMemberId;
import com.example.cryptoFlow.entity.User;
import com.example.cryptoFlow.entity.app_enum.ChatRole;
import com.example.cryptoFlow.entity.app_enum.ChatType;
import com.example.cryptoFlow.exception.NotFoundException;
import com.example.cryptoFlow.mapper.chat.ChatMapper;
import com.example.cryptoFlow.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final ChatMemberRepository chatMemberRepository;
    private final UserRepository userRepository;

    @Override
    public ChatDto getOrCreateDirectChat(Long currentUserId, Long targetUserId) {

        Optional<Chat> existingChat = chatRepository.
                findDirectChatByMembers(currentUserId, targetUserId);

        if (existingChat.isPresent()) {
            return chatMapper.toDto(existingChat.get());
        }

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("Current not found"));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new NotFoundException("Target not found"));

        Chat newChat = Chat.builder()
                .chatType(ChatType.DIRECT)
                .name(null)
                .build();
        newChat = chatRepository.save(newChat);

        ChatMember member1 = ChatMember.builder()
                .id(new ChatMemberId(newChat.getId(), currentUser.getId()))
                .user(currentUser)
                .chat(newChat)
                .memberRole(ChatRole.MEMBER)
                .build();


        ChatMember member2 = ChatMember.builder()
                .id(new ChatMemberId(newChat.getId(), targetUser.getId()))
                .user(targetUser)
                .chat(newChat)
                .memberRole(ChatRole.MEMBER)
                .build();


        chatMemberRepository.saveAll(List.of(member1, member2));

        return chatMapper.toDto(newChat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatDto> getUserChats(Long userId) {
        List<Chat> chats = chatRepository.findAllChatsByUserId(userId);
        return chatMapper.toDtoList(chats);
    }
}
