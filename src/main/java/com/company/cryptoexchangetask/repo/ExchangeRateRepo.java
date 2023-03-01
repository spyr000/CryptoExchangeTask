package com.company.cryptoexchangetask.repo;

import com.company.cryptoexchangetask.entity.Currency;
import com.company.cryptoexchangetask.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepo extends JpaRepository<ExchangeRate, Integer> {
    Optional<ExchangeRate> findByInputCurrencyAndOutputCurrency(
            Currency inputCurrency,
            Currency outputCurrency
    );

    List<ExchangeRate> findAllByInputCurrency(
            Currency currency
    );

    Optional<ExchangeRate> findByInputCurrencyCurrencyNameAndOutputCurrencyCurrencyName(
            String inputCurrencyName,
            String outPutCurrencyName
    );
}
