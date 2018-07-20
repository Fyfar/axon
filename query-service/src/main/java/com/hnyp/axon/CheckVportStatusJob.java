package com.hnyp.axon;

import com.hnyp.axon.connection.service.entity.Connection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CheckVportStatusJob {

    private ConnectionServiceClient client;
    private MockClient mockClient;

    @Scheduled(fixedRate = 30 * 1000)
    public void execute() {
        log.info("=== Job executed ===");

        List<Connection> list = client.checkVportsStatus();
        log.info("Response: {}", list);

        for (Connection connection : list) {
            connection.setState(mockClient.getStatus());
            log.info("Updating vport status...connection is: {}", connection);
            client.updateVport(connection);
        }
    }
}
