package com.pmt.health.exceptions;

public class ToggleException extends RuntimeException {

    /*
    Exception to be thrown when a toggle does something unexpected, such as JSON without an expected object
    or no JSON at all
     */

    /**
     * Creates a new instance of <code>VibrentException</code> without detail
     * message.
     */
    public ToggleException() {
    }

    /**
     * Constructs an instance of <code>VibrentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ToggleException(String msg) {
        super(msg);
    }
}
