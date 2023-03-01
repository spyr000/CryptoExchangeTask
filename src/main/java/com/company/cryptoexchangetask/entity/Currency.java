package com.company.cryptoexchangetask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "currencies", schema = "public")
public class Currency implements Serializable {
    public Currency(String currencyName) {
        this.currencyName = currencyName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "currency_id", nullable = false)
    private Long id;

    @Column(name = "currency_name", unique = true, nullable = false)
    private String currencyName;
}
