package com.company.cryptoexchangetask.repos;

import com.company.cryptoexchangetask.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Integer> {
}
