package com.example.cryptoFlow.mapper.message;

import com.example.cryptoFlow.dto.message.MessageDto;
import com.example.cryptoFlow.entity.Message;
import com.example.cryptoFlow.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapper.class)
public interface MessageMapper extends BaseMapper<Message, MessageDto> {


    @Override
    @Mapping(source = "chat.id", target = "chatId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.nickname", target = "senderNickname")
    MessageDto toDto(Message message);
}
