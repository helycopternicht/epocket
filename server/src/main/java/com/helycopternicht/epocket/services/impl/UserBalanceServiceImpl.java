package com.helycopternicht.epocket.services.impl;

import com.helycopternicht.epocket.models.Balance;
import com.helycopternicht.epocket.models.Currency;
import com.helycopternicht.epocket.models.TransactionTypes;
import com.helycopternicht.epocket.models.User;
import com.helycopternicht.epocket.services.UserBalanceService;
import com.helycopternicht.epocket.services.dtos.UserBalanceResponseDto;
import com.helycopternicht.epocket.services.dtos.UserResponseDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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

    public UserBalanceServiceImpl(@NonNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserBalanceResponseDto getUserBalance(User user) {
        Query balanceQuery = entityManager.createNativeQuery(balanceQueryText, Balance.class);
        balanceQuery.setParameter("positive", TransactionTypes.DEPOSIT.toString());
        balanceQuery.setParameter("negative", TransactionTypes.WITHDRAW.toString());
        balanceQuery.setParameter("userId", user.getId());

        List<Balance> balance = balanceQuery.getResultList();
        Map<Currency, BigDecimal> map = balance.stream()
                .collect(Collectors.toMap(Balance::getCurrency, Balance::getBalance));

        return UserBalanceResponseDto.builder()
                .user(UserResponseDto.builder().id(user.getId()).name(user.getName()).build())
                .balance(map)
                .build();
    }
}
