package com.example.jmsspringboot.config;

import javax.jms.ConnectionFactory;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.example.jmsspringboot.properties.ConfigProperties;
import com.example.jmsspringboot.properties.RabbitProperties;
import com.example.jmsspringboot.properties.SqsAmazonProperties;
import com.rabbitmq.jms.admin.RMQConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class ConnectionQueueConfig {


    @ConditionalOnProperty(havingValue = ConfigProperties.CONN_SQS_AMAZON_QUALIFIER , name = ConfigProperties.PROPERTIE_QUEUE_CONFIG_QUALIFIER_CONN)
    @Bean
    public ConnectionFactory jmsConnectionSQSAmazonFactory(@Autowired SqsAmazonProperties sqsAmazonProperties) {
  
        AmazonSQSClientBuilder sqsClientBuilder =  AmazonSQSClientBuilder.standard();
        sqsClientBuilder.withCredentials(new EnvironmentVariableCredentialsProvider());
        sqsClientBuilder.setEndpointConfiguration(new EndpointConfiguration(sqsAmazonProperties.getEndpoint(), sqsAmazonProperties.getRegion()));
  
        return new SQSConnectionFactory(
                    new ProviderConfiguration(),
                    sqsClientBuilder.build()
                );
    }

    
    @ConditionalOnProperty(havingValue = ConfigProperties.CONN_RABBIT_QUALIFIER , name = ConfigProperties.PROPERTIE_QUEUE_CONFIG_QUALIFIER_CONN)
    @Bean
    public ConnectionFactory jmsConnectionRabbitFactory(@Autowired RabbitProperties rabbitProperties) {
        RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
        connectionFactory.setUsername(rabbitProperties.getUser());
        connectionFactory.setPassword(rabbitProperties.getPass());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualhost());
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        
        return connectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory( @Autowired ConnectionFactory connectionFactory, @Autowired ConfigProperties configProperties) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAutoStartup(configProperties.getListenerEnabled());
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate(@Autowired ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

}
