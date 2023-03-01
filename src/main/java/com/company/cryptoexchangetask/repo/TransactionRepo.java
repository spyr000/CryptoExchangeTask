package com.company.cryptoexchangetask.repo;

import com.company.cryptoexchangetask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    Optional<Integer> countByDateBetween(LocalDateTime from, LocalDateTime to);
}
