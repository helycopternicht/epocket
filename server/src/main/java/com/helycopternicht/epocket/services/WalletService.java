package com.helycopternicht.epocket.services;

import com.helycopternicht.epocket.api.DepositRequest;
import com.helycopternicht.epocket.api.UserBalanceRequest;
import com.helycopternicht.epocket.api.UserBalanceResponse;
import com.helycopternicht.epocket.api.WithdrawRequest;
import lombok.NonNull;

public interface WalletService {

    void doDeposit(@NonNull DepositRequest request);

    void doWithdraw(@NonNull WithdrawRequest request);

    UserBalanceResponse getUserBalance(@NonNull UserBalanceRequest request);
}
