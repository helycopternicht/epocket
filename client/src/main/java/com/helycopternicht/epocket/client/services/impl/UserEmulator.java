package com.helycopternicht.epocket.client.services.impl;

import com.helycopternicht.epocket.client.services.RoundCreationService;
import com.helycopternicht.epocket.client.tasks.Round;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserEmulator {

    private final Integer numberOfThreads;
    private final Integer numberOfRoundsPerThread;
    private final ExecutorService executorService;
    private final RoundCreationService roundsService;
    private final Long userId;

    private List<Callable<Boolean>> tasks;

    public UserEmulator(@NonNull Integer numberOfThreads,
                        @NonNull Integer numberOfRoundsPerThread,
                        @NonNull Long userId,
                        @NonNull RoundCreationService roundsService) {

        this.userId = userId;
        this.roundsService = roundsService;
        this.numberOfThreads = numberOfThreads;
        this.numberOfRoundsPerThread = numberOfRoundsPerThread;
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
        this.tasks = prepareTasks();
    }

    public List<Future<Boolean>> start() {
        List<Future<Boolean>> results = new ArrayList<>();
        tasks.forEach(task -> results.add(executorService.submit(task)));
        return results;
    }

    private List<Callable<Boolean>> prepareTasks() {
        List<Callable<Boolean>> tasks = new ArrayList<>(numberOfThreads);
        int threadsCount = numberOfThreads;
        while (threadsCount > 0) {
            tasks.add(createTask());
            threadsCount--;
        }
        return tasks;
    }

    private Callable<Boolean> createTask() {
        return () -> {
            Round round;
            int roundsCount = numberOfRoundsPerThread;
            while (roundsCount > 0) {
                round = roundsService.getRound(userId);
                round.execute();
                roundsCount--;
            }
            return true;
        };
    }

}