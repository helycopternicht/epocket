package com.helycopternicht.epocket.models;

import lombok.Getter;

@Getter
public enum TransactionTypes {

    DEPOSIT(1L),
    WITHDRAW(2L);

    private Long id;

    TransactionTypes(long id) {
        this.id = id;
    }
}
