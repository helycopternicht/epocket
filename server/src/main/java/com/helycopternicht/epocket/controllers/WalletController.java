package com.helycopternicht.epocket.controllers;

import com.helycopternicht.epocket.api.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class WalletController extends WalletServiceGrpc.WalletServiceImplBase {

    @Override
    public void doDeposit(DepositRequest request, StreamObserver<Empty> responseObserver) {
        super.doDeposit(request, responseObserver);
    }

    @Override
    public void doWithdraw(WithdrawRequest request, StreamObserver<Empty> responseObserver) {
        super.doWithdraw(request, responseObserver);
    }

    @Override
    public void getUserBalance(UserBalanceRequest request, StreamObserver<UserBalanceResponse> responseObserver) {
        super.getUserBalance(request, responseObserver);
    }
}
