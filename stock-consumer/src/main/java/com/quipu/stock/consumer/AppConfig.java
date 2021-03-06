package com.quipu.stock.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.ConnectionFactory;

@Configuration
@EnableScheduling
@EnableJms
public class AppConfig {

    @Value("${jms.clientId}")
    private String clientId;

    @Value("${jms.destination.durable}")
    private boolean isDurable;

    @Bean
    @Profile("directDestinationEnabled")
    JmsListenerContainerFactory directJmsConnectionFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(jacksonJmsMessageConverter());
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    @Profile("topicDestinationEnabled")
    JmsListenerContainerFactory topicJmsConnectionFactory(SingleConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        connectionFactory.setClientId(clientId);
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(jacksonJmsMessageConverter());
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        if (isDurable) {
            factory.setSubscriptionDurable(true);
        }
        return factory;
    }

    @Bean
    @Profile("directDestinationEnabled")
    StockDirectConsumer directStockConsumer() {
        return new StockDirectConsumer();
    }

    @Bean
    @Profile("topicDestinationEnabled")
    StockTopicConsumer topicStockConsumer() {
        return new StockTopicConsumer();
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
