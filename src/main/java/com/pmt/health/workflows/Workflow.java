package com.pmt.health.workflows;

import com.pmt.health.exceptions.PageNotFoundException;
import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.application.selenified.WebApp;
import com.pmt.health.interactions.element.Element;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.Page;
import com.pmt.health.utilities.LocatorType;
import com.pmt.health.workflows.inputs.Input;
import com.pmt.health.workflows.inputs.web.WebQuiz;
import com.pmt.health.workflows.inputs.web.WebSignature;
import com.pmt.health.workflows.inputs.web.WebVideo;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is the replacement base for the interface issue.
 * TODO: Describe this thing.
 *
 * @author jeff
 */
public abstract class Workflow {
    public static final String TEXT_XPATH = "text()";
    /**
     * The logger
     */
    private final Logger log = Logger.getLogger(Workflow.class);
    /**
     * The App under test
     */
    protected App app = null;
    /**
     * The pages
     */
    protected List<Page> pages = new ArrayList<>();
    /**
     * Driver holding the browser or app
     */
    protected WebDriver driver;

    /**
     * The page we currently believe ourselves to be on
     */
    protected Page currentPage;

    /**
     * The index of the page we currently believe ourselves to be on
     */
    protected int currentPageIndex;

    /**
     * The submit button. Common to many workflows, this is a convenience.
     */
    protected Element submitButton;

    /**
     * The stop participating button.
     */
    protected Element stopParticipatingButton;

    /**
     * The close button. Common to many workflows, this is a convenience.
     */
    protected Element closeButton;

    /**
     * The navigate up button. Common to many workflows, this is a convenience.
     */
    protected Element navigateUp;

    /**
     * The save button. Common to many workflows, this is a convenience.
     */
    protected Element saveButton;

    /**
     * The start button. Common to many workflows, this is a convenience.
     */
    protected Element startButton;

    /**
     * The next (forward) button. Common to many workflows, this is a convenience.
     */
    protected Element nextButton;

    /**
     * The Get Started button.
     */
    protected Element getStartedButton;

    /**
     * The back button. Common to many workflows, this is a convenience.
     */
    protected Element backButton;

    /**
     * The previous (back) button. Common to many workflows, this is a convenience.
     */
    protected Element prevButton;
    protected Element doneButton;
    protected Element transcriptHeader;
    protected Element transcriptButton;

    /**
     * Adds a page to the pages
     *
     * @param page The Page to add
     */
    protected void addPage(Page page) {
        pages.add(page);
    }

    /**
     * Adds all the pages in the List
     *
     * @param pages A List of Page objects to add
     */
    protected void addAll(List<Page> pages) {
        this.pages.addAll(pages);
    }

    /**
     * Retrieve a List of Page objects
     *
     * @return
     */
    public List<Page> getPages() {
        return pages;
    }

    /**
     * Replace the pages with the pages in the List
     *
     * @param pages A List of Page objects
     */
    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    /**
     * Retrieves the App under test.
     *
     * @return
     */
    public App getApp() {
        return app;
    }

    /**
     * Captures all of the identifying tags on this page.
     * This method is useful for pages where there should be an identifier
     * but we're still looking by XPATH.
     *
     * @param step A String description of the current step under execution from gherkin code
     */
    public void captureTags(String step) {
        log.warn("Capturing tags for " + step);
        this.app.captureTags(step);
    }

    /**
     * Stores the form page for later comparison, and then closes out of the form.
     */
    public void closeAndSaveForm() {
        app.setStoredPage(this.currentPage);
        if (closeButton.is().displayed()) {
            closeButton.click();
        } else if (navigateUp.is().displayed()) {
            navigateUp.click();
        }
        // Needs to wait to see if save button will possibly pop up or not.
        app.wait(1.0);
        if (saveButton.is().present()) {
            saveButton.click();
        }
    }

