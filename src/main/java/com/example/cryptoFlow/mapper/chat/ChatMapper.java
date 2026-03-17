package com.example.cryptoFlow.mapper.chat;

import com.example.cryptoFlow.dto.chat.ChatDto;
import com.example.cryptoFlow.entity.Chat;
import com.example.cryptoFlow.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface ChatMapper extends BaseMapper<Chat, ChatDto> {
}
