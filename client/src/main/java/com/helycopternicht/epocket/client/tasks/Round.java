package com.helycopternicht.epocket.client.tasks;

import com.helycopternicht.epocket.api.Currency;
import com.helycopternicht.epocket.api.TransactionRequest;
import com.helycopternicht.epocket.api.UserBalanceRequest;
import com.helycopternicht.epocket.client.services.WalletService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public abstract class Round {

    private WalletService walletService;
    private Long userId;

    public abstract void execute();

    protected void deposit(double amount, @NonNull Currency currency) {
        QueryCounter.increment();
        walletService.doDeposit(createTransactionRequest(amount, currency));
    }

    protected void withdraw(double amount, @NonNull Currency currency) {
        QueryCounter.increment();
        walletService.doWithdraw(createTransactionRequest(amount, currency));
    }

    protected void balance() {
        QueryCounter.increment();
        walletService.getUserBalance(createBalanceRequest());
    }

    private TransactionRequest createTransactionRequest(double amount, @NonNull Currency currency) {
        return TransactionRequest.newBuilder()
                .setUserId(userId)
                .setCurrency(currency)
                .setAmount(amount)
                .build();
    }

    private UserBalanceRequest createBalanceRequest() {
        return UserBalanceRequest.newBuilder()
                .setUserId(userId)
                .build();
    }
}
