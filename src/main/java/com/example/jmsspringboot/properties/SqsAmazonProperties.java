package com.example.jmsspringboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "queue.sqs")
public class SqsAmazonProperties {

    private String endpoint;
    private String region;
    
    public String getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }



}