    /**
     * Determines if the next button is present on the page
     *
     * @return true if continue button is found, false if submit is found.
     */
    public boolean continueButtonPresent() {
        return nextButton.is().present();
    }

    /**
     * Determines if the previous button is present on the page
     *
     * @return true if continue button is found, false if submit is found.
     */
    public boolean previousButtonPresent() {
        return prevButton.is().present();
    }

    /**
     * Clicks the save button.
     */
    public void clickSaveButton() {
        saveButton.click();
    }

    /**
     * Goes through the form, interacting with the next button. If the next button is present it is clicked,
     * otherwise, nothing happens
     */
    public void next() {
        app.setStoredPage(this.currentPage);
        if (startButton.is().displayed()) {
            startButton.click();
            startButton.waitFor().notDisplayed();
            currentPageIndex++;
        } else if (nextButton.is().displayed()) {
            nextButton.waitFor().enabled();
            nextButton.click();
            currentPageIndex++;
        } else if (getStartedButton.is().displayed()) {
            getStartedButton.waitFor().enabled();
            getStartedButton.click();
            currentPageIndex++;
        }
    }

    /**
     * Clicks the Previous (back) button
     */
    public void previous() {
        prevButton.waitFor().enabled();
        prevButton.click();
        currentPageIndex--;
    }

    public void submit() {
        submitButton.waitFor().enabled();
        submitButton.click();
        submitButton.waitFor().notDisplayed();
    }

    /**
     * Clicks on Stop Participating button
     */
    public void stopParticipating() {
        stopParticipatingButton.waitFor().enabled();
        stopParticipatingButton.click();
        stopParticipatingButton.waitFor().notDisplayed();
    }

    public void done() {
        doneButton.waitFor().enabled();
        doneButton.click();
        doneButton.waitFor().notDisplayed();
    }

    /**
     * Reads the current page the application is on and, based on the title, produces the corresponding page object.
     * TODO: Simplify this, as it's big and ugly.
     *
     * @return page the application is currently looking at
     * @throws PageNotFoundException
     */
    private Page getCurrentPage() throws PageNotFoundException {
        String foundPageB = "Found page <b>";
        String action = "Finding form page";
        String expected = "Found Page";
        //checking for the current page first
        int numPages = pages.size();
        if (currentPageIndex > numPages || currentPageIndex < 0) {
            log.error("Requested out of bounds page #" + currentPageIndex + " of " + numPages);
            throw new PageNotFoundException("Requested out of bounds page #" + currentPageIndex + " of " + numPages);
        }
        Page foundPage = pages.get(currentPageIndex);

        //Attempt to find current page right away
        if (isCurrentPage(foundPage.getPageTitle())) {
            app.getReporter().pass(action, expected, foundPageB + foundPage.toString() + "</b>");
            return foundPage;
        }
        app.getReporter().fail("Found no corresponding page with the title \"" + foundPage.getPageTitle() + "\" on page number " + currentPageIndex + ". \nPage: " + foundPage.getPageNumber());
        throw new PageNotFoundException("Found no corresponding page with the title \"" + foundPage.getPageTitle() + "\" on page number " + currentPageIndex + ". ");
    }

    private boolean isCurrentPage(String titleToCheckFor) {
        log.info("Looking for title: " + titleToCheckFor);
        return isCurrentWebPage(titleToCheckFor);
    }

    private boolean isCurrentWebPage(String titleToCheckFor) {
        String titleMatch = "//div[" + App.getSanitizedTranslate("@aria-label", titleToCheckFor, true) + "]";
        String textMatch =
                "//div[@class='ng-binding ng-scope' and contains(" + App.getSanitizedTranslate(TEXT_XPATH, titleToCheckFor, false) + ")]";
        String richTextMatch =
                "//span[contains(" + App.getSanitizedTranslate(TEXT_XPATH, titleToCheckFor, false) + ")]";
        String tabTextMatch = "//p[contains(" + App.getSanitizedTranslate(TEXT_XPATH, titleToCheckFor, false) + ")]";
        WebbElement pageTitle = ((WebApp) app).newElement(LocatorType.XPATH, titleMatch);
        WebbElement pageTitleEqualsText = ((WebApp) app).newElement(LocatorType.XPATH, textMatch);
        WebbElement richTextTitle = ((WebApp) app).newElement(LocatorType.XPATH, richTextMatch);
        WebbElement tabText = ((WebApp) app).newElement(LocatorType.XPATH, tabTextMatch);
        return pageTitle.is().present() || pageTitleEqualsText.is().present() || richTextTitle.is().present() || tabText.is().present();
    }

