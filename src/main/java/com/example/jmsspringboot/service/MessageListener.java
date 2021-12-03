package com.example.jmsspringboot.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import com.example.jmsspringboot.properties.ListQueueNameProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    @JmsListener(destination = ListQueueNameProperties.KEY_ATTR_QUEUE_ANY_1)
    public void messageConsumer(@Payload Message message,  @Headers MessageHeaders headers) throws JMSException {
        LOGGER.debug("Calling messageConsumer()" );

        LOGGER.debug("headers = {}",headers);
        LOGGER.debug("message = {}", ((TextMessage) message ).getText());

    }
    
}
