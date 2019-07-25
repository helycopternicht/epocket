package com.helycopternicht.epocket.client.tasks;

import com.helycopternicht.epocket.api.Currency;

public class RoundA extends Round {

    @Override
    public void execute() {
        deposit(100, Currency.USD);
        withdraw(200, Currency.USD);
        deposit(100, Currency.EUR);
        balance();
        withdraw(100, Currency.USD);
        balance();
        withdraw(100, Currency.USD);
    }

}