    /**
     * Reads the current page, and sets the current page and its index based on the page list.
     */
    protected void readCurrentPage() throws PageNotFoundException {
        currentPage = getCurrentPage();
    }

    /**
     * Generic go to form method based on a desired page title.
     * The desired page is reached, handled, then stopped on.
     *
     * @param pageTitle title of desired page
     */
    public void goToFormPage(String pageTitle) throws PageNotFoundException {
        while (!submitButton.is().present()) {
            readCurrentPage();
            handleAllPageInputs(currentPage.getInputs());
            if (currentPage.getPageTitle().contains(pageTitle)) {
                break;
            }
            next();
        }
    }

    /**
     * Generic go to form method based on a desired page number.
     * The desired page is reached, handled, then stopped on.
     * (Note, due to the branching nature of some forms, careful consideration should be made when using this method.)
     *
     * @param pageNumber number of desired page
     */
    public void goToFormPage(int pageNumber) throws PageNotFoundException {
        while (!submitButton.is().present()) {
            readCurrentPage();
            handleAllPageInputs(currentPage.getInputs());
            if (currentPage.getPageNumber() == pageNumber) {
                break;
            }
            next();
        }
    }

    /**
     * Generic go to form method based on a desired page input type, picking one randomly to stop on.
     * (Currently the method operates by giving each page it encounters a 75% chance of stopping on it. Given the size
     * of our forms, it's possible, though unlikely for this to not happen for the whole form. This could be improved).
     * The desired page is reached, handled, then stopped on.
     *
     * @param inputClass class of the desired input to stop on.
     */
    public void goToFormPage(Class inputClass) throws PageNotFoundException {
        // TODO Improve this method to ensure that a random page is always stopped on
        boolean firstPage = true;
        while (!submitButton.is().present()) {
            readCurrentPage();
            handleAllPageInputs(currentPage.getInputs());
            if (!firstPage && currentPage.isInputType(inputClass)) {
                break;
            }
            firstPage = false;
            next();
        }
    }

    /**
     * Generic go to form method based on a desired page number.
     * The desired page is reached and stopped on without being handled.
     *
     * @param pageNumber number of the desired input to stop on.
     */
    public void goToFormPageNoHandle(int pageNumber) throws PageNotFoundException {
        while (!submitButton.is().present()) {
            readCurrentPage();
            boolean isQuiz = currentPage.isInputType(WebQuiz.class);
            if (currentPage.getPageNumber() == pageNumber) {
                break;
            } else if (!isQuiz) {
                handleAllPageInputs(currentPage.getInputs());
                next();
            }
        }
    }

    /**
     * Generic go to form method based on a desired page title.
     * The desired page is reached and stopped on without being handled.
     *
     * @param title title of the desired page to stop on.
     */
    public void goToFormPageNoHandle(String title) throws PageNotFoundException {
        while (!submitButton.is().present()) {
            readCurrentPage();
            boolean isQuiz = currentPage.isInputType(WebQuiz.class);
            if (currentPage.getPageTitle().equals(title)) {
                break;
            } else if (!isQuiz) {
                handleAllPageInputs(currentPage.getInputs());
                next();
            }
        }
    }

