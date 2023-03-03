package com.company.cryptoexchangetask.controller;

import com.company.cryptoexchangetask.dto.currency.CurrencyRequest;
import com.company.cryptoexchangetask.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CurrencyRequest currencyRequest) throws URISyntaxException {
        var currency = currencyService.add(currencyRequest.getCurrencyName());
        return ResponseEntity
                .created(new URI("/api/v1/currencies/" + currency.getId()))
                .body(Map.of("currency_name", currency.getCurrencyName()));
    }
}
