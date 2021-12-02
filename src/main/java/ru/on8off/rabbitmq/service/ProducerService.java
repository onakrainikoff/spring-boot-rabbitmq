package ru.on8off.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.on8off.rabbitmq.model.Event;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
public class ProducerService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${exchange}")
    private String exchange;
    private Random random = new Random();

    public void generateAndProduce(Integer count, Integer pause) throws InterruptedException {
        count = Objects.requireNonNullElse(count,1);
        pause = Objects.requireNonNullElse(count, 1000 + random.nextInt(5) * 1000);
        for (int i = 0; i < count; i++) {
            var events = generate();
            for (Event event : events) {
                rabbitTemplate.convertAndSend(exchange, null, event, m -> {
                    m.getMessageProperties().setHeader("userId", event.getUserId());
                    m.getMessageProperties().setHeader("eventId", event.getEventId());
                    return m;
                });
                log.info(">>> sleep {} ms", pause);
                Thread.sleep(pause);
            }
        }
    }

    private List<Event> generate(){
        var userId = random.nextInt(10);
        var timeStamp = System.currentTimeMillis();
        return List.of(
                new Event(1, timeStamp, userId),
                new Event(2, timeStamp + 500, userId),
                new Event(3, timeStamp + 1000, userId),
                new Event(4, timeStamp + 1500, userId),
                new Event(5, timeStamp + 2000, userId)
        );
    }
}
