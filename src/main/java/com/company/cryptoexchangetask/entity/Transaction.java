package com.company.cryptoexchangetask.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions", schema = "public")
public class Transaction {
    public Transaction(LocalDateTime date, double moneyAmount) {
        this.date = date;
        this.moneyAmount = moneyAmount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id", nullable = false)
    private Long id;
    @Column(name = "transactions_date")
    private LocalDateTime date;
    @Column(name = "money_amount")
    private double moneyAmount;
}
