package ru.on8off.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.on8off.rabbitmq.model.Event;
import ru.on8off.rabbitmq.model.Events;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

@Service
@Slf4j
public class AggregationService {
    @Autowired
    private LinkedBlockingDeque<Events> eventsQueue;
    private ConcurrentHashMap<Integer, Events> store = new ConcurrentHashMap<>();


    public void add(final Event event){
        log.info("add event {} to store", event);
        store.compute(event.getUserId(), (key, events) -> {
            if(events == null){
                events = new Events();
                events.setUserId(event.getUserId());
            }
            events.getEvents().add(event);
            return events;
        });
    }

    @Scheduled(fixedDelayString = "${aggregationWindowMs}")
    private void aggregate() {
        for (Map.Entry<Integer, Events> entry : store.entrySet()) {
            log.info("aggregate event {} to store", entry.getValue());
            eventsQueue.offer(entry.getValue());
            store.remove(entry.getKey());
        }
    }

}
