package com.pmt.health.workflows;

import org.openqa.selenium.Keys;
import org.testng.Assert;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;

public class CapacityManagementPage {
    private final App app;
    private User user;
    private final WebbElement editButton;
    private final WebbElement saveButton;
    private final WebbElement totalDailyTargetInput;
    private final WebbElement totalDailyGoalInput;
    private final WebbElement dailyTargetInput;
    private final WebbElement dailyGoalInput;
    private final WebbElement editSuccessMessage;
    private final WebbElement awardeeName;
    private final WebbElement orgName;
    private final WebbElement selectedOrgName;
    private final WebbElement siteName;
    private final WebbElement totalDailyTargetSpan;
    private final WebbElement totalDailyGoalSpan;
    private final WebbElement selectArrow;
    private final WebbElement dailyTargetSpan;
    private final WebbElement dailyGoalSpan;

    public CapacityManagementPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.editButton = app.newElement(LocatorType.CSS, "button[class*=goal-edit-button]");
        this.saveButton = app.newElement(LocatorType.CSS, "button[class*=goal-save-button]");
        this.totalDailyTargetInput = app.newElement(LocatorType.CSS, "div.text-truncate #target");
        this.totalDailyGoalInput = app.newElement(LocatorType.CSS, "div.text-truncate #goal");
        this.dailyTargetInput = app.newElement(LocatorType.CSS, "div.cell-container #target");
        this.dailyGoalInput = app.newElement(LocatorType.CSS, "div.cell-container #goal");
        this.editSuccessMessage = app.newElement(LocatorType.CSS, "div[class=\"message animated fade success in\"]");
        this.awardeeName = app.newElement(LocatorType.CSS, "div.org-name a");
        this.orgName = app.newElement(LocatorType.CSS, "div.cell-container div");
        this.selectedOrgName = app.newElement(LocatorType.CSS, "div.org-name a.selected");
        this.siteName = app.newElement(LocatorType.CSS, "div.cell-container div");
        this.totalDailyTargetSpan = app.newElement(LocatorType.XPATH, "(//div[@class='text-truncate']//span)[1]");
        this.totalDailyGoalSpan = app.newElement(LocatorType.XPATH, "(//div[@class='text-truncate']//span)[2]");
        this.selectArrow = app.newElement(LocatorType.CSS, "span.Select-arrow");
        this.dailyTargetSpan = app.newElement(LocatorType.XPATH, "(//div[@class='cell-container']//span)[1]");
        this.dailyGoalSpan = app.newElement(LocatorType.XPATH, "(//div[@class='cell-container']//span)[2]");
    }

    public void clickSelectArrow() {
        selectArrow.waitFor().displayed();
        selectArrow.click();
    }

    public void clickSelectAwardee(String awardeeName) {
        WebbElement selectAwardee = app.newElement(LocatorType.CSS, "div.Select-option");
        selectAwardee.waitFor().displayed();
        selectAwardee.assertEquals().text(awardeeName);
        selectAwardee.click();
    }

    public void assertDisplayAwardeeName(String expectedAwardee) {
        awardeeName.assertState().displayed();
        Assert.assertEquals(awardeeName.get().text(), expectedAwardee);
    }

    public void assertDisplayOrgName(String expectedOrg) {
        orgName.assertState().displayed();
        Assert.assertEquals(orgName.get().text(), expectedOrg);
    }

    public void clickOrgName() {
        orgName.click();
    }

    public void assertDisplaySelectedOrgName(String expectedOrg) {
        selectedOrgName.assertState().displayed();
        Assert.assertEquals(selectedOrgName.get().text(), expectedOrg);
    }

    public void assertDisplaySiteName(String expectedSite) {
        siteName.assertState().displayed();
        Assert.assertEquals(siteName.get().text(), expectedSite);
    }

    public void enterTotalDailyTarget(String totalDailyTargetValue) {
        totalDailyTargetInput.waitFor().displayed();
        totalDailyTargetInput.doubleClick();
        totalDailyTargetInput.type(Keys.BACK_SPACE);
        totalDailyTargetInput.type(totalDailyTargetValue);
    }

    public void enterTotalDailyGoal(String totalDailyGoalValue) {
        totalDailyGoalInput.waitFor().displayed();
        totalDailyGoalInput.doubleClick();
        totalDailyGoalInput.type(Keys.BACK_SPACE);
        totalDailyGoalInput.type(totalDailyGoalValue);
    }

    public void clickEditButton() {
        editButton.waitFor().displayed();
        editButton.click();
    }

    public void clickSaveButton() {
        saveButton.waitFor().displayed();
        saveButton.click();
    }

    public void assertEditSuccessMessage(String expectedMessage) {
        editSuccessMessage.assertState().displayed();
        editSuccessMessage.assertContains().text(expectedMessage);
    }

    public void assertTotalDailyTargetSpan(String expectedValue) {
        totalDailyTargetSpan.waitFor().displayed();
        Assert.assertEquals(totalDailyTargetSpan.get().text(), expectedValue);
    }

    public void assertTotalDailyGoalSpan(String expectedValue) {
        totalDailyGoalSpan.waitFor().displayed();
        Assert.assertEquals(totalDailyGoalSpan.get().text(), expectedValue);
    }

    public void enterDailyTarget(String dailyTargetValue) {
        dailyTargetInput.waitFor().displayed();
        dailyTargetInput.doubleClick();
        dailyTargetInput.type(Keys.BACK_SPACE);
        dailyTargetInput.type(dailyTargetValue);
    }

    public void enterDailyGoal(String dailyGoalValue) {
        dailyGoalInput.waitFor().displayed();
        dailyGoalInput.doubleClick();
        dailyGoalInput.type(Keys.BACK_SPACE);
        dailyGoalInput.type(dailyGoalValue);
    }

    public void assertDailyTargetSpan(String expectedValue) {
        dailyTargetSpan.waitFor().displayed();
        Assert.assertEquals(dailyTargetSpan.get().text(), expectedValue);
    }

    public void assertDailyGoalSpan(String expectedValue) {
        dailyGoalSpan.waitFor().displayed();
        Assert.assertEquals(dailyGoalSpan.get().text(), expectedValue);
    }
}
