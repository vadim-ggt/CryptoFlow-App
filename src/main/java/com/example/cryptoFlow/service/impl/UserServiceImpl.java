package com.example.cryptoFlow.service.impl;

import com.example.cryptoFlow.dao.UserRepository;
import com.example.cryptoFlow.dto.user.ResponseUserDto;
import com.example.cryptoFlow.dto.user.UpdateUserDto;
import com.example.cryptoFlow.dto.user.UserRegisterDto;
import com.example.cryptoFlow.entity.User;
import com.example.cryptoFlow.entity.app_enum.Role;
import com.example.cryptoFlow.exception.AlreadyExistsException;
import com.example.cryptoFlow.exception.NotFoundException;
import com.example.cryptoFlow.mapper.user.UserMapper;
import com.example.cryptoFlow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseUserDto registerUser(UserRegisterDto user) {
        if (userRepository.existsByEmail(user.email())){
            throw new AlreadyExistsException("Email already exists");
        }
        if(userRepository.existsByNickname(user.nickname())){
            throw new AlreadyExistsException("Nickname already exists");
        }

        User newUser = User.builder()
                .email(user.email())
                .nickname(user.nickname())
                .role(Role.ROLE_USER)
                .passwordHash(passwordEncoder.encode(user.password()))
                .build();

        User savedUser = userRepository.save(newUser);

        return userMapper.toDto(savedUser);
    }

    @Override
    public ResponseUserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email " +  email));
        return userMapper.toDto(user);
    }

    @Override
    public ResponseUserDto getUserByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException("User not found with nickname " + nickname));
        return userMapper.toDto(user);
    }

    @Override
    public ResponseUserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public ResponseUserDto updateUser(Long id, UpdateUserDto updateUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));

        if (updateUser.nickname() != null && !updateUser.nickname().equals(user.getNickname())) {
            if (userRepository.existsByNickname(updateUser.nickname())) {
                throw new AlreadyExistsException("Nickname already exists");
            }
        }

        userMapper.merge(user, updateUser);

        return userMapper.toDto(userRepository.save(user));
    }


    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));
        userRepository.delete(user);
    }

}
