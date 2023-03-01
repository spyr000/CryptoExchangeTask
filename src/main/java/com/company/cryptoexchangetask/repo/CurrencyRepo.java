package com.company.cryptoexchangetask.repo;

import com.company.cryptoexchangetask.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency, Integer> {
    Optional<Currency> findByCurrencyName(String currencyName);
}
