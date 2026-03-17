package com.example.cryptoFlow.dao;

import com.example.cryptoFlow.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("""
           Select c from Chat c
           JOIN  ChatMember cm1 ON c.id = cm1.chat.id
           JOIN  ChatMember cm2 ON c.id = cm2.chat.id
           where c.chatType = 'DIRECT'
           AND cm1.user.id = :userId1
           AND cm2.user.id = :userId2
           """)
    Optional<Chat> findDirectChatByMembers(@Param("userId1") Long userId1,
                                           @Param("userId2") Long userId2);

    @Query("""
            SELECT c FROM Chat c
            JOIN ChatMember cm ON c.id = cm.chat.id
            WHERE cm.user.id = :userId
            ORDER BY c.createdAt DESC
            """)
    List<Chat> findAllChatsByUserId(@Param("userId") Long userId);
}
