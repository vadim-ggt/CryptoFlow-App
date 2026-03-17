package com.example.cryptoFlow.config;

import com.example.cryptoFlow.dao.UserRepository;
import com.example.cryptoFlow.entity.User;
import com.example.cryptoFlow.entity.app_enum.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsByEmail("admin@admin.com")) {

            User admin = User.builder()
                    .email("admin@admin.com")
                    .nickname("SuperAdmin")
                    .role(Role.ROLE_ADMIN)
                    .passwordHash(passwordEncoder.encode("11111111"))
                    .build();
            userRepository.save(admin);

            log.info("admin added");
        } else log.info("admin already exists");
    }
}
