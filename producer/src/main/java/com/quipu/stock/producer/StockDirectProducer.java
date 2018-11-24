package com.quipu.stock.producer;

import com.quipu.stock.dto.StockPriceDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.Destination;
import java.util.Random;
import java.util.UUID;

@Profile("directDestinationEnabled")
public class StockDirectProducer {
    
    private final JmsTemplate jmsTemplate;
    @Qualifier("directDestination")
    private final Destination directDestination;

    StockDirectProducer(JmsTemplate jmsTemplate, Destination directDestination) {
        this.jmsTemplate = jmsTemplate;
        this.directDestination = directDestination;
    }

    @Scheduled(initialDelay = 5000L, fixedDelayString = "${producer.interval}")
    void produce() {
        jmsTemplate.setPubSubDomain(false);
        int price = new Random().nextInt(100);
        StockPriceDTO stockPriceDTO = new StockPriceDTO(UUID.randomUUID().toString(), "google", String.valueOf(price));
        jmsTemplate.convertAndSend(directDestination, stockPriceDTO);
        System.out.println("Stock price produced to direct queue");
    }
}
