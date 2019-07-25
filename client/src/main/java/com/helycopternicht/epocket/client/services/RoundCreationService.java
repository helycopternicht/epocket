package com.helycopternicht.epocket.client.services;

import com.helycopternicht.epocket.client.tasks.Round;
import lombok.NonNull;

public interface RoundCreationService {

    Round getRound(@NonNull Long userId) throws IllegalAccessException, InstantiationException;
}
