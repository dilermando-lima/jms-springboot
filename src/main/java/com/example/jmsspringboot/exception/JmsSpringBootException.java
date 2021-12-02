package com.example.jmsspringboot.exception;

public class JmsSpringBootException extends RuntimeException {

    public JmsSpringBootException(String message, Object... replacement) {
        super(replacement!= null ? String.format(message, replacement ) : message );
    }
    public JmsSpringBootException(Throwable throwable) {
        super(throwable);
    }

    public JmsSpringBootException(Throwable throwable, String message, Object... replacement) {
        super(replacement!= null ? String.format(message, replacement ) : message ,throwable);
    }

}
