package com.example.jmsspringboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {


    static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void publish(String queueName, String message) {
        LOGGER.debug("Calling publish() : queueName = {} , message = {}", queueName , message );
        jmsTemplate.convertAndSend(queueName, message);
    }

}
