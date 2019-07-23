package com.helycopternicht.epocket.services.impl;

import com.helycopternicht.epocket.api.*;
import com.helycopternicht.epocket.models.Currency;
import com.helycopternicht.epocket.models.Transaction;
import com.helycopternicht.epocket.models.TransactionTypes;
import com.helycopternicht.epocket.models.User;
import com.helycopternicht.epocket.repositories.CurrencyRepository;
import com.helycopternicht.epocket.repositories.TransactionRepository;
import com.helycopternicht.epocket.repositories.UserRepository;
import com.helycopternicht.epocket.services.UserBalanceService;
import com.helycopternicht.epocket.services.WalletService;
import com.helycopternicht.epocket.services.dtos.UserBalanceResponseDto;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final TransactionRepository transactionRepository;
    private final UserBalanceService userBalanceService;

    @Override
    @Transactional
    public void doDeposit(@NonNull TransactionRequest request) {

        Currency currency = currencyRepository.findByName(request.getCurrency())
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Cant find user"));

        if (0 >= request.getAmount()) {
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT, new Metadata());
        }

        Transaction deposit = Transaction.builder()
                .user(user)
                .currency(currency)
                .amount(BigDecimal.valueOf(request.getAmount()))
                .transactionType(TransactionTypes.DEPOSIT)
                .build();

        transactionRepository.save(deposit);
    }

    @Override
    @Transactional
    public void doWithdraw(@NonNull TransactionRequest request) {
        Currency currency = currencyRepository.findByName(request.getCurrency())
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Cant find user"));

        if (0 >= request.getAmount()) {
            throw new IllegalArgumentException("Amount cant be lass than zero");
        }

        UserBalanceResponseDto userBalance = userBalanceService.getUserBalance(user);
        BigDecimal currentBalance = userBalance.getBalance().get(currency);

        if (null == currentBalance || currentBalance.doubleValue() < request.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        Transaction withdraw = Transaction.builder()
                .user(user)
                .currency(currency)
                .amount(BigDecimal.valueOf(request.getAmount()))
                .transactionType(TransactionTypes.WITHDRAW)
                .build();

        transactionRepository.save(withdraw);
    }

    @Override
    @Transactional(readOnly = true)
    public UserBalanceResponse getUserBalance(@NonNull UserBalanceRequest request) {

        User databaseUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Cant find user"));

        UserBalanceResponseDto userBalance = userBalanceService.getUserBalance(databaseUser);

        UserBalanceResponse.Builder builder = UserBalanceResponse.newBuilder();
        userBalance.getBalance().forEach((currency, sum) ->
            builder.putBalances(currency.getName(), sum.doubleValue())
        );
        builder.setUser(
                com.helycopternicht.epocket.api.User.newBuilder()
                .setUserId(databaseUser.getId())
                .setName(databaseUser.getName())
        );
        return builder.build();
    }
}
