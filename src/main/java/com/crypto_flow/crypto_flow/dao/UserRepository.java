package com.crypto_flow.crypto_flow.dao;

import com.crypto_flow.crypto_flow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
