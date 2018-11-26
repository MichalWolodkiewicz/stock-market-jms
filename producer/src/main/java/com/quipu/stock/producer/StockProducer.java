package com.quipu.stock.producer;

import com.quipu.stock.dto.StockPriceDTO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.Destination;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

class StockProducer {

    private final JmsTemplate jmsTemplate;
    private final boolean pubSubDomain;
    private final Destination destination;

    private final List<String> stockNames = new ArrayList<>();

    private int counter;

    StockProducer(JmsTemplate jmsTemplate, boolean pubSubDomain, Destination directDestination) {
        this.jmsTemplate = jmsTemplate;
        this.pubSubDomain = pubSubDomain;
        this.destination = directDestination;
        initStockNames();
        counter = stockNames.size();
    }

    private void initStockNames() {
        stockNames.add("google");
        stockNames.add("apple");
        stockNames.add("ibm");
        stockNames.add("commodore");
        stockNames.add("amiga");
        stockNames.add("sun");
    }

    @Scheduled(initialDelay = 5000L, fixedDelayString = "${producer.interval}")
    public void produce() {
        jmsTemplate.setPubSubDomain(pubSubDomain);
        int price = new Random().nextInt(100);
        String stockName = getStockName();
        StockPriceDTO stockPriceDTO = new StockPriceDTO(UUID.randomUUID().toString(), stockName, String.valueOf(price));
        jmsTemplate.convertAndSend(destination, stockPriceDTO, message -> {
            message.setStringProperty("stockname", stockName);
            return message;
        });
        System.out.println("Stock price produced " + stockPriceDTO.getUuid());
    }

    private String getStockName() {
        return stockNames.get((counter++) % stockNames.size());
    }
}