    /**
     * Generic go to form method based on a desired page input type, picking one randomly to stop on.
     * (Currently the method operates by giving each page it encounters a 75% chance of stopping on it. Given the size
     * of our forms, it's possible, though unlikely for this to not happen for the whole form. This could be improved).
     * The desired page is reached and stopped on without being handled.
     *
     * @param inputClass the class to select for the input
     */
    public void goToFormPageNoHandle(Class inputClass) throws PageNotFoundException {
        // TODO Improve this method to ensure that a random page is always stopped on
        Random rand = new Random();
        boolean firstPage = true;
        while (!submitButton.is().present()) {
            readCurrentPage();
            boolean isQuiz = currentPage.isInputType(WebQuiz.class);
            if (!firstPage && currentPage.isInputType(inputClass) && rand.nextInt(3) != 0) {
                break;
            } else if (!isQuiz) {
                firstPage = false;
                handleAllPageInputs(currentPage.getInputs());
                next();
            }
        }
    }

    /**
     * Completes the form, without handling any video page inputs
     */
    public void completeFormNoVideo() throws PageNotFoundException {
        while (!submitButton.is().present()) {
            readCurrentPage();
            if (!currentPage.isInputType(WebVideo.class)) {
                handleAllPageInputs(currentPage.getInputs());
            }
            next();
        }
    }

    /**
     * Generic go to form method that handles and then stops on a random page in the form.
     * (Currently the method operates by giving each page it encounters a 25% chance of stopping on it. Given the size
     * of our forms, it's possible, though unlikely for this to not happen for the whole form. This could be improved).
     */
    public void goToRandomFormPage() throws PageNotFoundException {
        // TODO Improve this method to ensure that a random page is always stopped on
        Random rand = new Random();
        boolean firstPage = true;
        while (!isLastPageSubmitPresent()) {
            readCurrentPage();
            handleAllPageInputs(currentPage.getInputs());
            if (!firstPage && prevButton.is().displayed() && rand.nextInt(3) == 0 && !submitButton.is().displayed()) {
                break;
            }
            if (prevButton.is().displayed() && nextButton.is().displayed()) {
                firstPage = false;
            }
            next();
        }

    }

    /**
     * Walks to a random form page and clicks the previous button if it exists.
     */
    public void gotoFormPageAndPossiblyClickPrevious() throws PageNotFoundException {
        goToRandomFormPage();
        previous();
    }

    /**
     * Walks to a random form page and closes and saves. Has nothing to do with logout
     * and I don't know why it's named like it is. TODO: Rename this.
     */
    public void gotoFormPagePossiblyLogout() throws PageNotFoundException {
        goToRandomFormPage();
        app.setStoredPage(this.currentPage);
        closeAndSaveForm();
    }

    public void closeSaveOnePageForm() throws PageNotFoundException {
        readCurrentPage();
        handleAllPageInputs(currentPage.getInputs());
        app.setStoredPage(this.currentPage);
        closeAndSaveForm();
    }

    /**
     * Fills out the entire form and submits it using the set user values
     */
    public void completeForm() throws PageNotFoundException {
        closeButton.waitFor().displayed();

        do {
            readCurrentPage();
            handleAllPageInputs(currentPage.getInputs());
            next();
        } while (!isLastPageSubmitPresent());
        readCurrentPage();
    }

    /**
     * Method used to continue filling out and submit the form after partial completion, it is using the set user values
     *
     * @param header java.lang.String containing the page title from which to continue the workflow
     * @throws PageNotFoundException if the continuation page title can't be located or if the Page can not be read.
     */
    public void continueAndCompleteTheSurvey(String header) throws PageNotFoundException {
        for (currentPageIndex = 0; currentPageIndex < pages.size(); currentPageIndex++) {
            if (pages.get(currentPageIndex).getPageTitle().contains(header)) {
                break;
            }
        }
        closeButton.waitFor().displayed();
        next();
        do {
            readCurrentPage();
            handleAllPageInputs(currentPage.getInputs());
            next();
        } while (!isLastPageSubmitPresent());
        readCurrentPage();
    }

