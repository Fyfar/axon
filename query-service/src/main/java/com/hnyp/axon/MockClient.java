package com.hnyp.axon;

import com.hnyp.axon.api.entity.State;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class MockClient {
    private static final List<State> STATUSES = Collections.unmodifiableList(Arrays.asList(State.values()));
    private Random rnd = new Random();

    public State getStatus() {
        int index = rnd.nextInt(STATUSES.size());

        return STATUSES.get(index);
    }

}
