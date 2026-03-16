package com.example.cryptoFlow.security;

import com.example.cryptoFlow.dao.UserRepository;
import com.example.cryptoFlow.dto.user.auth.LoginDto;
import com.example.cryptoFlow.dto.user.auth.RefreshTokenDto;
import com.example.cryptoFlow.dto.user.auth.ResponseAuthDto;
import com.example.cryptoFlow.dto.user.auth.UserRegisterDto;
import com.example.cryptoFlow.entity.User;
import com.example.cryptoFlow.entity.app_enum.Role;
import com.example.cryptoFlow.exception.AlreadyExistsException;
import com.example.cryptoFlow.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ResponseAuthDto register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByEmail(userRegisterDto.email())){
            throw new AlreadyExistsException("Email already exists");
        }
        if(userRepository.existsByNickname(userRegisterDto.nickname())){
            throw new AlreadyExistsException("Nickname already exists");
        }

        User newUser = User.builder()
                .email(userRegisterDto.email())
                .nickname(userRegisterDto.nickname())
                .role(Role.ROLE_USER)
                .passwordHash(passwordEncoder.encode(userRegisterDto.password()))
                .build();

        userRepository.save(newUser);

        String accessToken = jwtService.generateToken(newUser);
        String refreshToken = jwtService.generateRefreshToken(newUser);

        return new ResponseAuthDto(accessToken, refreshToken);
    }

    public ResponseAuthDto login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
        );

        User user = userRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new NotFoundException("Email not found"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new ResponseAuthDto(accessToken, refreshToken);
    }

    public ResponseAuthDto refreshToken(RefreshTokenDto refreshTokenDto) {
        String userEmail = jwtService.extractUsername(refreshTokenDto.refreshToken());

        if (userEmail != null){
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("Email not found"));

            String accessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);

            return new ResponseAuthDto(accessToken, newRefreshToken);
        } else {
            throw new RuntimeException("Refresh token not found");
        }
    }


}
