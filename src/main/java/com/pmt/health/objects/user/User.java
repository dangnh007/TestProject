package com.pmt.health.objects.user;

import com.pmt.health.exceptions.VibrentJSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
    protected Address address;
    protected String phoneNumber;
    protected Date dob;
    protected String verificationSmsToken = null;
    protected Relationship relationship = null;
    protected String coResidents;
    protected String socialSecurity;
    protected List<Role> roles;
    protected Contacts contacts;
    protected UserLanguage userLanguage;
    protected String vanityCode = null;
    protected String inviteCode = null;
    protected boolean typeSignature;
    protected String programDistributionId;
    // Uses questions already selected on the page, unless otherwise set.
    protected SecurityQuestion[] questions = new SecurityQuestion[10];
    protected String[] answers = new String[questions.length];
    protected SecurityQuestion supportQuestion;
    protected String supportAnswer;
    protected int supportQuestionId;
    protected String passwordResetKey = null;
    protected String reduxState = "";
    protected String cleanReduxState = "";

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
        socialSecurity = "999999999";
        dob = getDefaultDOB();
        coResidents = "2";
        roles = new ArrayList<>(Collections.singletonList(Role.USER));
        address = new Address();
        contacts = new Contacts();
        userLanguage = UserLanguage.ENGLISH;
    }

    public User copy() throws IOException, VibrentJSONException {
        User newUser = new User();

        newUser.setLogin(login);
        newUser.setSignUpType(signUpType);
        newUser.setSESSIONToken(xauthToken);
        newUser.setUserId(userId);
        newUser.setVerify(verify);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setMiddleInitial(middleInitial);
        newUser.setLastName(lastName);
        newUser.setAddress(address);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setDOB(dob);
        newUser.setVerificationSmsToken(verificationSmsToken);
        newUser.setRelationship(relationship);
        newUser.setCoResidents(coResidents);
        newUser.setSocialSecurity(socialSecurity);
        newUser.roles = roles;
        newUser.contacts = contacts;
        newUser.setUserLanguage(userLanguage);
        newUser.setVanityCode(vanityCode);
        newUser.setInviteCode(inviteCode);
        newUser.setTypeSignature(typeSignature);
        newUser.setProgramDistributionId(programDistributionId);
        for (int i = 0; i < questions.length; i++) {
            newUser.setSecurityQuestion(i + 1, (SecurityQuestion) getSecurityQuestion(i + 1));
            newUser.setSecurityAnswer(i + 1, getSecurityAnswer(i + 1));
        }
        newUser.setSupportQuestion(supportQuestion);
        newUser.setSupportAnswer(supportAnswer);
        newUser.setUserSupportQuestionId(supportQuestionId);
        newUser.setPasswordResetKey(passwordResetKey);
        newUser.setReduxState(reduxState);

        return newUser;
    }

    public String getReduxState() {
        return reduxState;
    }

    public void setReduxState(String reduxState) {
        this.reduxState = reduxState;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCoResidents() {
        return this.coResidents;
    }

    public void setCoResidents(String coResidents) {
        this.coResidents = coResidents;
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

    public UserLanguage getUserLanguage() {
        return this.userLanguage;
    }

    public void setUserLanguage(UserLanguage userLanguage) {
        this.userLanguage = userLanguage;
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

    public String getSupportAnswer() {
        return supportAnswer;
    }

    public void setSupportAnswer(String answer) {
        this.supportAnswer = answer;
    }

    public SecurityQuestion getSupportQuestion() {
        return supportQuestion;
    }

    public void setSupportQuestion(SecurityQuestion supportQuestion) {
        this.supportQuestion = supportQuestion;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phoneNumber = phonenumber;
    }

    public String getSocialSecurity() {
        return this.socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    /**
     * Return contacts object
     *
     * @return contacts object
     */
    public Contacts getContacts() {
        return this.contacts;
    }

    /**
     * Return a list of the contacts
     *
     * @return a list of the contacts
     */

    public List<User> getContactsList() {
        return Arrays.asList(this.contacts.getFirstContact(), this.contacts.getSecondContact());
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String code) {
        inviteCode = code;
    }

    public String getVanityCode() {
        return vanityCode;
    }

    public void setVanityCode(String vanity) {
        vanityCode = vanity;
    }

    public String getVerify() {
        return this.verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    /**
     * Retrieves the password reset key
     *
     * @return A String containing the password reset key.
     */
    public String getPasswordResetKey() {
        return passwordResetKey;
    }

    /**
     * Sets the password reset key to be used elsewhere
     *
     * @param key A String containing the key
     */

    public void setPasswordResetKey(String key) {
        this.passwordResetKey = key;
    }

    public String getCleanReduxState() {
        return this.cleanReduxState;
    }

    public void setCleanReduxState(String reduxState) {
        this.cleanReduxState = reduxState;
    }

    public String getProgramDistributionId() {
        return programDistributionId;
    }

    public void setProgramDistributionId(String programDistributionId) {
        this.programDistributionId = programDistributionId;
    }

    public void setUserSupportQuestionId(int supportQuestionId) {
        this.supportQuestionId = supportQuestionId;
    }

    /**
     * Set the contacts object first contact
     *
     * @param firstContact user object to set as the first contact
     */
    public void setFirstContact(User firstContact) {
        contacts.setFirstContact(firstContact);
    }

    /**
     * Set the contacts object second contact
     *
     * @param secondContact user object to set as the second contact
     */
    public void setSecondContact(User secondContact) {
        contacts.setSecondContact(secondContact);
    }

    public boolean getTypeSignature() {
        return typeSignature;
    }

    public void setTypeSignature(boolean typeSignature) {
        this.typeSignature = typeSignature;
    }

    public String getVerificationSmsToken() {
        return verificationSmsToken;
    }

    public void setVerificationSmsToken(String validSmsVerificationCode) {
        this.verificationSmsToken = validSmsVerificationCode;
    }

    /**
     * Converts the DOB to the format required for input
     */
    public String getDobInInputFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
        return dateFormat.format(getDOB());
    }

    /**
     * Sets a specific number security question
     *
     * @param number           between 1-3 inclusive, which question to set
     * @param securityQuestion what enum value of the security question
     */
    public void setSecurityQuestion(int number, SecurityQuestion securityQuestion) {
        questions[number - 1] = securityQuestion;
    }

    /**
     * Sets a specific number security answer
     *
     * @param number         between 1-3 inclusive, which answer to set
     * @param securityAnswer what value of the security question to answer
     */
    public void setSecurityAnswer(int number, String securityAnswer) {
        answers[number - 1] = securityAnswer;
    }

    /**
     * Gets a specific number security answer
     *
     * @param number between 1-3 inclusive, which answer to obtain
     * @return security answer with corresponding number
     */
    public String getSecurityAnswer(int number) {
        return answers[number - 1];
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

    @SuppressWarnings("squid:S1301")
    public void setLanguage(String language) {
        switch (language.toLowerCase()) {
            case "spanish":
                this.userLanguage = UserLanguage.SPANISH;
                break;
            default:
                this.userLanguage = UserLanguage.ENGLISH;
        }
    }

    public UserValue getSecurityQuestion(int i) {
        return questions[i - 1];
    }

    public SignUpType getSignUpType() {
        return signUpType;
    }

    public void setSignUpType(SignUpType signUpType) {
        this.signUpType = signUpType;
    }

    public String getSESSIONToken() {
        return xauthToken;
    }

    public void setSESSIONToken(String authToken) {
        this.xauthToken = authToken;
    }

    public enum SignUpType {
        EMAIL, PHONE, NONE, BOTHPHONEANDEMAIL, EMAILMISMATCH
    }

    public enum YesNo implements UserValue {
        YES("Yes", "Yes"), NO("No", "No"), UNSURE(API_DONTKNOW, NOTSURE), NOANSWER(API_PREFERNOANSWER, PREFERNOANSWER);
        private final String apiValue;
        private final String value;

        YesNo(String apiValue, String value) {
            this.apiValue = apiValue;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getAPIValue(String prefix) {
            if (apiValue == null) {
                return null;
            }
            if (apiValue.equals(DONTKNOW) || apiValue.equals(PREFERNOANSWER)) {
                return apiValue;
            } else {
                return prefix + "_" + apiValue;
            }
        }
    }

    /**
     * Enum containing all possible security questions for the user to answer.
     */
    public enum SecurityQuestion implements UserValue {
        QUESTION1("¿Cuál es el apellido de su profesor favorito?", "What is the last name of your favorite school teacher?"),
        QUESTION2("¿Cuál es el nombre de su canción favorita?", "What is the name of your favorite song?"),
        QUESTION3("¿Cuál es lugar más lejano al que ha viajado?", "What is the furthest place to which you have traveled?"),
        QUESTION4("¿Cuál es el nombre de su primera mascota?", "What was your first pet’s name?"),
        QUESTION5("¿Cuál es el nombre de su actor o actriz favorito?", "What is the name of your favorite actor or actress?"),
        QUESTION6("¿Quién es su héroe personal?", "Who is your personal hero?"),
        QUESTION7("¿Cuál es su hobby favorito?", "What is your favorite hobby?"),
        QUESTION8("¿Cuál es el nombre del pueblo donde nació?", "The city name or town name of your birth?"),
        QUESTION9("¿Cuál es su equipo de deporte favorito?", "What is your least favorite sports team?"),
        QUESTION10("¿Cuál es el empleo de su madre?", "What is your mother's occupation?"),
        QUESTION11("¿Cuál es su marca favorita de dulces?", "What is your favorite brand of candy?"),
        QUESTION12("¿Cuál es la comida que prefiere menos?", "What is your least favorite food?"),
        QUESTION13("¿Cuál es el segundo nombre de su madre/padre?", "What is your mother’s/father’s middle name?"),
        QUESTION14("¿En qué ciudad nació su mamá?", "In what city was your mother born?"),
        QUESTION15("¿Cuál fue el nombre de su primaria?", "What was the name of your elementary school?");


        private final String apiValue;
        private final String value;

        SecurityQuestion(String apiValue, String value) {
            this.apiValue = apiValue;
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public String getAPIValue(String prefix) {
            return apiValue;
        }
    }

    /**
     * The language used by the user on the settings page
     */
    public enum UserLanguage implements UserValue {
        SPANISH("", "Spanish"), ENGLISH("", "English");
        private final String apiValue;
        private final String value;

        UserLanguage(String apiValue, String value) {
            this.apiValue = apiValue;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getAPIValue(String prefix) {
            return this.apiValue;
        }
    }

    public enum Role {USER, ADMIN, SUPPORT, PROVIDER, DEVELOPER, T2_SUPPORT, MC_SUPPORT_ADMIN, MC_SUPPORT_STAFF}

    public enum EmailCopy implements UserValue {
        YES("", "email a copy to me"), NO("No", "");
        private final String apiValue;
        private final String value;

        EmailCopy(String apiValue, String value) {
            this.apiValue = apiValue;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getAPIValue(String prefix) {
            return this.apiValue;
        }
    }
}
