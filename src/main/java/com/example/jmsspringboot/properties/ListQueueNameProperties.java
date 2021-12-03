package com.example.jmsspringboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "queue.list-queue-name")
public class ListQueueNameProperties{

    public static final String KEY_ATTR_QUEUE_ANY_1 = "${queue.list-queue-name.queue-any-1}";
    public static final String KEY_ATTR_QUEUE_ANY_2 = "${queue.list-queue-name.queue-any-2}";
    
    private String queueAny1;
    private String queueAny2;

    @Override
    public String toString() {
        return "ListQueueNameProperties [" + (queueAny1 != null ? "queueAny1=" + queueAny1 + ", " : "")
                + (queueAny2 != null ? "queueAny2=" + queueAny2 : "") + "]";
    }
    public String getQueueAny1() {
        return queueAny1;
    }
    public void setQueueAny1(String queueAny1) {
        this.queueAny1 = queueAny1;
    }
    public String getQueueAny2() {
        return queueAny2;
    }
    public void setQueueAny2(String queueAny2) {
        this.queueAny2 = queueAny2;
    }


}
