package ru.on8off.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.on8off.rabbitmq.model.Event;

@Service
@Slf4j
public class ConsumerService {
    @Autowired
    private AggregationService aggregationService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "${queue}", exclusive = "false", autoDelete="false", durable="true"),
                    exchange = @Exchange(value = "${exchange}", type = ExchangeTypes.HEADERS)
            ),
            concurrency = "1"
    )
    public void consumeEvent(@Payload Event event) {
        log.info("received event {}", event);
        aggregationService.add(event);
    }
}
