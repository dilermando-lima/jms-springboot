package com.example.jmsspringboot.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    @JmsListener(destination = "queue-1")
    public void messageConsumer(@Headers Map<String, Object> messageAttributes,  @Payload String message) {
        LOGGER.debug("Calling messageConsumer() :  messageAttributes = {} , message = {}" , messageAttributes , message );
    }
    
}
