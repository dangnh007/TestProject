package com.pmt.health.objects.user;

import com.pmt.health.exceptions.VibrentJSONException;

import java.io.IOException;
import java.util.*;

public class User {

    protected String email;
    protected String password; // NOSONAR
    protected String firstName;
    protected String lastName;
    protected String secretKey;
    protected List<Role> roles;
    private String xauthToken;

    public User() throws IOException, VibrentJSONException {
        firstName = "Automation";
        lastName = "user";
        email = UserUtility.makeRandomUserEmail();
        password = "";
        secretKey = "";
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

    public String getSESSIONToken() {
        return xauthToken;
    }

    public void setSESSIONToken(String authToken) {
        this.xauthToken = authToken;
    }

    public enum Role {SITE_MANAGER, NIH}
}
