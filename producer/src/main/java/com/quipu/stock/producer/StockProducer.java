package com.quipu.stock.producer;

import com.quipu.stock.dto.StockPriceDTO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.Destination;
import java.util.Random;

public class StockProducer {

    private final JmsTemplate jmsTemplate;
    private final Destination destination;

    public StockProducer(JmsTemplate jmsTemplate, Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    @Scheduled(initialDelay = 5000L, fixedDelay = 5000L)
    void produce() {
        int price = new Random().nextInt(100);
        StockPriceDTO stockPriceDTO = new StockPriceDTO("google", String.valueOf(price));
        jmsTemplate.convertAndSend(destination, stockPriceDTO);
        System.out.println("Stock price produced");
    }
}
