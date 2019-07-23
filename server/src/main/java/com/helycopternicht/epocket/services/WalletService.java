package com.helycopternicht.epocket.services;

import com.helycopternicht.epocket.api.TransactionRequest;
import com.helycopternicht.epocket.api.UserBalanceRequest;
import com.helycopternicht.epocket.api.UserBalanceResponse;
import lombok.NonNull;

public interface WalletService {

    void doDeposit(@NonNull TransactionRequest request);

    void doWithdraw(@NonNull TransactionRequest request);

    UserBalanceResponse getUserBalance(@NonNull UserBalanceRequest request);
}
