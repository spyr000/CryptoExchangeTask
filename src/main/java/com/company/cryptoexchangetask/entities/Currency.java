package com.company.cryptoexchangetask.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currencies", schema = "public")
public class Currency implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "currency_id")
    private Long id;

    @NotEmpty(message = "Currency name can not be empty")
    @Column(name = "currency_name")
    private String currencyName;
}
