package com.example.cryptoFlow.dao;

import com.example.cryptoFlow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String username);

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
}
