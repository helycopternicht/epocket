package com.helycopternicht.epocket.client.tasks;

import com.helycopternicht.epocket.api.Currency;

public class RoundC extends Round {

    @Override
    public void execute() {
        balance();
        deposit(100, Currency.USD);
        deposit(100, Currency.USD);
        withdraw(100, Currency.USD);
        deposit(100, Currency.USD);
        balance();
        withdraw(200, Currency.USD);
        balance();
    }
}
