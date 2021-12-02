package ru.on8off.rabbitmq.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.on8off.rabbitmq.model.Events;

import java.util.concurrent.LinkedBlockingDeque;

@Configuration
@EnableScheduling
public class SpringConfiguration {
    @Bean
    public LinkedBlockingDeque<Events> eventsQueue(){
        return new LinkedBlockingDeque<>();
    }
}
