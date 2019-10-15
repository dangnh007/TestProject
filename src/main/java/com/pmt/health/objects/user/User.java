package com.pmt.health.objects.user;

public class User {

    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String secretKey;
    protected String searchedUserSecret;
    protected String userId;
    protected String participantEmail;
    protected String groupValue;
    protected String hoursOfoperarion;
    protected String authToken;
    protected String searchedUserEmail;
    protected String searchedUserPassword;

    public User() {
        firstName = "Automation";
        lastName = "user";
        email = "";
        password = "";
        secretKey = "";
        userId = "";
        participantEmail = UserUtility.makeRandomUserEmail();
        groupValue = "";
        hoursOfoperarion = "";
        authToken = "";
        searchedUserEmail = "";
        searchedUserPassword = "";
        searchedUserSecret = "";
    }

    /**
     * Getter
     */
    public String getSearchedUserSecret() {
        return searchedUserSecret;
    }

    /**
     * Setter
     */
    public void setSearchedUserSecret(String searchedUserSecret) {
        this.searchedUserSecret = searchedUserSecret;
    }

    /**
     * Getter
     */
    public String getSearchedUserPassword() {
        return searchedUserPassword;
    }

    /**
     * Setter
     */
    public void setSearchedUserPassword(String searchedUserPassword) {
        this.searchedUserPassword = searchedUserPassword;
    }

    /**
     * Getter
     */
    public String getSearchedUserEmail() {
        return searchedUserEmail;
    }

    /**
     * Setter
     */
    public void setSearchedUserEmail(String searchedUserEmail) {
        this.searchedUserEmail = searchedUserEmail;
    }

    /**
     * Getter
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Setter
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Getter
     */
    public String getHoursOfoperarion() {
        return hoursOfoperarion;
    }

    /**
     * Setter
     */
    public void setHoursOfoperarion(String hoursOfoperarion) {
        this.hoursOfoperarion = hoursOfoperarion;
    }

    /**
     * Getter
     */
    public String getGroupValue() {
        return groupValue;
    }

    /**
     * Setter
     */
    public void setGroupValue(String groupValue) {
        this.groupValue = groupValue;
    }

    /**
     * Getter
     */
    public String getParticipantEmail() {
        return participantEmail;
    }

    /**
     * Setter
     */
    public void setParticipantEmail(String participantEmail) {
        this.participantEmail = participantEmail;
    }

    /**
     * Getter
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Setter
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Getter
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter
     */
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    /**
     * Getter
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter
     */
    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    /**
     * Getter
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
