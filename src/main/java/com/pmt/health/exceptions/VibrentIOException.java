package com.pmt.health.exceptions;

import java.io.IOException;
import com.google.gson.JsonElement;
import com.pmt.health.interactions.services.RequestData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This exception represents a failure to get an organizational URL or file.
 * If everything goes right, this should contain the status code and error message from the server.
 */
public class VibrentIOException extends IOException {

    private final String responseData;
    private final int responseCode;
    @SuppressWarnings("squid:S1165") // Sonar wants this final even though it has a setter down there.
    private String additionalData;
    private final List<String> additionalDataList = new ArrayList<>();
    @SuppressWarnings("squid:S1165") // Sonar wants this final even though it has a setter down there.
    private transient RequestData requestData = null;

    /**
     * Custom constructor
     *
     * @param message      The message from the base exception
     * @param responseCode The status code from the remote server
     * @param responseData The error message from the remote server
     */
    public VibrentIOException(String message, int responseCode, String responseData) {
        super(message);
        this.responseCode = responseCode;
        this.responseData = responseData;
    }

    /**
     * Retrieves the response data contained in this exception
     *
     * @return A string containing the text recovered from the error message
     */
    public String getResponseData() {
        return responseData;
    }

    /**
     * Retrieves the status code contained in this exception
     *
     * @return An int containing the status code from the remote server (404, 500,...)
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Provides additional log detail when a networking exception occurs.
     *
     * @return A String containing any additional data supplied by the thrower
     */
    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.getMessage()).append(" -- ");
        if (requestData != null) {
            builder.append(formatRequest(requestData));
        }
        builder.append("  Response:  [").append(responseData).append("]  ");
        if (!additionalDataList.isEmpty()) {
            builder.append("Additional data supplied by the caller [");
            for (String nextMessage : additionalDataList) {
                builder.append(" [").append(nextMessage).append("] ");
            }
            builder.append("] ");
        }
        return builder.toString();
    }

    private String formatRequest(RequestData requestData) {
        JsonElement json = requestData.getJSON();
        Map<String, String> params = requestData.getParams();
        StringBuilder builder = new StringBuilder();
        builder.append("  JSON Data: ").append(json);
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                builder.append("  param[").append(param.getKey()).append("] value [").append(param.getValue()).append("] ");
            }
        }
        return builder.toString();
    }

    /**
     * Sets the additional data if there's any available.
     *
     * @param additionalData A String containing any additional data to supply to the catcher.
     */
    public void addAdditionalData(String additionalData) {
        this.additionalDataList.add(additionalData);
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }
}
