package com.pmt.health.objects.user;

public class Prospect {

    protected String firstName;
    protected String lastName;
    protected String dateOfBirth;
    protected String phoneNumber;
    protected String email;

    public Prospect() {
        firstName = "Automation" + UserUtility.generateUUID(9) + " API";
        lastName = "Prospect" + UserUtility.generateUUID(9) + " API";
        dateOfBirth = UserUtility.generateDateInThePast(20);
        phoneNumber = "555" + UserUtility.generateStringNumber(7);
        email =  UserUtility.makeRandomUserEmail();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
