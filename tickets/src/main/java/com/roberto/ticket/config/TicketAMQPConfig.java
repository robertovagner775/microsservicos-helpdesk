package com.roberto.ticket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberto.ticket.config.constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketAMQPConfig {

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(RabbitMQConstants.FANOUT_EXCHANGE_TICKET);
    }

    @Bean
    public Queue createQueue() {
        return QueueBuilder
                .durable(RabbitMQConstants.QUEUE_CREATED_TICKET)
                .build();
    }

    @Bean
    public Queue createQueueCategoryAnalysis() {
        return QueueBuilder
                .durable(RabbitMQConstants.QUEUE_CREATED_CATEGORY)
                .build();
    }

    @Bean
    public DirectExchange createDirectExchange() {
        return new DirectExchange(RabbitMQConstants.DIRECT_EXCHANGE_TICKET);
    }

    @Bean
    public Binding createBindingDirectExchange() {
        return BindingBuilder
                .bind(createQueueCategoryAnalysis())
                .to(createDirectExchange())
                .with(RabbitMQConstants.ROUTING_KEY_CATEGORY_CREATED);
    }

    @Bean
    public Binding createBindingFanoout() {
        return BindingBuilder
                .bind(createQueue())
                .to(fanoutExchange());
    }




    
}

