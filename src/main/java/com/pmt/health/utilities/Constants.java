package com.pmt.health.utilities;

public class Constants {

    public static final String NOT_IMPLEMENTED = "Not Implemented";
    @SuppressWarnings("squid:S2068") // This is a dummy password
    public static final String WRONGPASSWORD = "wrongpassword";
    public static final String NEVER_CALLED = "This method is never called.";
    public static final String METHOD_DELETED = "This method is deleted.";
    public static final String SHOULD_FAIL = "We should have failed: ";
    public static final String THERE_ARE = "There are ";
    public static final String PROGRAM_MANAGER_ROLE = "Program Manager";
    public static final String SITE_MANAGER_ROLE = "Site Manager";
    public static final String SUPPORT_ADMIN_ROLE = "Support Admin";
    public static final String RESEARCH_ASSISTANT_ROLE = "Research Assistant";
    public static final String EMAIL_RESET_PASSWORD = "reset your password";    //NOSONAR
    public static final String AUTOMATION_AWARDEE = "Awardee/TEST_AUTOMATION";
    public static final String AUTOMATION_SITE = "Site/hpo-test-automation";
    public static final String AUTOMATION_ORG = "Organization/TEST_AUTOMATION_ORGANIZATION";

    private Constants() {
        throw new UnsupportedOperationException("Don't call new on Constants");
    }
}
