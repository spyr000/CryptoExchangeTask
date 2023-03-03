package com.company.cryptoexchangetask.service;

import com.company.cryptoexchangetask.entity.Currency;

public interface CurrencyService {
    Currency add(String currencyName);
    Currency get(String currencyName);
}
