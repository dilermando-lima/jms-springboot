package com.example.jmsspringboot.config;


import javax.jms.ConnectionFactory;

import com.example.jmsspringboot.properties.RabbitProperties;
import com.rabbitmq.jms.admin.RMQConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ConnctionRabbitConfig {

    @Bean
    public ConnectionFactory jmsConnectionFactory(@Autowired RabbitProperties rabbitProperties) {
        RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
        connectionFactory.setUsername(rabbitProperties.getUser());
        connectionFactory.setPassword(rabbitProperties.getPass());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualhost());
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        return connectionFactory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate(@Autowired ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

}
