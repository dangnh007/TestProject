package com.pmt.health.utilities;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pmt.health.interactions.services.HTTP;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.steps.Configuration;
import cucumber.api.Scenario;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import static com.pmt.health.steps.Configuration.SCENARIO;

public class Sauce {

    public static final String SESSION_ID = "SessionId";
    private static final String SAUCE_API = "https://saucelabs.com/rest/v1/";
    private static final String JOBS = "/jobs/";
    private static final Logger log = Logger.getLogger(Sauce.class);

    private Sauce() {
    }

    public static Boolean isUsed() {
        String hub = System.getProperty("hub");
        return (hub != null && hub.contains("ondemand.saucelabs.com"));
    }

    private static String getCreds(String hub) {
        String[] parts = hub.split("@");
        if (parts.length != 2) {
            return null;
        }
        String[] startParts = parts[0].split("/");
        if (startParts.length != 3) {
            return null;
        }
        return startParts[2];
    }

    public static String getUser() {
        if (!isUsed()) {
            return null;
        }
        String creds = getCreds(System.getProperty("hub"));
        if (creds == null) {
            return null;
        }
        String[] parts = creds.split(":");
        if (parts.length != 2) {
            return null;
        }
        return parts[0];
    }

    public static String getKey() {
        if (!isUsed()) {
            return null;
        }
        String creds = getCreds(System.getProperty("hub"));
        if (creds == null) {
            return null;
        }
        String[] parts = creds.split(":");
        if (parts.length != 2) {
            return null;
        }
        return parts[1];
    }

    public static void updateJob(ITestResult result) throws IOException {
        if (isUsed() && result.getAttribute(SESSION_ID) != null) {
            String sessionId = result.getAttribute(SESSION_ID).toString();
            JsonObject json = new JsonObject();
            json.addProperty("passed", (result.getStatus() == ITestResult.SUCCESS));
            JsonArray tags = (JsonArray) new Gson().toJsonTree((GherkinFeature.getScenario(result)).getSourceTagNames(),
                    new TypeToken<Collection<String>>() {
                    }.getType());
            json.add("tags", tags);
            json.add("custom-data", new Configuration(null).getSystemInfo(null));
            RequestData requestData = new RequestData();
            requestData.setJSON(json);

            HTTP http =
                    new HTTP(SAUCE_API + getUser() + JOBS, getUser(),
                            getKey());
            http.simplePut(sessionId, requestData);
            // if jenkins spawned add build job information
            // TODO
        }
    }

    private static String getAsset(ITestResult result, Asset asset, String fileInfo) throws IOException {
        if (isUsed() && result.getAttribute(SESSION_ID) != null) {
            String sessionId = result.getAttribute(SESSION_ID).toString();
            String filePrefix = GherkinHelpers.getFileName((Scenario) result.getAttribute(SCENARIO));
            filePrefix = filePrefix.substring(0, filePrefix.length() - 5);
            HTTP http =
                    new HTTP(SAUCE_API + getUser() + JOBS, getUser(),
                            getKey());
            RequestData requestData = new RequestData();
            if (tryToDownloadVideo(asset, fileInfo, sessionId, filePrefix, http, requestData).getCode() != 200) {
                log.error(sessionId + "/assets/" + fileInfo + asset.getFileName() + " could not be downloaded from " +
                        "sauce");
                throw new FileNotFoundException();
            }
            return filePrefix + "_" + fileInfo + asset.getFileName();
        }
        return null;
    }

    private static Response tryToDownloadVideo(Asset asset, String fileInfo, String sessionId, String filePrefix,
                                               HTTP http, RequestData requestData) throws IOException {
        Response resp = new Response();
        for (int i = 0; i < 15; i++) {
            try {
                resp = http.simpleGet(sessionId + "/assets/" + fileInfo + asset.getFileName(), requestData,
                        Response.ResponseData.FILE, filePrefix);
                if (resp.getCode() == 200) {
                    break;
                }
            } catch (FileNotFoundException fnfe) {
                // Retry
                log.error(fnfe);
            }
        }
        return resp;
    }

    public static String getSeleniumLog(ITestResult result) throws IOException {
        return getAsset(result, Asset.LOG, "");
    }

    public static String getRecording(ITestResult result) throws IOException {
        return getAsset(result, Asset.VIDEO, "");
    }

    public static String getScreenshot(ITestResult result, String screenshot) throws IOException {
        return getAsset(result, Asset.SCREENSHOT, screenshot);
    }

    public enum Asset {
        LOG("selenium-server.log"), VIDEO("video.mp4"), SCREENSHOT("screenshot.png");

        private String fileName;

        Asset(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
