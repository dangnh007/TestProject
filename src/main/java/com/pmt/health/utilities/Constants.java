package com.pmt.health.utilities;

public class Constants {

    public static final String NOT_IMPLEMENTED = "Not Implemented";
    @SuppressWarnings("squid:S2068") // This is a dummy password
    public static final String WRONGPASSWORD = "wrongpassword";
    public static final String NEVER_CALLED = "This method is never called.";
    public static final String METHOD_DELETED = "This method is deleted.";
    public static final String SHOULD_FAIL = "We should have failed: ";
    public static final String THERE_ARE = "There are ";

    private Constants() {
        throw new UnsupportedOperationException("Don't call new on Constants");
    }
}
