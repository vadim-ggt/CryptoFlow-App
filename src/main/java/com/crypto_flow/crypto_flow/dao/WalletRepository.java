package com.crypto_flow.crypto_flow.dao;

import com.crypto_flow.crypto_flow.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
