package com.example.cryptoFlow.dao;

import com.example.cryptoFlow.entity.ChatMember;
import com.example.cryptoFlow.entity.ChatMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, ChatMemberId> {
    boolean existsByChatIdAndUserId(Long chatId, Long userId);
}
