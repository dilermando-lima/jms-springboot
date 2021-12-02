package com.example.jmsspringboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jms-springboot")
public class QueueProperties {

        // TODO: Check with Enum later
        private String qualifierQueueName;

        public String getQualifierQueueName() {
            return qualifierQueueName;
        }

        public void setQualifierQueueName(String qualifierQueueName) {
            this.qualifierQueueName = qualifierQueueName;
        }
    
}
