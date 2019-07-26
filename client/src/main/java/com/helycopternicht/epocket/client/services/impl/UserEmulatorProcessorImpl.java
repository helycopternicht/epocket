package com.helycopternicht.epocket.client.services.impl;

import com.helycopternicht.epocket.client.configuration.ClientSettingsPropertyHolder;
import com.helycopternicht.epocket.client.services.RoundCreationService;
import com.helycopternicht.epocket.client.services.UserEmulatorProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.helycopternicht.epocket.client.tasks.QueryCounter.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEmulatorProcessorImpl implements UserEmulatorProcessor {

    private final ClientSettingsPropertyHolder propertyHolder;
    private final RoundCreationService roundCreationService;

    @Override
    public void process() {
        List<UserEmulator> emulators = createUserEmulators();
        log.info("Test started");
        begin();
        List<Future<Boolean>> futures = startUserEmulators(emulators);
        waitingForComplete(futures);
        finish();
        log.info("Test completed. QUERY PROCESSED: {} at {} seconds. Query per second: {}", countProcessed(), elapsedTime(), countPerSecond());
    }

    private List<Future<Boolean>> startUserEmulators(List<UserEmulator> emulators) {
        List<Future<Boolean>> futures = new ArrayList<>();
        emulators.forEach(userEmulator -> futures.addAll(userEmulator.start()));
        return futures;
    }

    private List<UserEmulator> createUserEmulators() {
        List<UserEmulator> emulators = new ArrayList<>();
        int usersCount = propertyHolder.getNumberOfUsers();
        while (usersCount > 0) {
            emulators.add(createEmulator((long)usersCount));
            usersCount--;
        }
        return emulators;
    }

    private void waitingForComplete(List<Future<Boolean>> futures) {
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private UserEmulator createEmulator(long userId) {
        return new UserEmulator(propertyHolder.getNumberOfUserThreads(),
                propertyHolder.getNumberOfRoundsPerThread(), userId, roundCreationService);
    }
}
