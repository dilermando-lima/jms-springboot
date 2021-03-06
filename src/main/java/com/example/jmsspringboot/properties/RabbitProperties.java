package com.example.jmsspringboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "queue.rabbit")
public class RabbitProperties {

    private String user;
    private String pass;
    private String virtualhost;
    private String host;
    private Integer port;

    @Override
    public String toString() {
        return "RabbitProperties [" + (host != null ? "host=" + host + ", " : "")
                + (pass != null ? "pass=" + pass + ", " : "") + (port != null ? "port=" + port + ", " : "")
                + (user != null ? "user=" + user + ", " : "")
                + (virtualhost != null ? "virtualhost=" + virtualhost : "") + "]";
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getVirtualhost() {
        return virtualhost;
    }
    public void setVirtualhost(String virtualhost) {
        this.virtualhost = virtualhost;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }

}

