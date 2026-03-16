package com.example.cryptoFlow.controller;

import com.example.cryptoFlow.dto.user.auth.LoginDto;
import com.example.cryptoFlow.dto.user.auth.RefreshTokenDto;
import com.example.cryptoFlow.dto.user.auth.ResponseAuthDto;
import com.example.cryptoFlow.dto.user.auth.UserRegisterDto;
import com.example.cryptoFlow.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseAuthDto> register(@RequestBody UserRegisterDto userRegisterDto) {
        ResponseAuthDto responseAuthDto = authenticationService.register(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseAuthDto);

    }

    @PostMapping("/login")
    public ResponseEntity<ResponseAuthDto> login(@RequestBody LoginDto loginDto) {
        ResponseAuthDto responseAuthDto = authenticationService.login(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseAuthDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseAuthDto> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto ) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenDto));
    }


}
