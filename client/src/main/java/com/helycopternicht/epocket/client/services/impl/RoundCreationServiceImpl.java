package com.helycopternicht.epocket.client.services.impl;

import com.helycopternicht.epocket.client.services.RoundCreationService;
import com.helycopternicht.epocket.client.services.WalletService;
import com.helycopternicht.epocket.client.tasks.Round;
import com.helycopternicht.epocket.client.tasks.RoundA;
import com.helycopternicht.epocket.client.tasks.RoundB;
import com.helycopternicht.epocket.client.tasks.RoundC;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RoundCreationServiceImpl implements RoundCreationService {

    private final Random random = new Random();
    private final List<Class<? extends Round>> rounds = Arrays.asList(
            RoundA.class,
            RoundB.class,
            RoundC.class
    );

    private final WalletService walletService;

    public Round getRound(@NonNull Long userId) throws IllegalAccessException, InstantiationException {
        Round instance = rounds.get(randomIndex()).newInstance();
        instance.setWalletService(walletService);
        instance.setUserId(userId);
        return instance;
    }

    private int randomIndex() {
        return random.nextInt(rounds.size());
    }

}