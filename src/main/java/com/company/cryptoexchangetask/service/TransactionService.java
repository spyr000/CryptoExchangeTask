package com.company.cryptoexchangetask.service;

import com.company.cryptoexchangetask.exception.UnableToCountTransactionsException;
import com.company.cryptoexchangetask.repo.TransactionRepo;
import com.company.cryptoexchangetask.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;

    public int countTransactionsBetween(LocalDateTime from, LocalDateTime to) {
        var transactionCount = transactionRepo.countByDateBetween(from, to)
                .orElseThrow(() -> new UnableToCountTransactionsException(from, to));
        if (transactionCount == null) {
            throw new UnableToCountTransactionsException(from, to);
        }
        return transactionCount;
    }


}
