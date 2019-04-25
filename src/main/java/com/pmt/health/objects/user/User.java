package com.pmt.health.objects.user;

import com.pmt.health.exceptions.VibrentJSONException;

import java.io.IOException;
import java.util.*;


public class User {

    public static final String API_DONTKNOW = "PMI_DontKnow";
    public static final String DONTKNOW = "don't know";
    public static final String NOTSURE = "Not sure";
    public static final String API_PREFERNOANSWER = "PMI_PreferNotToAnswer";
    public static final String PREFERNOANSWER = "Prefer not to answer";
    public static final int YEARS_OLD = 20;

    protected int userId = 0;
    protected String verify;
    protected String email;
    protected String password; // NOSONAR
    protected String firstName;
    protected String middleInitial;
    protected String lastName;
    protected String phoneNumber;
    protected Date dob;
    protected List<Role> roles;

    private String login = null;
    private SignUpType signUpType = SignUpType.EMAIL;
    private String xauthToken;

    public User() throws IOException, VibrentJSONException {
        firstName = "Automation";
        middleInitial = "Q";
        lastName = "user";
        email = "matthew.grasberger+pmt@coveros.com";
        setLogin(email);
        password = "Password123!"; // NOSONAR
        verify = password;
        dob = getDefaultDOB();
        roles = new ArrayList<>(Collections.singletonList(Role.USER));
    }

    public User copy() throws IOException, VibrentJSONException {
        User newUser = new User();

        newUser.setLogin(login);
        newUser.setSignUpType(signUpType);
        newUser.setSESSIONToken(xauthToken);
        newUser.setUserId(userId);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setMiddleInitial(middleInitial);
        newUser.setLastName(lastName);
        newUser.setDOB(dob);
        newUser.roles = roles;

        return newUser;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDOB() {
        return this.dob;
    }

    public void setDOB(Date dob) {
        this.dob = dob;
    }

    public Date getDOB(int yearsOld) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.YEAR, -1 * yearsOld);
        return calendar.getTime();
    }

    public Date getDefaultDOB() {
        return getDOB(YEARS_OLD);
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

    public String getMiddleInitial() {
        return this.middleInitial;
    }

    public void setMiddleInitial(String middleinitial) {
        this.middleInitial = middleinitial;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.verify = password;
    }


    public String getLogin() {
        if (signUpType == SignUpType.EMAIL) {
            return email;
        }
        return phoneNumber;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public SignUpType getSignUpType() {
        return signUpType;
    }

    public void setSignUpType(SignUpType signUpType) {
        this.signUpType = signUpType;
    }

    public enum SignUpType {
        EMAIL, PHONE, NONE, BOTHPHONEANDEMAIL, EMAILMISMATCH
    }

    public String getSESSIONToken() {
        return xauthToken;
    }

    public void setSESSIONToken(String authToken) {
        this.xauthToken = authToken;
    }

    public enum Role {USER, ADMIN, SUPPORT, PROVIDER, DEVELOPER, T2_SUPPORT, MC_SUPPORT_ADMIN, MC_SUPPORT_STAFF}
}
