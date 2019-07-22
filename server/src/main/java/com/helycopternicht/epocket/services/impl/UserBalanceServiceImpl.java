package com.helycopternicht.epocket.services.impl;

import com.helycopternicht.epocket.models.Balance;
import com.helycopternicht.epocket.services.UserBalanceService;
import com.helycopternicht.epocket.services.dtos.UserBalanceResponseDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserBalanceServiceImpl implements UserBalanceService {

    private final EntityManager entityManager;

    public UserBalanceServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserBalanceResponseDto getUserBalance(Long userId) {
//        Query nativeQuery = entityManager.createNativeQuery(
//                "SELECT\n" +
//                        "  c.name,\n" +
//                        "  b.balance\n" +
//                        "FROM balance b\n" +
//                        "  JOIN currencies c ON c.id = b.currency_id\n" +
//                        "WHERE b.user_id = 1;", Balance.class);
//
//        List<Balance> resultList = nativeQuery.getResultList();
//        Map<String, BigDecimal> map = new LinkedHashMap<>();
//        resultList.stream().forEach(el -> map.put(el.currency, el.balance));
//        return UserBalanceResponseDto.builder().balance(map).build();
        return null;
    }
}
