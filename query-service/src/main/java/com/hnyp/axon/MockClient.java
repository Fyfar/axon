package com.hnyp.axon;

import com.hnyp.axon.connection.service.entity.ConnectionState;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class MockClient {
    private static final List<ConnectionState> STATUSES = Collections.unmodifiableList(Arrays.asList(ConnectionState
            .values()));
    private Random rnd = new Random();

    public ConnectionState getStatus() {
        int index = rnd.nextInt(STATUSES.size());

        return STATUSES.get(index);
    }

}
