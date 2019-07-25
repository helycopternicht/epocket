package com.helycopternicht.epocket.services.impl;

import com.helycopternicht.epocket.api.CurrencyBalance;
import com.helycopternicht.epocket.api.TransactionRequest;
import com.helycopternicht.epocket.api.UserBalanceRequest;
import com.helycopternicht.epocket.api.UserBalanceResponse;
import com.helycopternicht.epocket.exceptions.InsufficientFondsException;
import com.helycopternicht.epocket.models.Currency;
import com.helycopternicht.epocket.models.Transaction;
import com.helycopternicht.epocket.models.TransactionTypes;
import com.helycopternicht.epocket.repositories.CurrencyRepository;
import com.helycopternicht.epocket.repositories.TransactionRepository;
import com.helycopternicht.epocket.services.UserBalanceService;
import com.helycopternicht.epocket.services.WalletService;
import com.helycopternicht.epocket.services.dtos.UserBalanceResponseDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final CurrencyRepository currencyRepository;
    private final TransactionRepository transactionRepository;
    private final UserBalanceService userBalanceService;

    @Override
    @Transactional
    public void doDeposit(@NonNull TransactionRequest request) {

        Currency currency = currencyRepository.findByName(request.getCurrency().name()).orElseThrow(() ->
                new IllegalArgumentException("Currency not found")
        );

        Transaction deposit = Transaction.builder()
                .userId(request.getUserId())
                .currency(currency)
                .amount(BigDecimal.valueOf(request.getAmount()))
                .transactionType(TransactionTypes.DEPOSIT)
                .build();

        transactionRepository.save(deposit);
    }

    @Override
    @Transactional
    public void doWithdraw(@NonNull TransactionRequest request) {

        Currency currency = currencyRepository.findByName(request.getCurrency().name()).orElseThrow(() ->
            new IllegalArgumentException("Currency not found")
        );

        UserBalanceResponseDto userBalance = userBalanceService.getUserBalance(request.getUserId());
        BigDecimal currentBalance = userBalance.getBalance().get(currency);

        if (null == currentBalance || currentBalance.doubleValue() < request.getAmount()) {
            throw new InsufficientFondsException("Insufficient fonds");
        }

        Transaction withdraw = Transaction.builder()
                .userId(request.getUserId())
                .currency(currency)
                .amount(BigDecimal.valueOf(request.getAmount()))
                .transactionType(TransactionTypes.WITHDRAW)
                .build();

        transactionRepository.save(withdraw);
    }

    @Override
    @Transactional(readOnly = true)
    public UserBalanceResponse getUserBalance(@NonNull UserBalanceRequest request) {

        UserBalanceResponseDto userBalance = userBalanceService.getUserBalance(request.getUserId());
        UserBalanceResponse.Builder builder = UserBalanceResponse.newBuilder();

        builder.setUserId(request.getUserId());
        userBalance.getBalance().forEach((currency, sum) ->
            builder.addBalances(
                    CurrencyBalance.newBuilder()
                            .setCurrency(currency.getCurrency())
                            .setBalance(sum.doubleValue())
                            .build()
            )
        );

        return builder.build();
    }
}
