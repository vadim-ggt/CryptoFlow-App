package com.example.cryptoFlow.controller;

import com.example.cryptoFlow.dto.user.ResponseUserDto;
import com.example.cryptoFlow.dto.user.UpdateUserDto;
import com.example.cryptoFlow.dto.user.UserRegisterDto;
import com.example.cryptoFlow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        ResponseUserDto responseUserDto = userService.registerUser(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping(params = "email")
    public ResponseEntity<ResponseUserDto> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping(params = "nickname")
    public ResponseEntity<ResponseUserDto> getUserByNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.getUserByNickname(nickname));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
