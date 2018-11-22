package com.quipu.stock.producer;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.Destination;

@Configuration
@EnableScheduling
@EnableJms
public class AppConfig {

    @Bean
    Destination destination() {
        return new ActiveMQQueue("stock-price");
    }

    @Bean
    StockProducer stockProducer(JmsTemplate jmsTemplate) {
        return new StockProducer(jmsTemplate, destination());
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
