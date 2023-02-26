package com.company.cryptoexchangetask.repos;

import com.company.cryptoexchangetask.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency, Integer> {
}
