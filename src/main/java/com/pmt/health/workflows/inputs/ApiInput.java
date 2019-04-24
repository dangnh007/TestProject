package com.pmt.health.workflows.inputs;

public class ApiInput {
    private String apiLabel;
    private String apiPrefix = "";


    public ApiInput(String apiLabel) {
        this.apiLabel = apiLabel;
    }

    public ApiInput(String apiLabel, String apiPrefix) {
        this.apiLabel = apiLabel;
        this.apiPrefix = apiPrefix;
    }

    public String getApiLabel() {
        return apiLabel;
    }

    public void setApiLabel(String apiLabel) {
        this.apiLabel = apiLabel;
    }

    public String getApiPrefix() {
        return apiPrefix;
    }

    public void setApiPrefix(String apiPrefix) {
        this.apiPrefix = apiPrefix;
    }
}
