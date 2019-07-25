package com.helycopternicht.epocket.endpoints;

import com.helycopternicht.epocket.api.*;
import com.helycopternicht.epocket.services.WalletService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class WalletEndpoint extends WalletServiceGrpc.WalletServiceImplBase {

    private final WalletService walletService;

    public WalletEndpoint(@NonNull WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public void doDeposit(TransactionRequest request, StreamObserver<Empty> responseObserver) {
        try {
            walletService.doDeposit(request);
        } catch (RuntimeException ex) {
            responseObserver.onError(ex);
            return;
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void doWithdraw(TransactionRequest request, StreamObserver<Empty> responseObserver) {
        try {
            walletService.doWithdraw(request);
        } catch (RuntimeException ex) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(ex.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserBalance(UserBalanceRequest request, StreamObserver<UserBalanceResponse> responseObserver) {
        UserBalanceResponse userBalance;
        try {
            userBalance = walletService.getUserBalance(request);
        } catch (RuntimeException ex) {
            responseObserver.onError(ex);
            return;
        }
        responseObserver.onNext(userBalance);
        responseObserver.onCompleted();
    }
}
