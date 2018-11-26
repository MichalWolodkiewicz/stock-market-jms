package com.quipu.stock.consumer;

import com.quipu.stock.dto.StockPriceDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;

@Profile("directDestinationEnabled")
public class StockDirectConsumer {

    @JmsListener(destination = "${jms.destination.direct.name}", containerFactory = "directJmsConnectionFactory", selector = "${jms.message.selector}")
    void consume(StockPriceDTO stockPriceDTO) {
        System.out.println("direct consumer: stock price consumed " + stockPriceDTO.toString());
    }
}
