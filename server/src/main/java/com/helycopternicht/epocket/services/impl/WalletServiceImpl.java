package com.helycopternicht.epocket.services.impl;

import com.helycopternicht.epocket.api.*;
import com.helycopternicht.epocket.models.Currency;
import com.helycopternicht.epocket.models.Transaction;
import com.helycopternicht.epocket.models.TransactionTypes;
import com.helycopternicht.epocket.models.User;
import com.helycopternicht.epocket.repositories.CurrencyRepository;
import com.helycopternicht.epocket.repositories.TransactionRepository;
import com.helycopternicht.epocket.repositories.TransactionTypeRepository;
import com.helycopternicht.epocket.repositories.UserRepository;
import com.helycopternicht.epocket.services.UserBalanceService;
import com.helycopternicht.epocket.services.WalletService;
import com.helycopternicht.epocket.services.dtos.UserBalanceResponseDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionRepository transactionRepository;
    private final UserBalanceService userBalanceService;

    public WalletServiceImpl(@NonNull UserRepository userRepository,
                             @NonNull CurrencyRepository currencyRepository,
                             @NonNull TransactionTypeRepository transactionTypeRepository,
                             @NonNull UserBalanceService userBalanceService,
                             @NonNull TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionRepository = transactionRepository;
        this.userBalanceService = userBalanceService;
    }

    @Override
    public void doDeposit(@NonNull DepositRequest request) {

        Currency currency = currencyRepository.findByName(request.getCurrency())
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Cant find user"));

        if (0 > request.getAmount()) {
            throw new IllegalArgumentException("Amount cant be lass than zero");
        }

        Transaction deposit = Transaction.builder()
                .user(user)
                .currency(currency)
                .amount(BigDecimal.valueOf(request.getAmount()))
                .transactionType(transactionTypeRepository.findById(TransactionTypes.DEPOSIT.getId()).get())
                .build();

        transactionRepository.save(deposit);
    }

    @Override
    public void doWithdraw(@NonNull WithdrawRequest request) {
        Currency currency = currencyRepository.findByName(request.getCurrency())
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Cant find user"));

        if (0 > request.getAmount()) {
            throw new IllegalArgumentException("Amount cant be lass than zero");
        }

        UserBalanceResponseDto userBalance = userBalanceService.getUserBalance(request.getUserId());
        BigDecimal currentBalance = userBalance.getBalance().get(currency);

        if (null == currentBalance || currentBalance.doubleValue() < request.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        Transaction withdraw = Transaction.builder()
                .user(user)
                .currency(currency)
                .amount(BigDecimal.valueOf(request.getAmount()))
                .transactionType(transactionTypeRepository.findById(TransactionTypes.WITHDRAW.getId()).get())
                .build();

        transactionRepository.save(withdraw);
    }

    @Override
    public UserBalanceResponse getUserBalance(@NonNull UserBalanceRequest request) {
        UserBalanceResponseDto userBalance = userBalanceService.getUserBalance(request.getUserId());
        UserBalanceResponse.Builder builder = UserBalanceResponse.newBuilder();
        userBalance.getBalance().forEach((currency, sum) ->
            builder.putBalances(currency.getName(), sum.doubleValue())
        );
        return builder.build();
    }
}
