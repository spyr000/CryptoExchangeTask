package com.company.cryptoexchangetask.service;

import com.company.cryptoexchangetask.entity.ExchangeRate;

import java.util.HashMap;
import java.util.Map;

public interface ExchangeRateService {
    ExchangeRate add(ExchangeRate exchangeRate);
    ExchangeRate getLatest(String inputCurrencyName, String outputCurrencyName);

    HashMap<String, Double> getLatest(String inputCurrencyName);

    Map<String, Double> changeExchangeRate(Map<String, Double> currencies, String baseCurrencyName);


}
