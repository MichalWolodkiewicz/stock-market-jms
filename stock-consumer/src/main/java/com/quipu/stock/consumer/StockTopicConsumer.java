package com.quipu.stock.consumer;

import com.quipu.stock.dto.StockPriceDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;

@Profile("topicDestinationEnabled")
public class StockTopicConsumer {

    @JmsListener(destination = "${jms.destination.topic.name}", containerFactory = "topicJmsConnectionFactory")
    void consume(StockPriceDTO stockPriceDTO) {
        System.out.println("topic consumer: stock price consumed " + stockPriceDTO.toString());
    }

}
