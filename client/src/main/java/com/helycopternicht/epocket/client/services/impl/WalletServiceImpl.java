package com.helycopternicht.epocket.client.services.impl;

import com.helycopternicht.epocket.api.UserBalanceResponse;
import com.helycopternicht.epocket.api.WalletServiceGrpc;
import com.helycopternicht.epocket.client.services.WalletService;
import com.helycopternicht.epocket.client.services.dtos.TransactionRequestDto;
import com.helycopternicht.epocket.client.services.dtos.UserBalanceRequestDto;
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
    public void doDeposit(@NonNull TransactionRequestDto request) {

        try {
            stub.doDeposit(request.toTransactionRequest());
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {0}", e.getStatus());
            throw e;
        }
    }

    @Override
    public void doWithdraw(@NonNull TransactionRequestDto request) {

        try {
            stub.doWithdraw(request.toTransactionRequest());
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {0}", e.getStatus());
            throw e;
        }
    }

    @Override
    public UserBalanceResponse getUserBalance(UserBalanceRequestDto request) {

        UserBalanceResponse response;
        try {
            response = stub.getUserBalance(request.toUserBalanceRequest());
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {0}", e.getStatus());
            throw e;
        }
        return response;
    }
}
