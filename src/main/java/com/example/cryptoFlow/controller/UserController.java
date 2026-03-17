package com.example.cryptoFlow.controller;

import com.example.cryptoFlow.dto.user.ResponseUserDto;
import com.example.cryptoFlow.dto.user.UpdateUserDto;
import com.example.cryptoFlow.dto.user.auth.UserRegisterDto;
import com.example.cryptoFlow.security.annotation.IsOwnerOrAdmin;
import com.example.cryptoFlow.security.annotation.IsUserOrAdmin;
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

    @GetMapping("/{id}")
    @IsUserOrAdmin
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping(params = "email")
    @IsUserOrAdmin
    public ResponseEntity<ResponseUserDto> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping(params = "nickname")
    @IsUserOrAdmin
    public ResponseEntity<ResponseUserDto> getUserByNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.getUserByNickname(nickname));
    }

    @PutMapping("/{id}")
    @IsOwnerOrAdmin
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    @IsOwnerOrAdmin
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
