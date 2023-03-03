package com.company.cryptoexchangetask.service.impl;

import com.company.cryptoexchangetask.entity.Currency;
import com.company.cryptoexchangetask.entity.ExchangeRate;
import com.company.cryptoexchangetask.exception.BaseException;
import com.company.cryptoexchangetask.exception.EntityNotFoundException;
import com.company.cryptoexchangetask.exception.EntitySavingFailedException;
import com.company.cryptoexchangetask.repo.CurrencyRepo;
import com.company.cryptoexchangetask.repo.ExchangeRateRepo;
import com.company.cryptoexchangetask.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final CurrencyRepo currencyRepo;
    private final ExchangeRateRepo exchangeRateRepo;

    @Override
    public ExchangeRate add(ExchangeRate exchangeRate) {
        Optional<ExchangeRate> exchangeRate1 = exchangeRateRepo.findByInputCurrencyAndOutputCurrency(exchangeRate.getInputCurrency(), exchangeRate.getOutputCurrency());

        if (exchangeRate1.isEmpty()) {
            return exchangeRateRepo.save(exchangeRate);
        }
        return exchangeRate;
    }

    @Override
    public ExchangeRate getLatest(String inputCurrencyName, String outputCurrencyName) {
        var inputCurrency = currencyRepo.findByCurrencyName(inputCurrencyName)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class));
        var outputCurrency = currencyRepo.findByCurrencyName(outputCurrencyName)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class));
        return exchangeRateRepo.findByInputCurrencyAndOutputCurrency(
                inputCurrency,
                outputCurrency
        ).orElseThrow(() -> new EntityNotFoundException(ExchangeRate.class));
    }

    @Override
    public HashMap<String, Double> getLatest(String inputCurrencyName) {
        var currency = currencyRepo.findByCurrencyName(inputCurrencyName)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class));
        var rates = exchangeRateRepo.findAllByInputCurrency(currency);
        var namesAndRates = new HashMap<String, Double>();
        for (ExchangeRate rate : rates) {
            var name = rate.getOutputCurrency().getCurrencyName();
            if (name.equals(inputCurrencyName)) continue;
            namesAndRates.put(
                    name,
                    rate.getCurrencyCoefficient()
            );
        }
        return namesAndRates;
    }

    @Override
    public Map<String, Double> changeExchangeRate(
            Map<String, Double> currencies,
            String baseCurrencyName
    ) {
        var response = new HashMap<String, Double>();
        for (String currencyName : currencies.keySet()) {
            var rate = exchangeRateRepo.findByInputCurrencyCurrencyNameAndOutputCurrencyCurrencyName(
                    baseCurrencyName,
                    currencyName
            ).orElseThrow(() -> new EntityNotFoundException(ExchangeRate.class));

            try {
                var currencyExchangeRate = currencies.get(currencyName);
                rate.setCurrencyCoefficient(currencyExchangeRate);
                exchangeRateRepo.save(rate);
                response.put(currencyName, currencyExchangeRate);
            } catch (EntitySavingFailedException e) {
                throw new EntitySavingFailedException(ExchangeRate.class);
            } catch (Exception e) {
                throw new BaseException(HttpStatus.BAD_REQUEST, "Currency doesn't have value");
            }


            rate = exchangeRateRepo.findByInputCurrencyCurrencyNameAndOutputCurrencyCurrencyName(
                    currencyName,
                    baseCurrencyName
            ).orElseThrow(() -> new EntityNotFoundException(ExchangeRate.class));
            try {
                var currencyExchangeRate = currencies.get(currencyName);
                rate.setCurrencyCoefficient(1. / currencyExchangeRate);
                exchangeRateRepo.save(rate);
                response.put(currencyName, currencyExchangeRate);
            } catch (EntitySavingFailedException e) {
                throw new EntitySavingFailedException(ExchangeRate.class);
            } catch (Exception e) {
                throw new BaseException(HttpStatus.BAD_REQUEST, "Currency doesn't have value");
            }
        }

        return response;
    }
}
