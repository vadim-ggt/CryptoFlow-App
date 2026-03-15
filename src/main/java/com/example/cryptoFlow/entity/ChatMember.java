package com.example.cryptoFlow.entity;

import com.example.cryptoFlow.entity.app_enum.ChatRole;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_members")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ChatMember {

    @EmbeddedId
    private ChatMemberId id = new ChatMemberId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chatId")
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private ChatRole memberRole = ChatRole.MEMBER;

    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt;

    @PrePersist
    protected void onCreate() {
        this.joinedAt = LocalDateTime.now();
    }
}
