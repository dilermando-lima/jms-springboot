package com.example.jmsspringboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "queue.config")
public class ConfigProperties {

    public static final String CONN_SQS_AMAZON_QUALIFIER = "SQS_AMAZON";
    public static final String CONN_RABBIT_QUALIFIER = "RABBIT";
    public static final String PROPERTIE_QUEUE_CONFIG_QUALIFIER_CONN = "queue.config.qualifier-conn";
    
    private Boolean listenerEnabled;
    private String qualifierConn;

    @Override
    public String toString() {
        return "ConfigProperties [" + (listenerEnabled != null ? "listenerEnabled=" + listenerEnabled + ", " : "")
                + (qualifierConn != null ? "qualifierConn=" + qualifierConn : "") + "]";
    }
    public Boolean getListenerEnabled() {
        return listenerEnabled;
    }
    public void setListenerEnabled(Boolean listenerEnabled) {
        this.listenerEnabled = listenerEnabled;
    }
    public String getQualifierConn() {
        return qualifierConn;
    }
    public void setQualifierConn(String qualifierConn) {
        this.qualifierConn = qualifierConn;
    }


}
