package com.company.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingCopnfig {

    public static final String QUEUEUSER1 = "user1";
    public static final String QUEUEERROR = "error";
    public static final String EXCHANGEUSER1 = "exchangeUser1";
    public static final String EXCHANGEERROR = "exchangeError";
    public static final String ROUTINGKEYUSER1 = "routingKey";
    public static final String ROUTINGKEYERROR = "routingKeyError";

    @Bean
    public Queue queueUser1() {
        return QueueBuilder.durable(QUEUEUSER1).build();
    }

    @Bean
    public Queue queueError() {
        return QueueBuilder.durable(QUEUEERROR).build();
    }

    @Bean
    public Exchange exchangeUser1() {
        return ExchangeBuilder.directExchange(EXCHANGEUSER1).build();
    }

    @Bean
    public Exchange exchangeError() {
        return ExchangeBuilder.directExchange(EXCHANGEERROR).build();
    }


    @Bean
    public Binding bindExchangeUser1(Queue queueUser1, DirectExchange exchangeUser1) {
        return BindingBuilder.bind(queueUser1).to(exchangeUser1).with(ROUTINGKEYUSER1);
    }

    @Bean
    public Binding bindExchangeError(Queue queueError, DirectExchange exchangeError) {
        return BindingBuilder.bind(queueError).to(exchangeError).with(ROUTINGKEYERROR);
    }

}
