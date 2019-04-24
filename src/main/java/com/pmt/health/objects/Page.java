package com.pmt.health.objects;

import com.pmt.health.interactions.application.App;
import com.pmt.health.workflows.inputs.Input;

import java.util.*;

public class Page {

    private final App app;
    private final int pageNumber;
    private final String pageTitle;
    private final List<Input> inputs = new ArrayList<>();

    public Page(App app, int pageNumber, String pageTitle, List<Input> inputs) {
        this.app = app;
        this.pageNumber = pageNumber;
        this.pageTitle = pageTitle;
        this.inputs.addAll(inputs);
    }

    public Page(App app, int pageNumber, String pageTitle, Input... inputs) {
        this.app = app;
        this.pageNumber = pageNumber;
        this.pageTitle = pageTitle;
        Collections.addAll(this.inputs, inputs);
    }

    /**
     * Constructor for a page with no input options, like an info page
     *
     * @param pageNumber number of the page
     * @param pageTitle  title of the page
     */
    public Page(App app, int pageNumber, String pageTitle) {
        this.app = app;
        this.pageNumber = pageNumber;
        this.pageTitle = pageTitle;
    }

    /**
     * Set whether this page object should have a Next control on it
     *
     * @param hasNext boolean true if this page should have a Next object
     * @return itself
     */
    public Page setNext(boolean hasNext) {
        return this;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    /**
     * Returns the stored values of all recorded inputs
     *
     * @return a list of all stored input values
     */
    private List<String> getStoredInputs() {
        List<String> storedInputList = new ArrayList<>();
        for (Input input : inputs) {
            storedInputList.addAll(input.getRecordedInput());
        }
        return storedInputList;
    }

    /**
     * Returns all the values that are scanned from the current page.
     *
     * @return a list of all scanned inputs currently on the page
     */
    private List<String> getScannedInputs() {
        Set<String> finalScannedInputList = new HashSet<>();
        for (Input input : inputs) {
            finalScannedInputList.addAll(input.scanInput());
        }
        List<String> scannedInputList = new ArrayList<>();
        if (finalScannedInputList.size() > 1) {
            for (String option : finalScannedInputList) {
                if (!option.isEmpty()) {
                    scannedInputList.add(option);
                }
            }
        } else {
            scannedInputList.addAll(finalScannedInputList);
        }
        return scannedInputList;
    }

    /**
     * Checks through all the page's inputs and returns if the page contains a certain type of input.
     *
     * @param inputToCheck class of the input to check for.
     * @return true if the page contains an input of the desired type, false otherwise.
     */
    public boolean isInputType(Class... inputToCheck) {
        for (Class inputType : inputToCheck) {
            for (Input input : inputs) {
                if (input.getClass().equals(inputType)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Asserts that the current page's scanned and recorded choices match, as well as page number and title.
     */
    public void validateInputs() {
        String storedPageTitle = app.getStoredPage().getPageTitle();
        String currentPageTitle = getPageTitle();
        int storedPageNumber = app.getStoredPage().getPageNumber();
        int currentPageNumber = getPageNumber();
        List<String> stored = getStoredInputs();
        List<String> scanned = getScannedInputs();
        List<String> scannedWithoutNull = new ArrayList<>();
        Collections.sort(stored);
        // Taking out null values from scanned results
        for (String scannedInput : scanned) {
            if (scannedInput != null) {
                scannedWithoutNull.add(scannedInput);
            }
        }
        // Getting unique values from the results
        List<String> finalScanned = new ArrayList<>(scannedWithoutNull);
        Collections.sort(finalScanned);
        String action = "Checking the page's stored vs scanned results";
        String expected =
                "Expected to find <b>" + stored + "</b> options selected on the page number <b>" + storedPageNumber +
                        "</b> with the page title <b>" + storedPageTitle + "</b>";
        String actual =
                "Found <b>" + finalScanned + "</b> options selected on the page number <b>" + currentPageNumber +
                        "</b> with the page title <b>" + currentPageTitle + "</b>";
        if (stored.equals(finalScanned)) {
            app.getReporter().pass(action, expected, actual);
        } else {
            app.getReporter().fail(action, expected, actual);
        }
    }

    @Override
    public String toString() {
        return "Title: " + this.pageTitle + " | Number: " + this.pageNumber;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = o instanceof Page && o.toString().equals(this.toString());
        if (o != null) {
            String action = "Checking if current page matches with stored page";
            String expected = "Expected to find " + this.toString();
            String actual = "Found " + o.toString();
            if (result) {
                app.getReporter().pass(action, expected, actual);
            } else {
                app.getReporter().fail(action, expected, actual);
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.pageTitle.hashCode() + this.pageNumber;
    }
}
