package com.example.jmsspringboot.config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.example.jmsspringboot.properties.ConfigProperties;
import com.example.jmsspringboot.properties.RabbitProperties;
import com.example.jmsspringboot.properties.SqsAmazonProperties;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;

@EnableJms
@Configuration
public class ConnectionQueueConfig {

    static final Logger LOGGER = LoggerFactory.getLogger(ConnectionQueueConfig.class);

    @ConditionalOnProperty(havingValue = ConfigProperties.CONN_SQS_AMAZON_QUALIFIER , name = ConfigProperties.PROPERTIE_QUEUE_CONFIG_QUALIFIER_CONN)
    @Bean
    public ConnectionFactory jmsConnectionSQSAmazonFactory(@Autowired SqsAmazonProperties sqsAmazonProperties) {

        LOGGER.debug("ConnectionFactory () : method = jmsConnectionSQSAmazonFactory , qualifier = {} , endpoint = {} ", ConfigProperties.CONN_SQS_AMAZON_QUALIFIER , sqsAmazonProperties.getEndpoint());
  
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

        LOGGER.debug("ConnectionFactory () : method = jmsConnectionRabbitFactory , qualifier = {} , endpoint = {}:{} ", ConfigProperties.CONN_RABBIT_QUALIFIER , rabbitProperties.getHost() , rabbitProperties.getPort());
  
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

        LOGGER.debug("ConnectionFactory () : method = jmsListenerContainerFactory , listenerEnabled = {} ", configProperties.getListenerEnabled());

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        if( ConfigProperties.CONN_RABBIT_QUALIFIER.equals(configProperties.getQualifierConn())  ){
            factory.setDestinationResolver(new DestinationResolver() {
                @Override
                public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)  throws JMSException {
                    RMQDestination jmsDestination = new RMQDestination();
                    jmsDestination.setDestinationName(destinationName);
                    jmsDestination.setAmqpQueueName(destinationName);
                    jmsDestination.setAmqp(true);
                    return jmsDestination;
                }
            });
        }

        //factory.setConcurrency("3-10")
        //factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE)
        factory.setAutoStartup(configProperties.getListenerEnabled());
        return factory;
    }


    
    @Bean
    public JmsTemplate defaultJmsTemplate(@Autowired ConnectionFactory connectionFactory) {
        return  new JmsTemplate(connectionFactory);
    }



}
