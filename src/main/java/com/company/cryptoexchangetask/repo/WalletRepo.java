package com.company.cryptoexchangetask.repo;

import com.company.cryptoexchangetask.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepo extends JpaRepository<Wallet, Integer> {
    Optional<Wallet> findWalletByUserSecretKeyAndCurrencyCurrencyName(String secretKey, String currencyName);

    List<Wallet> findWalletByUserSecretKey(String secretKey);
}
