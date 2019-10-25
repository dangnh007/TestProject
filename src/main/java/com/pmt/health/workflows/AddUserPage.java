package com.pmt.health.workflows;

import org.openqa.selenium.Keys;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;

public class AddUserPage {

    private final App app;
    private final WebbElement firstNameInput;
    private final WebbElement lastNameInput;
    private final WebbElement emailInput;
    private final WebbElement rolesInput;
    private final WebbElement saveButton;
    private final WebbElement successMessagePanel;
    private static final String EXPECTED_VALUE_WHEN_GROUP_IS_CHECKED = "background-color";
    private static final String STYLE_ATTRIBUTE = "style";
    private static final String VALUE_ATTRIBUTE = "value";

    public AddUserPage(App app, User user) {
        this.app = app;
        this.firstNameInput = app.newElement(LocatorType.NAME, "firstName");
        this.lastNameInput = app.newElement(LocatorType.NAME, "lastName");
        this.emailInput = app.newElement(LocatorType.NAME, "email");
        this.rolesInput = app.newElement(LocatorType.CSS, "input[role*=combobox]");
        this.saveButton = app.newElement(LocatorType.ID, "save");
        this.successMessagePanel = app.newElement(LocatorType.CSS, "div[class=\"message animated fade success in\"]");
        
    }

    /**
     * click on the checkbox with parameterised organization/awardee
     *
     * @param org passing organization value
     */
    public void checkAwardee(String org) {
        WebbElement awardee = app.newElement(LocatorType.ID, org);
        awardee.click();
    }
    
    public void assertGroupIsChecked(String org) {
    	WebbElement group = app.newElement(LocatorType.XPATH, "//span[@id=\"" + org + "\"]//parent::li");
    	group.assertContains().attribute(STYLE_ATTRIBUTE, EXPECTED_VALUE_WHEN_GROUP_IS_CHECKED);
    }
    
    public void assertGroupIsNotChecked(String org) {
        WebbElement group = app.newElement(LocatorType.XPATH, "//span[@id=\"" + org + "\"]//parent::li");
        group.assertState().displayed();
        String elementValue = group.get().attribute(STYLE_ATTRIBUTE);
        if (!elementValue.contains(EXPECTED_VALUE_WHEN_GROUP_IS_CHECKED)) {
            app.getReporter().pass(group.prettyOutputStart() + " doesn't contains attribute of <i>" + STYLE_ATTRIBUTE
                    + " with the value of <b>" + elementValue + "</b>");
        } else {
            app.getReporter().fail(group.prettyOutputStart() + " contains attribute of <i>" + STYLE_ATTRIBUTE
                    + " with the value of <b>" + elementValue + "</b>");
        }
    }

    /**
     * checks if element is present
     * and if it is, clicks on the save button
     */
    public void saveUser() {
        if (saveButton.is().present()) {
            saveButton.click();
        }
    }

    /**
     * input for the first name field
     *
     * @param firstName passing first name parameter
     */
    public void enterFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.type(firstName);
        if ("".equals(firstName)) {
            firstNameInput.type("a");
            firstNameInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * input for the last name field
     *
     * @param lastName passing last name parameter
     */
    public void enterLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.type(lastName);
        if ("".equals(lastName)) {
            lastNameInput.type("a");
            lastNameInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * input for the email field
     *
     * @param email passing email parameter
     */
    public void enterEmail(String email) {
        emailInput.type(email);
        if ("".equals(email)) {
            emailInput.type("a");
            emailInput.type(Keys.BACK_SPACE);
        }
    }

    /**
     * input for the user role field
     *
     * @param role passing user role parameter
     */
    public void selectRole(String role) {
        rolesInput.type(Keys.DELETE);
        rolesInput.type(role + Keys.ENTER);
        if ("".equals(role)) {
            rolesInput.type("a");
            rolesInput.type(Keys.BACK_SPACE);
        }
    }

    public void inputUserInfo(String firstName, String lastName, String email, String role, String org) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        selectRole(role);
        checkAwardee(org);
    }

    public WebbElement getFirstNameInput() {
        return this.firstNameInput;
    }

    public WebbElement getLastNameInput() {
        return this.lastNameInput;
    }

    public WebbElement getEmailInput() {
        return this.emailInput;
    }

    public WebbElement getRolesInput() {
        return this.rolesInput;
    }

    public String getValueFirstNameInput() {
        return this.firstNameInput.get().value();
    }

    public String getValueLastNameInput() {
        return this.lastNameInput.get().value();
    }

    public void assertTextFirstNameInput(String expectedText) {
        this.firstNameInput.assertEquals().attribute(VALUE_ATTRIBUTE, expectedText);
    }

    public void assertTextLastNameInput(String expectedText) {
        this.lastNameInput.assertEquals().attribute(VALUE_ATTRIBUTE, expectedText);
    }

    public void clearFirstNameInput() {
        this.firstNameInput.clear();
    }

    public void clearLastNameInput() {
        this.lastNameInput.clear();
    }

    public String getValueEmailInput() {
        return emailInput.get().attribute(VALUE_ATTRIBUTE);
    }

    public void assertDisplayedSuccessMessagePanel() {
        successMessagePanel.assertState().displayed();
    }

    public void assertStringSuccessMessagePanel(String message) {
        successMessagePanel.assertEquals().text(message);
    }

    public void assertRoleIsSelected(String role) {
        WebbElement roleElement = app.newElement(LocatorType.XPATH, "//span[contains(text(),'" + role + "')]");
        roleElement.assertState().displayed();
    }

    public void assertRoleIsNotSelected(String role) {
        WebbElement roleElement = app.newElement(LocatorType.XPATH, "//span[contains(text(),'" + role + "')]");
        roleElement.assertState().notDisplayed();
    }
}
