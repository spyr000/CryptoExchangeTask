package com.company.cryptoexchangetask.service;

import java.time.LocalDateTime;

public interface TransactionService {
    int countTransactionsBetween(LocalDateTime from, LocalDateTime to);
}
