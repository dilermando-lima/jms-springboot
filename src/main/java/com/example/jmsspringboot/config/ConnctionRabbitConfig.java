package com.example.jmsspringboot.config;


import javax.jms.ConnectionFactory;

import com.example.jmsspringboot.properties.RabbitProperties;
import com.rabbitmq.jms.admin.RMQConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;


@EnableJms
@Configuration
public class ConnctionRabbitConfig {

    @Autowired RabbitProperties rabbitProperties;

    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
        connectionFactory.setUsername(rabbitProperties.getUser());
        connectionFactory.setPassword(rabbitProperties.getPass());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualhost());
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        return connectionFactory;
    }


    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(@Autowired ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAutoStartup(rabbitProperties.getListenerEnabled());
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate(@Autowired ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }


    // public MessageConverter jacksonJmsMessageConverter() 
    //     MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter()
    //     converter.setTargetType(MessageType.TEXT)
    //     converter.setTypeIdPropertyName("_type")

    //     ObjectMapper objectMapper = new ObjectMapper()
    //     objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    //     objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    //     objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    //     objectMapper.registerModule(new JavaTimeModule())
    //     converter.setObjectMapper(objectMapper)

    //     return converter
    // 

        



}
