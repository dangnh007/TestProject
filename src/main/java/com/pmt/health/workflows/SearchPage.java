package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;

public class SearchPage {

    private final User user;
    private final WebbElement searchMenu;
    private final WebbElement emailSearchField;
    private final WebbElement searchedParticipantEmail;
    private final WebbElement searchButton;
    private final WebbElement createButtonFromSearchResult;
    private final WebbElement spinner;
    private final WebbElement viewButton;
    private final WebbElement viewAppointmentDetailsText;

    public SearchPage(App app, User user) {
        this.user = user;
        this.searchMenu = app.newElement(LocatorType.CSS, "svg[class*=components-search]");
        this.emailSearchField = app.newElement(LocatorType.CSS, "input[name=emailAddress]");
        this.searchedParticipantEmail = app.newElement(LocatorType.CSS, "div[class=participant-emailAddress] div");
        this.searchButton = app.newElement(LocatorType.CSS, "button[class*=btn-primary]");
        this.createButtonFromSearchResult = app.newElement(LocatorType.CSS, "div[class=\"react-bs-table-container\"] button[class=\"button button-primary btn btn-primary\"]");
        this.spinner = app.newElement(LocatorType.CSS, "canvas[class='spinner']");
        this.viewButton = app.newElement(LocatorType.XPATH, "//strong[text()='View']/parent::button");
        this.viewAppointmentDetailsText = app.newElement(LocatorType.XPATH, "//h5[text()='Future Appointment Details']");
    }

    /**
     * Search already created appointment
     */
    public void searchAppointment() {
        searchMenu.click();
        emailSearchField.type(user.getParticipantEmail());
        searchButton.click();
    }

    /**
     * Verify searched appointment should display in Search Result
     */
    public void assertSearchedAppointment() {
        searchedParticipantEmail.assertEquals().text(user.getParticipantEmail());
    }

    /**
     * Click Create button from search result to create new appointment
     */
    public void clickCreateButton() {
        createButtonFromSearchResult.click();
        spinner.waitFor().notPresent();
    }

    public void clickViewButton() {
        viewButton.click();
        spinner.waitFor().notPresent();
    }

    public void assertViewAppointmentDetailPage() {
        viewAppointmentDetailsText.assertState().displayed();
    }
}
