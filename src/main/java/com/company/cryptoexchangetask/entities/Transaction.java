package com.company.cryptoexchangetask.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="transactions", schema = "public")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id", nullable = false)
    private Long id;

    @Column(name="transactions_date")
    private LocalDateTime date;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "currency_from_id", nullable = false)
    private Currency currencyFrom;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "currency_to_id", nullable = false)
    private Currency currencyTo;

    @Column(name="money_amount_from")
    private double moneyAmtFrom;

    @Column(name="money_amount_to")
    private double moneyAmtTo;
}
