package com.pmt.health.objects.user;

/**
 * Enumeration representing possible relationship values
 */
public enum Relationship implements UserValue {
    SPOUSE("SpousePartner", "spouse / partner"), FRIEND("Friend", "friend"),
    PARENT("ParentGuardian", "parent / guardian"), CHILD("Child", "child"), RELATIVE("Relative", "relative"),
    NOANSWER("", "");
    private final String apiValue;
    private final String value;

    Relationship(String apiValue, String value) {
        this.apiValue = apiValue;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getAPIValue(String prefix) {
        return apiValue;
    }
}