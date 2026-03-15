package com.example.cryptoFlow.service;

import com.example.cryptoFlow.dto.user.ResponseUserDto;
import com.example.cryptoFlow.dto.user.UpdateUserDto;
import com.example.cryptoFlow.dto.user.UserRegisterDto;

public interface UserService {
    ResponseUserDto registerUser(UserRegisterDto user);
    ResponseUserDto getUserByEmail(String email);
    ResponseUserDto getUserByNickname(String nickname);
    ResponseUserDto getUserById(Long id);
    ResponseUserDto updateUser(Long id, UpdateUserDto updateUser);
    void deleteUser(Long id);
}
