package com.company.cryptoexchangetask.service;

import com.company.cryptoexchangetask.entity.Currency;
import com.company.cryptoexchangetask.entity.ExchangeRate;
import com.company.cryptoexchangetask.exception.EntityNotFoundException;
import com.company.cryptoexchangetask.repo.CurrencyRepo;
import com.company.cryptoexchangetask.repo.ExchangeRateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrencyService {
    private final CurrencyRepo currencyRepo;
    private final ExchangeRateRepo exchangeRateRepo;

    public Currency add(String currencyName) {
        var currency = new Currency(currencyName);
        currencyRepo.save(currency);
        exchangeRateRepo.save(
                new ExchangeRate(
                        currency,
                        currency,
                        1.
                )
        );
        return currency;
    }

    public Currency get(String currencyName) {
        return currencyRepo.findByCurrencyName(currencyName)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class));
    }
}