    /**
     * Method used to continue filling out the Comparative form after partial completion, it is using the set user values
     *
     * @param header java.lang.String containing the page title from which to continue the workflow
     * @throws PageNotFoundException if the continuation page title can't be located or if the Page can not be read.
     */
    public void continueAndCompleteTheComparativeSurvey(String header) throws PageNotFoundException {
        for (currentPageIndex = 0; currentPageIndex < pages.size(); currentPageIndex++) {
            if (pages.get(currentPageIndex).getPageTitle().contains(header)) {
                break;
            }
        }
        navigateUp.waitFor().displayed();
        next();
        do {
            readCurrentPage();
            handleAllPageInputs(currentPage.getInputs());
            next();
        } while (!isLastPageSubmitPresent());
        readCurrentPage();
    }

    public void completeOnePageForm() throws PageNotFoundException {
        closeButton.waitFor().displayed();
        readCurrentPage();
        handleAllPageInputs(currentPage.getInputs());
    }

    /**
     * General handle method that pulls info from the user object to complete all inputs on the current page.
     *
     * @param inputs list of inputs on the current page.
     */
    protected void handleAllPageInputs(List<Input> inputs) {
        for (Input input : inputs) {
            input.handleInput();
        }
    }

    /**
     * Obtains the correct video class to use based on the currently used device.
     *
     * @return class of video of platform
     */
    public Class getPlatformVideoClass() {
        return WebVideo.class;
    }

    /**
     * Obtains the correct video class to use based on the currently used device.
     *
     * @return class of video of platform
     */
    public Class getPlatformSignatureClass() {
        return WebSignature.class;
    }

    /**
     * Fills out the entire Mood Module form and submits it using the set user values
     */
    public void completeFormMoodModule() throws PageNotFoundException {
        while (!submitButton.is().present() && !doneButton.is().present()) {
            readCurrentPage();
            handleAllPageInputs(currentPage.getInputs());
            if (!nextMoodModule()) {
                break;
            }
        }
    }

    /**
     * Goes through the form, interacting with the next button. If the next button is present it is clicked,
     * otherwise, nothing happens (Special for Mood Module)
     */
    private boolean nextMoodModule() {
        app.setStoredPage(this.currentPage);
        if (startButton.is().displayed()) {
            startButton.click();
            startButton.waitFor().notDisplayed();
            return true;
        } else if (nextButton.is().displayed() && nextButton.is().enabled()) {
            nextButton.click();
            return true;
        } else if (backButton.is().displayed() && backButton.is().enabled()) {
            backButton.click();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifies if this is the last page of the form
     *
     * @return true if any of the following button displayed
     */
    public boolean isLastPageSubmitDisplayed() {
        return submitButton.is().displayed() || stopParticipatingButton.is().displayed();
    }

    /**
     * Checks if any of possible continue buttons present
     *
     * @return true if any of the following buttons present
     */
    public boolean isContinuePresent() {
        return nextButton.is().present() || startButton.is().present() || getStartedButton.is().present();
    }

    /**
     * Verifies if the last page submit button present
     *
     * @return true if any of the following buttons is present
     */
    public boolean isLastPageSubmitPresent() {
        return submitButton.is().present() || doneButton.is().present() || stopParticipatingButton.is().present();
    }

    /**
     * Verifies if the current page is random
     *
     * @return true if all the requirements for the ranfom page are met
     */
    public boolean isRandomPage() {
        Random rand = new Random();
        return currentPageIndex != 0 && currentPageIndex != 1 && rand.nextInt(3) == 0 && prevButton.is().displayed() && !isLastPageSubmitDisplayed();
    }

    /**
     * Sets the current page index. This is useful if we need to start a workflow from a particular page from
     * the buildPagesMethod.
     *
     * @param currentPageIndex An int containing the page number (zero based) to set the current page to.
     */
    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }
}
