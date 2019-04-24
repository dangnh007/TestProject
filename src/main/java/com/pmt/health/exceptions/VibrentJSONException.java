package com.pmt.health.exceptions;

/**
 * This exception represents a failure to get an organizational URL or file.
 * If everything goes right, this should contain the status code and error message from the server.
 */
public class VibrentJSONException extends Exception {

    private final String unexpectedJSON;
    private final int statusCode;

    /**
     * @param message        A string containing the error message
     * @param unexpectedJSON A string containing the errored json
     */
    public VibrentJSONException(String message, int statusCode, String unexpectedJSON) {
        super(message);
        this.statusCode = statusCode;
        this.unexpectedJSON = unexpectedJSON;
    }


    public String getUnexpectedJSON() {
        return unexpectedJSON;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
