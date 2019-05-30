package com.pmt.health.objects.user;

import com.pmt.health.exceptions.VibrentJSONException;
import com.pmt.health.steps.Configuration;
import com.pmt.health.utilities.Property;

import java.io.IOException;
import java.util.*;

public class TestUser {

    protected String verify;
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;

    protected List<Role> roles;

    private String login = null;
    private String xauthToken;

    public TestUser() throws IOException, VibrentJSONException {
        firstName = "Automation";
        lastName = "User";
        email = UserUtility.makeRandomUserEmail();
        setLogin(email);
        password = Property.getProgramProperty(Configuration.getEnvironment() + ".admin.pass");
        verify = password;
        roles = new ArrayList<>(Collections.singletonList(Role.USER));
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
        this.verify = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSESSIONToken() {
        return xauthToken;
    }

    public void setSESSIONToken(String authToken) {
        this.xauthToken = authToken;
    }

    public enum Role {USER, NULL}
}
