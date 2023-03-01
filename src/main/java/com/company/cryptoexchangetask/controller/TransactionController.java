package com.company.cryptoexchangetask.controller;

import com.company.cryptoexchangetask.dto.transaction.TransactionCountRequest;
import com.company.cryptoexchangetask.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/admin/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/count")
    public ResponseEntity<?> getTransactionCountForTimeInterval(
            @RequestBody TransactionCountRequest transactionCountRequest
    ) {
        var count = transactionService
                .countTransactionsBetween(
                        transactionCountRequest.getDateFrom(),
                        transactionCountRequest.getDateTo()
                );
        return ResponseEntity
                .ok()
                .body(Map.of("transaction_count", count));
    }
}
