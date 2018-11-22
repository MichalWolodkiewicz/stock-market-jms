package com.quipu.stock.consumer;

import com.quipu.stock.dto.StockPriceDTO;
import org.springframework.jms.annotation.JmsListener;

public class StockConsumer {

    @JmsListener(destination = "stock-price")
    void consume(StockPriceDTO stockPriceDTO) {
        System.out.println("new stock price consumed " + stockPriceDTO.toString());
    }

}
