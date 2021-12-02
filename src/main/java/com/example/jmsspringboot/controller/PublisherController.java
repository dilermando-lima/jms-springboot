package com.example.jmsspringboot.controller;

import java.util.Map;

import com.example.jmsspringboot.service.MessagePublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/publish")
public class PublisherController {

    static final Logger LOGGER = LoggerFactory.getLogger(PublisherController.class);

    @Autowired
    private MessagePublisher messagePublisher;
    
    @PostMapping("/{queue-name}")
    public ResponseEntity<Void> publishMessage(
                                    @PathVariable("queue-name") String queueName, 
                                    @RequestParam  Map<String, Object> headers,
                                    @RequestBody String message ){
        
        LOGGER.info("Calling publishMessage() : queueName = {}", queueName);
        
        messagePublisher.publish(queueName, message, headers);
        return ResponseEntity.noContent().build();
    }
    
}
