package com.helycopternicht.epocket.client.tasks;

import com.helycopternicht.epocket.api.Currency;

public class RoundB extends Round {

    @Override
    public void execute() {
        withdraw(100, Currency.GBP);
        deposit(300, Currency.GBP);
        withdraw(100, Currency.GBP);
        withdraw(100, Currency.GBP);
        withdraw(100, Currency.GBP);
    }
}
