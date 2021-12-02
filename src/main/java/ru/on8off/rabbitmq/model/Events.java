package ru.on8off.rabbitmq.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Events {
    private Integer userId;
    private List<Event> events = new ArrayList<>();
}
