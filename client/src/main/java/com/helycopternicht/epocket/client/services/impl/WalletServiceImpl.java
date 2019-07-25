package com.helycopternicht.epocket.client.services.impl;

import com.helycopternicht.epocket.api.TransactionRequest;
import com.helycopternicht.epocket.api.UserBalanceRequest;
import com.helycopternicht.epocket.api.UserBalanceResponse;
import com.helycopternicht.epocket.api.WalletServiceGrpc;
import com.helycopternicht.epocket.client.services.WalletService;
import io.grpc.StatusRuntimeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletServiceGrpc.WalletServiceBlockingStub stub;

    @Override
    public void doDeposit(@NonNull TransactionRequest request) {

        try {
            stub.doDeposit(request);
            log.info("DO DEPOSIT {} {}, user id: {}", request.getAmount(), request.getCurrency(), request.getUserId());
        } catch (StatusRuntimeException e) {
            log.warn("Deposit failed: {} with message {}", e.getStatus(), e.getMessage());
            // TODO: error handling code should be here
        }
    }

    @Override
    public void doWithdraw(@NonNull TransactionRequest request) {

        try {
            stub.doWithdraw(request);
            log.info("DO WITHDRAW {} {}, user id: {}", request.getAmount(), request.getCurrency(), request.getUserId());
        } catch (StatusRuntimeException e) {
            log.warn("Withdraw failed: {} with message {}", e.getStatus(), e.getMessage());
            // TODO: error handling code should be here
        }
    }

    @Override
    public UserBalanceResponse getUserBalance(UserBalanceRequest request) {

        UserBalanceResponse response = null;
        try {
            response = stub.getUserBalance(request);
            log.info("DO BALANCE, user id: {}", request.getUserId());
        } catch (StatusRuntimeException e) {
            log.warn("Balance failed: {} with message {}", e.getStatus(), e.getMessage());
            // TODO: error handling code should be here
        }
        return response;
    }
}
