package com.company.cryptoexchangetask.controller;

import com.company.cryptoexchangetask.dto.exchange.ExchangeRateRequest;
import com.company.cryptoexchangetask.dto.exchange.LatestExchangeRateRequest;
import com.company.cryptoexchangetask.entity.ExchangeRate;
import com.company.cryptoexchangetask.exception.ParseJsonException;
import com.company.cryptoexchangetask.service.CurrencyService;
import com.company.cryptoexchangetask.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/exchange-rates")
public class ExchangeRateController {
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;

    @PostMapping("/admin")
    public ResponseEntity<?> add(@RequestBody ExchangeRateRequest exchangeRateRequest) throws URISyntaxException {
        var inputCurrency = currencyService.get(exchangeRateRequest.getInputCurrencyName());
        var outputCurrency = currencyService.get(exchangeRateRequest.getOutputCurrencyName());
        var outPutCost = exchangeRateRequest.getOutputCost();
        var exchangeRate = exchangeRateService.add(
                new ExchangeRate(
                        inputCurrency,
                        outputCurrency,
                        outPutCost
                )
        );
        exchangeRateService.add(
                new ExchangeRate(
                        outputCurrency,
                        inputCurrency,
                        1. / outPutCost
                )
        );
        return ResponseEntity
                .created(new URI("/api/v1/exchange-rates/" + exchangeRate.getId()))
                .body(Map.of("exchange_rate_id", exchangeRate.getId()));
    }

    @PostMapping("/rate")
    public ResponseEntity<?> getLatestRate(@RequestBody LatestExchangeRateRequest latest) {
        var coefficient = exchangeRateService
                .getLatest(
                        latest.getInputCurrencyName(),
                        latest.getOutputCurrencyName()
                ).getCurrencyCoefficient();
        return ResponseEntity
                .ok()
                .body(Map.of("currency_coefficient", coefficient));
    }

    @PostMapping("/rates")
    public ResponseEntity<HashMap<String, Double>> getLatestRates(@RequestBody LatestExchangeRateRequest latest) {
        var inputCurrencyName = latest.getInputCurrencyName();
        var namesAndRates = exchangeRateService
                .getLatest(
                        inputCurrencyName
                );

        return ResponseEntity
                .ok()
                .body(namesAndRates);
    }

    @PutMapping("/admin/change")
    public ResponseEntity<Map<String, Double>> change(@RequestBody Map<String, String> changeRequest) {
        var baseCurrencyName = "";
        var currencies = new HashMap<String, Double>();
        try {
            for (String key : changeRequest.keySet()) {
                if (key.equals("base_currency")) {
                    baseCurrencyName = changeRequest.get(key);
                } else {
                    currencies.put(key, Double.parseDouble(changeRequest.get(key)));
                }
            }
        } catch (Exception e) {
            throw new ParseJsonException(HttpStatus.BAD_REQUEST, "Unable to parse request json");
        }
        var response = exchangeRateService.changeExchangeRate(currencies, baseCurrencyName);

        return ResponseEntity
                .ok()
                .body(response);
    }
}
