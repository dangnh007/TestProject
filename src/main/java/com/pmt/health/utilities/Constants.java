package com.pmt.health.utilities;

public class Constants {

    public static final String NOT_IMPLEMENTED = "Not Implemented";
    @SuppressWarnings("squid:S2068") // This is a dummy password
    public static final String WRONGPASSWORD = "wrongpassword";
    public static final String NEVER_CALLED = "This method is never called.";
    public static final String METHOD_DELETED = "This method is deleted.";
    public static final String SHOULD_FAIL = "We should have failed: ";
    public static final String THERE_ARE = "There are ";
    public static final String REQUEST_FORGOT_PASSWORD_MESSAGE = "Request submitted. If the email matches an account, instructions to reset the password will be sent.";
    public static final String PROGRAM_MANAGER_ROLE = "Program Manager";
    public static final String SITE_MANAGER_ROLE = "Site Manager";
    public static final String SUPPORT_ADMIN_ROLE = "Support Admin";
    public static final String RESEARCH_ASSISTANT_ROLE = "Research Assistant";
    public static final String BANNER_HEALTH_ORG = "Organization/AZ_TUCSON_BANNER_HEALTH";
    public static final String BANNER_BAYWOOD_MEDICAL_CENTER_SITE = "Site/hpo-site-bannerbaywood";
    public static final String ACTIVE_STATUS = "Active";
    public static final String DISABLED_STATUS = "Disabled";
    public static final String ERROR_WHEN_LOGIN_BY_LOCKED_USER = "For security reasons, your account has been locked. Please contact your administrator to unlock your account.";
    public static final String LOCK_EMAIL_KEYWORD = "has been locked";
    public static final String EMAIL_RESET_PASSWORD = "reset your password";    //NOSONAR
    public static final String AUTOMATION_AWARDEE = "Awardee/TEST_AUTOMATION";
    public static final String AUTOMATION_SITE = "Site/hpo-test-automation";
    public static final String AUTOMATION_ORG = "Organization/TEST_AUTOMATION_ORGANIZATION";
    public static final String UNLOCK_EMAIL_KEYWORD = "unlock";

    private Constants() {
        throw new UnsupportedOperationException("Don't call new on Constants");
    }
}