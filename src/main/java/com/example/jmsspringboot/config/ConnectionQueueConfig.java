package com.example.jmsspringboot.config;

import javax.jms.ConnectionFactory;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.example.jmsspringboot.properties.RabbitProperties;
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

    public static final String CONN_SQS_AMAZON_QUALIFIER = "SQS_AMAZON";
    public static final String CONN_RABBIT_QUALIFIER = "RABBIT";

    @Autowired
    RabbitProperties rabbitProperties;

    @ConditionalOnProperty(havingValue = CONN_SQS_AMAZON_QUALIFIER , name = "jms-springboot.qualifier-queue-name", matchIfMissing = false)
    @Bean(CONN_SQS_AMAZON_QUALIFIER)
    public ConnectionFactory jmsConnectionSQSAmazonFactory() {
        return new SQSConnectionFactory(
                    new ProviderConfiguration(),
                    AmazonSQSClient.builder()
                        .withCredentials(new EnvironmentVariableCredentialsProvider())
                        .withRegion(Regions.SA_EAST_1)
                        .build()
                );
    }

    
    @ConditionalOnProperty(havingValue = CONN_RABBIT_QUALIFIER , name = "jms-springboot.qualifier-queue-name", matchIfMissing = true)
    @Bean(CONN_RABBIT_QUALIFIER)
    public ConnectionFactory jmsConnectionRabbitFactory() {
        RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
        connectionFactory.setUsername(rabbitProperties.getUser());
        connectionFactory.setPassword(rabbitProperties.getPass());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualhost());
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        return connectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory( @Autowired ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAutoStartup(rabbitProperties.getListenerEnabled());
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate(@Autowired ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

}
