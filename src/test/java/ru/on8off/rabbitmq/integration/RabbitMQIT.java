package ru.on8off.rabbitmq.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.on8off.rabbitmq.model.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RabbitMQIT {
    @LocalServerPort
    private int port;
    private String host = "http://localhost:";
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private LinkedBlockingDeque<Events> eventsQueue;

    @Test
    public void rabbitMQTest() throws InterruptedException {
        var response = testRestTemplate.getForEntity(host + port + "/generate?count=3&pause=10", String.class);
        Assertions.assertEquals(200, response.getStatusCodeValue());

        List<Events> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            var e = eventsQueue.poll(3, TimeUnit.SECONDS);
            if(e != null){
                result.add(e);
            }
        }
        Assertions.assertEquals(3, result.size());
    }
}
