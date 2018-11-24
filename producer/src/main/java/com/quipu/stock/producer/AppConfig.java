package com.quipu.stock.producer;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

    @Value("${jms.destination.direct.name}")
    private String directDestinationName;

    @Value("${jms.destination.topic.name}")
    private String topicDestinationName;

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    @Profile("directDestinationEnabled")
    StockDirectProducer stockDirectProducer(JmsTemplate jmsTemplate) {
        return new StockDirectProducer(jmsTemplate, directDestination());
    }

    @Bean
    @Profile("directDestinationEnabled")
    Destination directDestination() {
        return new ActiveMQQueue(directDestinationName);
    }

    @Bean
    @Profile("topicDestinationEnabled")
    StockTopicProducer stockTopicProducer(JmsTemplate jmsTemplate) {
        return new StockTopicProducer(jmsTemplate, topicDestination());
    }

    @Bean
    @Profile("topicDestinationEnabled")
    Destination topicDestination() {
        return new ActiveMQTopic(topicDestinationName);
    }
}
