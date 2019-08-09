package com.pmt.health.objects.user;

public class User {

    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String secretKey;
    protected String userId;
    protected String participantEmail;
    protected String groupValue;
    protected String hoursOfoperarion;

    public User() {
        firstName = "Automation";
        lastName = "user";
        email = UserUtility.makeRandomUserEmail();
        password = "";
        secretKey = "";
        userId = "";
        participantEmail = UserUtility.makeRandomUserEmail();
        groupValue = "";
        hoursOfoperarion = "";
    }

    public String getHoursOfoperarion() {
        return hoursOfoperarion;
    }

    public void setHoursOfoperarion(String hoursOfoperarion) {
        this.hoursOfoperarion = hoursOfoperarion;
    }

    public String getGroupValue() {
        return groupValue;
    }

    public void setGroupValue(String groupValue) {
        this.groupValue = groupValue;
    }

    public String getParticipantEmail() {
        return participantEmail;
    }

    public void setParticipantEmail(String participantEmail) {
        this.participantEmail = participantEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
