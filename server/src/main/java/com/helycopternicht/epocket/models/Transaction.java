package com.helycopternicht.epocket.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    private ZonedDateTime createdDate;

    @Enumerated
    @Column(name = "transaction_type")
    private TransactionTypes transactionType;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal amount;

    @PrePersist
    private void initCreateDate(){
        createdDate = ZonedDateTime.now();
    }

}
