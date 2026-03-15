package com.example.cryptoFlow.mapper.user;

import com.example.cryptoFlow.dto.user.ResponseUserDto;
import com.example.cryptoFlow.dto.user.UpdateUserDto;
import com.example.cryptoFlow.entity.User;
import com.example.cryptoFlow.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapper.class)
public interface UserMapper extends BaseMapper<User, ResponseUserDto> {

    void merge(@MappingTarget User entity, UpdateUserDto dto);
}
