package ru.on8off.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.on8off.rabbitmq.service.ProducerService;

@RestController
public class ProducerController {
    @Autowired
    private ProducerService producerService;

    @GetMapping("/generate")
    public String generateNewEvents(@RequestParam(required = false) Integer count, @RequestParam(required = false) Integer pause) throws InterruptedException {
        producerService.generateAndProduce(count, pause);
        return "OK";
    }
}
