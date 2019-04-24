package com.pmt.health.exceptions;

public class AppNotFoundException extends RuntimeException {

    public AppNotFoundException() {
        super();
    }

    public AppNotFoundException(String msg) {
        super(msg);
    }
}
