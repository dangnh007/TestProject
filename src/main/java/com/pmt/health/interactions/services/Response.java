/*
 * Copyright 2017 Coveros, Inc.
 *
 * This file is part of Selenified.
 *
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.pmt.health.interactions.services;

import com.google.gson.*;
import com.pmt.health.interactions.application.App;
import com.pmt.health.utilities.Reporter;
import org.testng.Assert;
import org.testng.log4testng.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class designed to hold data provided from the HTTP calls.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Response {

    private static final Logger log = Logger.getLogger(Response.class);
    private static final String FOUND = "Found a response of: ";
    private static final String FOUND_NULL_OBJECT = "Found null object";
    private static final String FOUND_NULL_ARRAY = "Found null array";
    private static final String EXPECTED = "Expected to find a response of: ";
    private static final String EQUAL_TO = "</i> equal to: ";
    private static final String MADE_A = "Made a <b>";
    private static final String CALL_TO = "</b> call to <b>";
    private static final String ALPHA_SPACE = "[^\\w\\s]";
    private Reporter reporter;
    private int statusCode;
    private Map<String, List<String>> headers = new HashMap<>();
    private ResponseData responseData = null;
    private Object data = null;
    private String message = null;
    private String action;
    private String call;
    private String service;
    private RequestData requestData;

    public Response() {
        // constructor needs to be empty for pico factory
        // TODO: PICOFactory doesn't need to know about Response any more.
    }

    /**
     * Asserts that the JSON array is of some size
     */
    public void assertJsonArraySizeEquals(JsonArray jsonArray, int size) {
        String expected = "Expected to find a JSON array of size " + size;
        String actual = "Array has size " + jsonArray.size() + Reporter.formatResponse(jsonArray);
        try {
            if (jsonArray.size() == size) {
                reporter.pass(action, expected, actual);
            } else {
                reporter.apiFail(action, expected, actual);
            }
        } catch (Exception e) {
            log.debug(e);
            reporter.apiFail(action, expected, actual);
        }
    }

    /**
     * Extracts the Json object or array from the HTTP response, and puts in the Response object
     *
     * @param responseData - the String containing the raw response data.
     */
    void captureJsonResponse(String responseData) {
        setMessage(responseData);
        try {
            JsonParser parser = new JsonParser();
            JsonElement parsedData = parser.parse(responseData);
            setData(ResponseData.JSON, parsedData);
        } catch (JsonSyntaxException jse) {
            log.debug("This response is not json.", jse);
            setData(ResponseData.RAW, responseData);
        }
    }

    /**
     * @param code
     * @param reporter
     * @param call
     * @param service
     * @param requestData
     * @param headers
     * @deprecated This method is no longer useful
     */
    @Deprecated
    public void initialize(int code, Reporter reporter, String call, String service, RequestData requestData, Map headers) {
        this.statusCode = code;
        this.reporter = reporter;
        this.call = call;
        this.service = service;
        this.requestData = requestData;
        this.headers = headers;
        this.action = getRequestData();
    }

    /**
     * @param code
     * @param responseData
     * @param data
     * @param message
     * @deprecated This method is no longer useful.
     */
    @Deprecated
    public void initialize(int code, ResponseData responseData, Object data, String message) {
        this.statusCode = code;
        this.responseData = responseData;
        this.data = data;
        this.message = message;
        this.action = "";
    }

    /**
     * @param reporter
     * @deprecated This method is no longer useful.
     */
    @Deprecated
    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    public int getCode() {
        return statusCode;
    }

    public void setStatusCode(int status) {
        this.statusCode = status;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public Object getData() {
        return data;
    }

    public JsonObject getObjectData() {
        if ((responseData == ResponseData.JSON) && ((JsonElement) data).isJsonObject()) {
            return ((JsonObject) data).getAsJsonObject();
        }
        Assert.fail("Reporter not available. Expected JsonObject. Received: " + this.getMessage());
        return null; // Statement is never reached
    }

    public JsonArray getArrayData() {
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            return ((JsonArray) data).getAsJsonArray();
        }
        Assert.fail("Reporter not available. Expected JsonArrayData. Received: " + this.getMessage());
        return null; // Statement is never reached
    }

    private JsonElement getJsonData() {
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            return ((JsonArray) data).getAsJsonArray();
        }
        Assert.fail("Reporter not available. Expected JsonData. Received: " + this.getMessage());
        return null; // Statement is never reached
    }

    public File getFileData() {
        if (responseData == ResponseData.FILE) {
            return new File(data.toString());
        }
        Assert.fail("Reporter not available. Expected File. Received: " + this.getMessage());
        return null; // Statement is never reached
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean containsData() {
        return data != null;
    }

    public void setData(ResponseData responseData, Object data) {
        this.responseData = responseData;
        this.data = data;
    }

    ///////////////////////////////////////////////////////////////////
    // some comparisons for our services
    ///////////////////////////////////////////////////////////////////

    /**
     * Prints out the request that was made to collect the response
     */
    private String getRequestData() {
        String requestDataString = "";
        if (reporter != null && call != null && service != null) {
            if (requestData != null) {
                if (requestData.getJSON() != null) {
                    requestDataString = MADE_A + call + CALL_TO + service + "</b> with following params." +
                            Reporter.formatAndLabelJson(requestData, Reporter.PAYLOAD);
                } else {
                    requestDataString = MADE_A + call + CALL_TO + service + "</b> with following params: " + requestData.getParams();
                }
                if (requestData.getHeaders() != null) {
                    requestDataString += "<br><b>Headers:</b> " + requestData.getHeaders().toString();
                }
            } else {
                requestDataString = MADE_A + call + CALL_TO + service + "</b>";
            }
            return requestDataString;
        }
        return "Error making call";
    }

    private void recordSuccess(String expected, String actual) {
        if (reporter != null) {
            reporter.pass(action, expected, actual);
        }
    }

    private void recordApiFailure(String expected, String actual) {
        if (reporter != null) {
            reporter.apiFail(action, expected, actual);
        }
        Assert.fail(expected + " " + actual);
    }


    private void recordJsonArrayNullAssertion(String expectedResult) {
        reporter.apiFail(action, expectedResult, FOUND_NULL_ARRAY);
    }

    /**
     * helper method to create the assertionTable in the test step when testing a series of things in one step
     *
     * @param expectedPairs - key value pairs
     * @return table of assertions
     */
    private StringBuilder buildAssertionTable(Map<String, String> expectedPairs) {
        StringBuilder assertionTable = new StringBuilder();
        for (Map.Entry<String, String> entry : expectedPairs.entrySet()) {
            assertionTable.append("<div>");
            assertionTable.append(entry.getKey());
            assertionTable.append(" : ");
            assertionTable.append(entry.getValue());
            assertionTable.append("</div>");
        }
        return assertionTable;
    }

    /**
     * Verifies the response body is equals to the expected response
     * body, and writes that out to the output reporter
     *
     * @param expectedBody - the expected response body
     */
    public void assertMessageEquals(String expectedBody) {
        Assert.assertEquals(this.message, expectedBody);
    }


    /**
     * Verifies the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output reporter
     *
     * @param expectedJson - the expected response json object
     */
    public void assertEquals(JsonObject expectedJson) {
        Assert.assertEquals(getObjectData(), expectedJson);
    }

    /**
     * Verifies the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output reporter
     *
     * @param expectedArray - the expected response json array
     */
    public void assertEquals(JsonArray expectedArray) {
        String expected = EXPECTED + Reporter.formatResponse(expectedArray);
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        String theExpectedString = expectedArray.toString().replaceAll(ALPHA_SPACE, "");
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            String myContainedString = getArrayData().toString().replaceAll(ALPHA_SPACE, "");

            if (theExpectedString.equals(myContainedString)) {
                recordSuccess(expected, actual);
            } else {
                recordApiFailure(expected, actual);
                Assert.fail(expected + actual);
            }
        } else {
            recordJsonArrayNullAssertion(expected);
            Assert.fail(expected + " got null");
        }
    }

    /**
     * Verifies the actual response json payload sanitized is equal to the expected
     * response json payload sanitized, and writes that out to the output reporter
     *
     * @param expectedArray - the expected response json array
     */
    public void assertEqualsSanitized(JsonArray expectedArray) {
        // TODO: Fix this reporter stuff that has no business polluting a perfectly good method.
        String expected = EXPECTED + Reporter.formatResponse(expectedArray);
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        String theExpectedString = App.getSanitizedString(expectedArray.toString().replaceAll(ALPHA_SPACE, ""));

        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            String myContainedString = getArrayData().toString().replaceAll(ALPHA_SPACE, "");

            if (theExpectedString.equals(myContainedString)) {
                recordSuccess(theExpectedString, myContainedString);
            } else {
                recordApiFailure(expected, actual);
                Assert.fail(expected + " " + actual);
            }
        } else {
            recordJsonArrayNullAssertion(theExpectedString);
            Assert.fail(expected + " got null");
        }
    }

    /**
     * Verifies the actual response json payload contains each of the pair
     * values provided, and writes that to the output reporter
     * If the response contains a single object, the pairs will be looked for in that object.
     * If the response is an array, the method will look for the pairs inside each object in the array.
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    // TODO: Make this Assert.fail on failure
    public void assertContains(Map<String, String> expectedPairs) {
        StringBuilder expectedString = buildAssertionTable(expectedPairs);
        String expected =
                "Expected to find a response containing: <div><i>" + expectedString.toString() + "</i></div>;";
        String actual = FOUND + Reporter.formatResponse(getJsonData());

        Reporter.Result success;
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonObject()) {
            success = containsExpectedPairs(expectedPairs, getObjectData()) ? Reporter.Result.SUCCESS :
                    Reporter.Result.APIFAILURE;
        } else if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            success = Reporter.Result.SUCCESS;
            for (JsonElement arrayObject : getArrayData()) {
                if (!containsExpectedPairs(expectedPairs, arrayObject.getAsJsonObject())) {
                    success = Reporter.Result.APIFAILURE;
                }
            }
        } else {
            actual = FOUND_NULL_OBJECT;
            success = Reporter.Result.APIFAILURE;
        }
        if (success == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail(expected + " " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Asserts that a response does not contain the entered key-value pairs.
     * For arrays, this checks each object in the array.
     *
     * @param expectedPairs key-value pairs the should not be in the response
     */
    // TODO: Make this Assert.fail on failure
    public void assertNotContains(Map<String, String> expectedPairs) {
        StringBuilder expectedString = buildAssertionTable(expectedPairs);
        String expected =
                "Expected to find a response NOT containing: <div><i>" + expectedString.toString() + "</i></div>;";
        String actual = FOUND + Reporter.formatResponse(getJsonData());

        Reporter.Result success;
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonObject()) {
            success = containsExpectedPairs(expectedPairs, getObjectData()) ? Reporter.Result.APIFAILURE :
                    Reporter.Result.SUCCESS;
        } else if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            success = Reporter.Result.SUCCESS;
            for (JsonElement arrayObject : getArrayData()) {
                if (containsExpectedPairs(expectedPairs, arrayObject.getAsJsonObject())) {
                    success = Reporter.Result.APIFAILURE;
                }
            }
        } else {
            actual = FOUND_NULL_OBJECT;
            success = Reporter.Result.APIFAILURE;
        }
        if (success == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail(expected + " " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Helper method to assert that a JSON object has key-value pairs.
     *
     * @param expectedPairs pairs of key-values that should be in the object
     * @return if the object contains the key-value pairs
     */
    private boolean containsExpectedPairs(Map<String, String> expectedPairs, JsonObject singleObject) {
        for (Map.Entry<String, String> entry : expectedPairs.entrySet()) {
            if (!singleObject.has(entry.getKey()) ||
                    !singleObject.get(entry.getKey()).getAsString().equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies the actual response json payload contains a key with a value
     * equals to the expected json element, and writes that out to the output
     * reporter
     * If the response contains a single object, the pairs will be looked for in that object.
     * If the response is an array, the method will look for the pairs inside each object in the array.
     *
     * @param key          - a String key value expected in the result
     * @param expectedJson - the expected response json object
     */
    // TODO: Make this Assert.fail on failure
    public void assertContains(String key, JsonElement expectedJson) {
        String expected =
                "Expected to find a response with key <i>" + key + EQUAL_TO + expectedJson.toString();
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success = Reporter.Result.APIFAILURE;
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonObject() && getObjectData().has(key)) {
            success = getObjectData().get(key).equals(expectedJson) ? Reporter.Result.SUCCESS :
                    Reporter.Result.APIFAILURE;
        } else if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            for (JsonElement arrayObject : getArrayData()) {
                if (arrayObject.getAsJsonObject().has(key) &&
                        arrayObject.getAsJsonObject().get(key).equals(expectedJson)) {
                    success = Reporter.Result.SUCCESS;
                }
            }
        }
        if (success == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail(expected + " " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Verifies the response json payload contains an object at the key
     * that contains a nestedKey with a value equal to a String, Integer, or Boolean
     * example response: { "books": { "id": 123 }, "author": "John Doe" }
     *
     * @param key       - top level json key eg. "books" in object above
     * @param nestedKey - key in nested object eg. "id" in object above
     * @param value     - a JSON Element ( can be Primitive, Object, or Array)
     */
    // TODO: Make this Assert.fail on failure
    private void assertContainsNested(String key, String nestedKey, JsonElement value) {
        String expected =
                "Expected to find a response object with <i>" + key + "." + nestedKey + EQUAL_TO + value;
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success = Reporter.Result.APIFAILURE;
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonObject() && getObjectData().has(key)) {
            JsonObject nestedObject = getObjectData().get(key).getAsJsonObject();
            if (nestedObject.has(nestedKey)) {
                success = nestedObject.get(nestedKey).equals(value) ? Reporter.Result.SUCCESS :
                        Reporter.Result.APIFAILURE;
            }
        }
        if (success == Reporter.Result.APIFAILURE) {
            reporter.apiFail(action, expected, actual);
            Assert.fail(expected + " " + actual);
        } else {
            reporter.pass(action, expected, actual);
        }
    }

    /**
     * Verifies the response json payload contains an object at the key
     * that contains a nestedKey that contains a value
     * example response: { "books": { "id": 123 }, "author": "John Doe" }
     *
     * @param key       - top level json key eg. books in object above
     * @param nestedKey - key in nested object eg. "id" in object above
     * @param value     - must be String, Integer or Boolean
     */
    public void assertContainsNested(String key, String nestedKey, Object value) {
        JsonElement convertedValue = null;
        if (value instanceof Boolean) {
            convertedValue = new JsonPrimitive((Boolean) value);
        } else if (value instanceof String) {
            convertedValue = new JsonPrimitive((String) value);
        } else if (value instanceof Integer) {
            convertedValue = new JsonPrimitive((Integer) value);
        }
        assertContainsNested(key, nestedKey, convertedValue);
    }

    // TODO: Make this Assert.fail on failure
    private void assertContains(String key, JsonPrimitive value) {
        String expected = "Expected to find a response with key <i>" + key + EQUAL_TO + value;
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success = Reporter.Result.APIFAILURE;
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonObject() && getObjectData().has(key)) {
            success = getObjectData().get(key).equals(value) ? Reporter.Result.SUCCESS : Reporter.Result.APIFAILURE;
        } else if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            for (JsonElement arrayObject : getArrayData()) {
                if (arrayObject.getAsJsonObject().has(key) && arrayObject.getAsJsonObject().get(key).equals(value)) {
                    success = Reporter.Result.SUCCESS;
                }
            }
            if (success == Reporter.Result.APIFAILURE) {
                if (reporter != null) {
                    reporter.apiFail(action, expected, actual);
                }
                Assert.fail(expected + " " + actual);
            } else {
                if (reporter != null) {
                    reporter.pass(action, expected, actual);
                }
            }
        }
    }

    /**
     * Verifies the actual response json payload contains a key with a value
     * equals to the String, and writes that out to the output
     * reporter
     * If the response contains a single object, the pairs will be looked for in that object.
     * If the response is an array, the method will look for the pairs inside each object in the array.
     *
     * @param key   - a String key value expected in the result
     * @param value - the expected String value for a specified key
     */
    // TODO: Make this Assert.fail on failure
    public void assertContains(String key, String value) {
        JsonPrimitive jsonValue = new JsonPrimitive(value);
        assertContains(key, jsonValue);
    }

    /**
     * Verifies the actual response json payload contains a key with a value
     * equals to the int, and writes that out to the output
     * reporter
     * If the response contains a single object, the pairs will be looked for in that object.
     * If the response is an array, the method will look for the pairs inside each object in the array.
     *
     * @param key   - a String key value expected in the result
     * @param value - an int value that corresponds with a key in the response
     */
    // TODO: Make this Assert.fail on failure
    public void assertContains(String key, int value) {
        JsonPrimitive jsonValue = new JsonPrimitive(value);
        assertContains(key, jsonValue);
    }

    /**
     * Verifies the response does not contain a certain key-value pair.
     * For arrays, this checks each object in the array.
     *
     * @param key          key that should not be in the response
     * @param expectedJson value that the key should not have in the response
     */
    // TODO: Make this Assert.fail on failure
    public void assertNotContains(String key, JsonElement expectedJson) {
        String expected = "Expected to find a response not containing a key <i>" + key + EQUAL_TO +
                expectedJson.toString();
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success = Reporter.Result.SUCCESS;
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonObject() && getObjectData().has(key)) {
            success = getObjectData().get(key).equals(expectedJson) ? Reporter.Result.APIFAILURE :
                    Reporter.Result.SUCCESS;
        } else if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            for (JsonElement arrayObject : getArrayData()) {
                if (arrayObject.getAsJsonObject().has(key) &&
                        arrayObject.getAsJsonObject().get(key).equals(expectedJson)) {
                    success = Reporter.Result.APIFAILURE;
                }
            }
        }
        if (success == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail(expected + " " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Verifies the response does not contain a certain key-value pair.
     * For arrays, this checks each object in the array.
     *
     * @param key key that should not be in the response
     */
    // TODO: Make this Assert.fail on failure
    public void assertNotContains(String key) {
        String expected = "Expected to find a response not containing a key <i>" + key;
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success = Reporter.Result.SUCCESS;
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonObject() && getObjectData().has(key)) {
            success = getObjectData().get(key) != null ? Reporter.Result.APIFAILURE : Reporter.Result.SUCCESS;
        } else if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            for (JsonElement arrayObject : getArrayData()) {
                if (arrayObject.getAsJsonObject().has(key)) {
                    success = Reporter.Result.APIFAILURE;
                }
            }
        }
        if (success == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail(expected + " " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Verifies the actual response json payload contains to the expected json
     * element, and writes that out to the output reporter
     *
     * @param expectedJson - the expected response json array
     */
    // TODO: Make this Assert.fail on failure
    public void assertContains(JsonElement expectedJson) {
        String expected = "Expected to find a response containing:" + Reporter.formatResponse(expectedJson);
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success = Reporter.Result.APIFAILURE;
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            success = getArrayData().contains(expectedJson) ? Reporter.Result.SUCCESS : Reporter.Result.APIFAILURE;
        }
        if (success == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail(expected + " " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Asserts that the response does not contain a certain JSON element.
     *
     * @param expectedJson JSON that should not be in the response
     */
    // TODO: Make this Assert.fail on failure
    public void assertNotContains(JsonElement expectedJson) {
        String expected = "Expected to find a response not containing:" + Reporter.formatResponse(expectedJson);
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success = Reporter.Result.SUCCESS;
        if (responseData == ResponseData.JSON && ((JsonElement) data).isJsonArray()) {
            success = getArrayData().contains(expectedJson) ? Reporter.Result.APIFAILURE : Reporter.Result.SUCCESS;
        }
        if (success == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail(expected + " " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Asserts that the response is a valid JSON array
     */
    // TODO: Make this Assert.fail on failure
    public void assertValidArrayData() {
        String expected = "Expected to find a valid JSON array";
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success;
        try {
            success = getArrayData().isJsonArray() ? Reporter.Result.SUCCESS : Reporter.Result.APIFAILURE;
        } catch (Exception e) {
            log.debug(e);
            success = Reporter.Result.APIFAILURE;
        }
        if (success == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail(expected + " " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Asserts that the response is a valid JSON array and contains the expected user event
     */
    public void assertValidArrayDataWithEvent(String event) {
        String expected = "Expected to find a valid JSON array with the " + event + " event";
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success = Reporter.Result.APIFAILURE;
        try {
            success = getArrayData().get(0).getAsJsonObject().get("eventType").getAsString().equals(event) ?
                    Reporter.Result.SUCCESS : Reporter.Result.APIFAILURE;
        } catch (Exception e) {
            log.debug(e);
            success = Reporter.Result.APIFAILURE;
        } finally {
            if (success == Reporter.Result.APIFAILURE) {
                reporter.apiFail(action, expected, actual);
            } else {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Verifies the actual response statusCode is equals to the expected response
     * statusCode, and writes that out to the output reporter
     *
     * @param expectedCode - the expected response statusCode
     */
    // TODO: Make this Assert.fail on failure
    public void assertEquals(int expectedCode) {
        String expected = "Expected to find a response statusCode of <b>" + expectedCode + "</b>";
        String actual = "Found response statusCode of <b>" + statusCode + "</b> with message: " + this.getMessage();
        if (statusCode == expectedCode) {
            recordSuccess(expected, actual);
        } else {
            recordApiFailure(expected, actual);
            Assert.fail(expected + " " + actual);
        }
    }

    /**
     * Asserts that the response is an empty JSON array
     */
    // TODO: Make this Assert.fail on failure
    public void assertEmptyArrayData() {
        String expected = "Expected to find an empty JSON array";
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result success;
        try {
            success = getArrayData().size() == 0 ? Reporter.Result.SUCCESS : Reporter.Result.APIFAILURE;
        } catch (Exception e) {
            log.debug(e);
            success = Reporter.Result.APIFAILURE;
        }
        if (success == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail("Expected an empty JSON array but found " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Asserts that the response contains some property name with the boolean value provided
     */
    // TODO: Make this Assert.fail on failure
    public void assertPropertyValue(String name, boolean value) {
        String expected = "Expected to find a JSON array with the property value " + name + " set to " + value;
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result result = Reporter.Result.APIFAILURE;
        try {
            for (JsonElement jsonElement : getArrayData()) {
                String propertyName = jsonElement.getAsJsonObject().get("propertyName").getAsString();
                if (propertyName.equals(name)) {
                    result = jsonElement.getAsJsonObject().get("profileValues").getAsJsonArray().get(0).
                            getAsJsonObject().get("valueAsBoolean").getAsBoolean() == value ?
                            Reporter.Result.SUCCESS : Reporter.Result.APIFAILURE;
                }
            }
        } catch (Exception e) {
            log.debug(e);
            result = Reporter.Result.APIFAILURE;
        }
        if (result == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail("Expected JSON Array with property of " + name + " set to " + value + ", but found " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Asserts that the response contains some property name with the string value provided
     */
    // TODO: Make this Assert.fail on failure
    public void assertPropertyValue(String name, String value) {
        String expected = "Expected to find a JSON array with the property value " + name + " set to " + value;
        String actual = FOUND + Reporter.formatResponse(getJsonData());
        Reporter.Result result = Reporter.Result.APIFAILURE;
        try {
            for (JsonElement jsonElement : getArrayData()) {
                String propertyName = jsonElement.getAsJsonObject().get("propertyName").getAsString();
                if (propertyName.equals(name)) {
                    result = jsonElement.getAsJsonObject().get("profileValues").getAsJsonArray().get(0).
                            getAsJsonObject().get("valueAsString").getAsString().equals(value) ?
                            Reporter.Result.SUCCESS : Reporter.Result.APIFAILURE;
                }
            }
        } catch (Exception e) {
            log.debug(e);
            result = Reporter.Result.APIFAILURE;
        }
        if (result == Reporter.Result.APIFAILURE) {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail("Expected JSON Array with property of " + name + " set to " + value + ", but found " + actual);
        } else {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        }
    }

    /**
     * Verifies if HTML response has expected value
     *
     * @param response      - current response
     * @param expectedValue - expected value from the response
     */
    // TODO: Make this Assert.fail on failure
    public void assertHTMLContains(Response response, String expectedValue) {
        String expected = "Expected to find a HTML response with value " + expectedValue;
        String actual = FOUND + response.getMessage();
        if (response.getMessage().contains(expectedValue)) {
            if (reporter != null) {
                reporter.pass(action, expected, actual);
            }
        } else {
            if (reporter != null) {
                reporter.apiFail(action, expected, actual);
            }
            Assert.fail("HTML response with " + expectedValue + " not found. Received " + actual);
        }
    }

    /**
     * A list of available options to parse the expected response data to
     */
    public enum ResponseData {
        JSON, FILE, RAW
    }
}
