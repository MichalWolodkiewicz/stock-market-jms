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
public class StockTopicProducer {
    
    private final JmsTemplate jmsTemplate;
    @Qualifier("topicDestination")
    private final Destination topicDestination;

    StockTopicProducer(JmsTemplate jmsTemplate, Destination topicDestination) {
        this.jmsTemplate = jmsTemplate;
        this.topicDestination = topicDestination;
    }

    @Scheduled(initialDelay = 5000L, fixedDelayString = "${producer.interval}")
    void produceToTopic() {
        jmsTemplate.setPubSubDomain(true);
        int price = new Random().nextInt(100);
        StockPriceDTO stockPriceDTO = new StockPriceDTO(UUID.randomUUID().toString(), "google", String.valueOf(price));
        jmsTemplate.convertAndSend(topicDestination, stockPriceDTO);
        System.out.println("Stock price produced to topic queue");
    }
}
