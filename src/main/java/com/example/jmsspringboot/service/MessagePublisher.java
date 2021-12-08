package com.example.jmsspringboot.service;

import java.util.Map;
import java.util.UUID;

import javax.jms.JMSException;

import com.example.jmsspringboot.exception.JmsSpringBootException;

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

    public void publish(String queueName, String message, Map<String, Object> headers) {
        LOGGER.debug("Calling publish() : queueName = {} , message = {}", queueName , message );

        jmsTemplate.convertAndSend(queueName, message , postProcessor -> {

            postProcessor.setJMSCorrelationID("DILO-" + UUID.randomUUID().toString());

            LOGGER.debug("id_message_sent = {} ", postProcessor.getJMSCorrelationID());

            headers.entrySet().stream().forEach(header ->  
                    {
                        try {
                            postProcessor.setObjectProperty(header.getKey(), header.getValue());
                        } catch (JMSException e) {
                            throw new JmsSpringBootException(e);
                        }
                    }
            );
            return postProcessor;
        });
    }

}
