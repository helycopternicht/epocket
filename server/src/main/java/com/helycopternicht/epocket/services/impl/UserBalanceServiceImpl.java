package com.helycopternicht.epocket.services.impl;

import com.helycopternicht.epocket.models.Balance;
import com.helycopternicht.epocket.models.Currency;
import com.helycopternicht.epocket.models.TransactionTypes;
import com.helycopternicht.epocket.repositories.TransactionRepository;
import com.helycopternicht.epocket.services.UserBalanceService;
import com.helycopternicht.epocket.services.dtos.UserBalanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBalanceServiceImpl implements UserBalanceService {

    private final static String balanceQueryText =
                    "SELECT " +
                    "currency_id, " +
                    "SUM(CASE WHEN transaction_type = :positive THEN amount " +
                    "WHEN transaction_type = :negative THEN amount * -1 " +
                    "END) balance " +
                    "FROM transactions " +
                    "WHERE user_id = :userId " +
                    "GROUP BY currency_id;";

    private final EntityManager entityManager;
    private final TransactionRepository transactionRepository;

    @Override
    public UserBalanceResponseDto getUserBalance(Long userId) {
        Query balanceQuery = entityManager.createNativeQuery(balanceQueryText, Balance.class);
        balanceQuery.setParameter("positive", TransactionTypes.DEPOSIT.toString());
        balanceQuery.setParameter("negative", TransactionTypes.WITHDRAW.toString());
        balanceQuery.setParameter("userId", userId);

        List<Balance> balance = balanceQuery.getResultList();
        Map<Currency, BigDecimal> map = balance.stream()
                .collect(Collectors.toMap(Balance::getCurrency, Balance::getBalance));

        return UserBalanceResponseDto.builder()
                .userId(userId)
                .balance(map)
                .build();
    }
}
