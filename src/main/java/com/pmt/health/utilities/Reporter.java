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

package com.pmt.health.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.services.RequestData;
import com.pmt.health.interactions.services.Response;
import com.pmt.health.steps.Configuration;
import cucumber.api.Scenario;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Assert;
import org.testng.log4testng.Logger;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * A custom output reporter, recording all details of every step performed, both
 * actions and app. Actions, expected results, and actual results are captured.
 * All asserts have a screenshot taken for traceability, while all failing
 * actions also have a screenshot taken to assist with debugging purposes
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Reporter {

    public static final String PASSORFAIL = "PASSORFAIL";
    public static final String A_TARGET_BLANK_HREF = "<a target='_blank' href='";
    public static final String PAYLOAD = "toggle API payload";
    public static final String RESPONSE = "toggle API response";
    // constants
    private static final Logger log = Logger.getLogger(Reporter.class);
    private static final String START_ROW = "   <tr>\n";
    private static final String START_CELL = "    <td>";
    private static final String END_CELL = "</td>\n";
    private static final String END_ROW = "   </tr>\n";
    private static final String A_TARGET_HREF = "<a target='_blank' href='";
    // the image width for reporting
    private static final int EMBEDDED_IMAGE_WIDTH = 300;
    private static final Random rnd = new Random();
    private final Scenario gherkin;
    private final String feature;
    private final Scenario scenario;
    private final Collection<String> labels;
    private final Collection<String> links;
    private final String directory;
    private final File file;
    private final String filename;
    private final Device device;
    private final List<String> screenshots = new ArrayList<>();
    private App app = null;
    // timing of the test
    private long startTime;
    private long lastTime = 0;
    // this will track the step numbers
    private int stepNum = 0;
    // this will keep track of the errors
    private int errors = 0;


    /**
     * Creates a new instance of the Reporter, which will serve as the
     * detailed log
     *
     * @param directory - a string of the directory holding the files
     * @param scenario  - the cucumber scenario containing all of the test suite and
     *                  test case information
     * @param device    - the device the tests are running on
     */
    public Reporter(String directory, Scenario scenario, Device device, Configuration configuration) throws IOException {
        this.directory = directory;
        this.gherkin = scenario;
        this.feature = GherkinHelpers.getFeatureName(scenario);
        this.scenario = scenario;
        this.labels = GherkinHelpers.getLabels(scenario);
        this.links = GherkinHelpers.getTestLinks(scenario);
        this.device = device;
        filename = GherkinHelpers.getFileName(scenario);
        file = new File(directory, filename);
        setupFile();
        setStartTime();
        createOutputHeader(configuration);
    }

    /**
     * Generates a random string of alpha-numeric characters
     *
     * @param length the length of the random string
     * @return String: random string of characters
     */
    private static String getRandomString(int length) {
        if (length <= 0) {
            return "";
        }
        String stringChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(stringChars.charAt(rnd.nextInt(stringChars.length())));
        }
        return sb.toString();
    }

    /**
     * Formats the response parameters to be 'prettily' printed out in HTML
     *
     * @param json - the json response to be formatted.
     * @return String: a 'prettily' formatted string that is HTML safe to output
     */
    @SuppressWarnings("squid:S1192") // /div tag
    public static String formatResponse(JsonElement json) {
        StringBuilder output = new StringBuilder();
        output.append("<div><i>");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        output.append(gson.toJson(json));
        output.append("</i></div>");
        return output.toString();
    }

    /**
     * Take the json element, formats it in nice html, and puts the result in a
     * togglable block
     *
     * @param request: the json payload or response
     * @param message: what message to show to toggle the data
     * @return String : the html friendly block
     */
    @SuppressWarnings("squid:S1192") // /div tag
    public static String formatAndLabelJson(RequestData request, String message) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        StringBuilder sb = new StringBuilder();
        sb.append(
                " <div style='cursor:pointer;color: blue;' onclick='this.firstElementChild.style.display == \"none\" ? this.firstElementChild.style.display=\"block\" : this.firstElementChild.style.display=\"none\";'>");
        sb.append(message);
        sb.append("<div class='params' style='padding-left:30px;display:none;color:black;'>");
        if (request.getMultipartData() != null) {
            for (Map.Entry<String, String> entry : request.getMultipartData().entrySet()) {
                sb.append(entry.getKey()).append(":").append(entry.getValue());
            }
        }
        if (request.getParams() != null) {
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                sb.append(entry.getKey()).append(":").append(entry.getValue());
            }
        }
        if (request.getJSON() != null) {
            sb.append(formatHTML(gson.toJson(request.getJSON())));
        }
        sb.append("</div>");
        sb.append("</div>");
        return sb.toString();
    }

    /**
     * Take the json element, formats it in nice html, and puts the result in a
     * togglable block
     *
     * @param response: the json payload or response
     * @param message:  what message to show to toggle the data
     * @return String : the html friendly block
     */
    @SuppressWarnings("squid:S1192") // /div tag
    public static String formatAndLabelJson(Response response, String message) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        StringBuilder sb = new StringBuilder();
        sb.append(
                " <div style='cursor:pointer;color: blue;' onclick='this.firstElementChild.style.display == \"none\" ? this.firstElementChild.style.display=\"block\" : this.firstElementChild.style.display=\"none\";'>");
        sb.append(message);
        sb.append("<div class='params' style='padding-left:30px;display:none;color:black;'><div><b>Response Code</b>: ")
                .append(response.getCode()).append("</div>");
        sb.append("<div><b>Response Headers</b>: ");
        for (Map.Entry<String, List<String>> entry : response.getHeaders().entrySet()) {
            sb.append("<div style='padding-left:40px;'>").append(entry.getKey()).append(":").append(entry.getValue())
                    .append("</div>");
        }
        sb.append("</div>");
        sb.append("<div><b>Response Content</b>: <div style='padding-left:40px;'>");
        if (response.containsData()) {
            sb.append(formatHTML(gson.toJson(response.getData())));
        } else {
            sb.append(formatHTML(response.getMessage()));
        }
        sb.append("</div></div></div></div>");
        return sb.toString();
    }

    /**
     * Take the json element, formats it in nice html, and puts the result in a
     * togglable block
     *
     * @param json:    the json payload or response
     * @param message: what message to show to toggle the data
     * @return String : the html friendly block
     */
    @SuppressWarnings("squid:S1192") // /div tag
    public static String formatAndLabelJson(JsonElement json, String message) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        return " <div style='cursor:pointer;color: blue;' " +
                "onclick='this.firstElementChild.style.display == \"none\" ? " +
                "this.firstElementChild.style.display=\"block\" : " +
                "this.firstElementChild.style.display=\"none\";'>" + message +
                "<div class='params' style='padding-left:30px;display:none;color:black;'>" +
                formatHTML(gson.toJson(json)) + "</div></div>";
    }

    /**
     * Takes a generic string and replaces spaces and new lines with HTML friendly
     * pieces for display purposes
     *
     * @param string : the regular string to be formatted into an HTML pretty rendering
     *               string
     * @return String : the replaced result
     */
    private static String formatHTML(String string) {
        if (string == null) {
            return "";
        }
        return string.replaceAll(" ", "&nbsp;").replaceAll("\n", "<br/>");
    }

    public File getFile() {
        return file;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    /**
     * Captures the entire page screen shot, and created an HTML file friendly
     * link to place in the output file
     *
     * @return String: the image link string
     */
    private String captureEntirePageScreenshot() {
        String imageName = generateImageName();
        String imageLink = generateImageLink(imageName);
        try {
            app.takeScreenshot(imageName);
            screenshots.add(imageName);
        } catch (Exception e) {
            log.error(e);
            imageLink = "<br/><b><font class='fail'>No Screenshot Available</font></b>";
        }
        return imageLink;
    }

    /**
     * Counts the number of occurrence of a string within a file
     *
     * @param textToFind - the text to count
     * @return Integer: the number of times the text was found in the file
     * provided
     */
    private int countInstancesOf(String textToFind) {
        int count = 0;
        try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(textToFind)) {
                    count++;
                }
            }
        } catch (IOException e) {
            log.error(e);
        }
        return count;
    }

    /**
     * Creates the specially formatted output header for the particular test
     * case
     */
    private void createOutputHeader(Configuration configuration) throws IOException {
        // setup some constants
        String endBracket3 = "   }\n";
        String endBracket4 = "    }\n";
        String boldFont = "    font-weight:bold;\n";
        String swapRow = "   </tr><tr>\n";

        // setup the date
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        String datePart = sdf.format(new Date());
        String sTime = stf.format(startTime);

        // setup the application information
        JsonObject systemInfo = configuration.getSystemInfo(this);
        String linkedSite = A_TARGET_HREF + configuration.getEnvironmentURL() + "'>" +
                Configuration.getEnvironment() + "</a>";

        // Start writing to the reporter
        try (FileWriter fw = new FileWriter(file); BufferedWriter out = new BufferedWriter(fw)) {
            out.write("<html>\n");
            out.write(" <head>\n");
            out.write("  <title>" + scenario.getName() + "</title>\n");
            out.write("  <style type='text/css'>\n");
            out.write("   table {\n");
            out.write("    margin-left:auto;margin-right:auto;\n");
            out.write("    width:90%;\n");
            out.write("    border-collapse:collapse;\n");
            out.write(endBracket3);
            out.write("   table, td, th {\n");
            out.write("    border:1px solid black;\n");
            out.write("    padding:0px 10px;\n");
            out.write(endBracket3);
            out.write("   th {\n");
            out.write("    text-align:right;\n");
            out.write(endBracket3);
            out.write("   td {\n");
            out.write("    word-wrap: break-word;\n");
            out.write(endBracket3);
            out.write("   .warning {\n");
            out.write("    color:orange;\n");
            out.write(endBracket3);
            out.write("   .check {\n");
            out.write("    color:orange;\n");
            out.write(boldFont);
            out.write(endBracket3);
            out.write("   .fail {\n");
            out.write("    color:black;\n");
            out.write("    background-color:#ffa3a3;\n");
            out.write(boldFont);
            out.write(endBracket3);
            out.write("   .pass {\n");
            out.write("    color:green;\n");
            out.write(boldFont);
            out.write(endBracket3);
            out.write("  </style>\n");
            out.write("  <script type='text/javascript'>\n");
            out.write("   function toggleImage( imageName ) {\n");
            out.write("    var element = document.getElementById( imageName );\n");
            out.write("    element.src = location.href.match(/^.*\\//) + imageName;\n");
            out.write("    element.style.display = (element.style.display != 'none' ? 'none' : '' );\n");
            out.write(endBracket3);
            out.write("   function displayImage( imageName ) {\n");
            out.write("    window.open( location.href.match(/^.*\\//) + imageName )\n");
            out.write(endBracket3);
            out.write("   function toggleVis(col_no, do_show) {\n");
            out.write("    var stl;\n");
            out.write("    if (do_show) stl = ''\n");
            out.write("    else         stl = 'none';\n");
            out.write("    var tbl  = document.getElementById('all_results');\n");
            out.write("    var rows = tbl.getElementsByTagName('tr');\n");
            out.write("    var cels = rows[0].getElementsByTagName('th')\n");
            out.write("    cels[col_no].style.display=stl;\n");
            out.write("    for (var row=1; row<rows.length;row++) {\n");
            out.write("     var cels = rows[row].getElementsByTagName('td')\n");
            out.write("     cels[col_no].style.display=stl;\n");
            out.write(endBracket4);
            out.write(endBracket3);
            out.write("   function getElementsByClassName(oElm, strTagName, strClassName){\n");
            out.write(
                    "    var arrElements = (strTagName == '*' && document.all)? document.all : oElm.getElementsByTagName(strTagName);\n");
            out.write("    var arrReturnElements = new Array();\n");
            out.write("    strClassName = strClassName.replace(/\\-/g, '\\\\-');\n");
            out.write("    var oRegExp = new RegExp('(^|\\s)' + strClassName + '(\\s|$)');\n");
            out.write("    var oElement;\n");
            out.write("    for(var i=0; i<arrElements.length; i++){\n");
            out.write("     oElement = arrElements[i];\n");
            out.write("     if(oRegExp.test(oElement.className)){\n");
            out.write("      arrReturnElements.push(oElement);\n");
            out.write("     }\n");
            out.write(endBracket4);
            out.write("    return (arrReturnElements)\n");
            out.write(endBracket3);
            out.write("   function fixImages( imageName ) {\n");
            out.write("    top.document.title = document.title;\n");
            out.write("    allImgIcons = getElementsByClassName( document, 'img', 'imgIcon' );\n");
            out.write("    for( var element in allImgIcons ) {\n");
            out.write("     element.src = location.href.match(/^.*\\//) + element.src;\n");
            out.write(endBracket4);
            out.write(endBracket3);
            out.write("  </script>\n");
            out.write(" </head>\n");
            out.write(" <body onLoad='fixImages()'>\n");
            out.write("  <table>\n");
            out.write(START_ROW);
            out.write("    <th bgcolor='lightblue'><font size='5'>Test</font></th>\n");
            out.write("    <td bgcolor='lightblue' colspan=3><font size='5'>" + file.getName().substring(0, file.getName().indexOf('.')) + A_TARGET_BLANK_HREF +
                    GherkinHelpers.getTestURL(gherkin) + "'>" + scenario.getName() + "</a></font></td>\n");
            out.write(swapRow);
            out.write("    <th>Date Tested</th>\n");
            out.write(START_CELL + datePart + END_CELL);
            out.write("    <th>Device</th>\n");
            String deviceString = this.device.toString();
            if (Property.isHeadless()) {
                deviceString += " (HEADLESS)";
            }
            if (gherkin.getSourceTagNames().contains("@api")) {
                deviceString = "NONE (STRICTLY API)";
            }
            out.write(START_CELL + deviceString + END_CELL);
            out.write(swapRow);
            out.write("    <th>Program Tested</th>\n");
            out.write(START_CELL + configuration.getProgram() + END_CELL);
            out.write("    <th rowspan='3'>Test Run Time</th>\n");
            out.write("    <td rowspan='3'>\n");
            out.write("     Start:\t" + sTime + " <br/>\n");
            out.write("     End:\tTIMEFINISHED <br/>\n");
            out.write("     Run Time:\tRUNTIME \n");
            out.write("    </td>\n ");
            out.write(swapRow);
            out.write("    <th>Version Tested</th>\n");
            out.write(START_CELL + configuration.getVersion(systemInfo) + END_CELL);
            out.write(swapRow);
            out.write("    <th>Site Tested</th>\n");
            out.write(START_CELL + linkedSite + END_CELL);
            out.write("    <th>Testing Suite</th>\n");
            out.write(START_CELL + A_TARGET_HREF + GherkinHelpers.getEpicLink(gherkin).toString() + "'>" +
                    this.feature + "</a>" + END_CELL);
            out.write(swapRow);
            out.write("    <th>Testing Groups</th>\n");
            out.write(START_CELL + String.join(", ", this.labels) + END_CELL);
            out.write("    <th>Stories Verified</th>\n");
            out.write(START_CELL);
            for (String link : this.links) {
                out.write(A_TARGET_HREF + Property.getJiraProperty("link") + "/browse/" + link + "'>" +
                        link + "</a> ");
            }
            out.write(END_CELL);
            out.write(swapRow);
            out.write("    <th>Overall Results</th>\n");
            out.write("    <td colspan=3 style='padding: 0px;'>\n");
            out.write("     <table style='width: 100%;'><tr>\n");
            out.write("      <td font-size='big' rowspan=2>PASSORFAIL</td>\n");
            out.write("      <td><b>Steps Performed</b></td><td><b>Steps Passed</b></td>" +
                    "<td><b>Steps Failed</b></td>\n");
            out.write("     </tr><tr>\n");
            out.write("      <td>STEPSPERFORMED</td><td>STEPSPASSED</td><td>STEPSFAILED</td>\n");
            out.write("     </tr></table>\n");
            out.write("    </td>\n");
            out.write(swapRow);
            out.write("    <th>View Results</th>\n");
            out.write("    <td colspan=3>\n");
            out.write("     <input type=checkbox name='step' onclick='toggleVis(0,this.checked)' checked>Step\n");
            out.write("     <input type=checkbox name='action' onclick='toggleVis(1,this.checked)' checked>Action \n");
            out.write(
                    "     <input type=checkbox name='expected' onclick='toggleVis(2,this.checked)' checked>Expected Results \n");
            out.write(
                    "     <input type=checkbox name='actual' onclick='toggleVis(3,this.checked)' checked>Actual Results \n");
            out.write(
                    "     <input type=checkbox name='times' onclick='toggleVis(4,this.checked)' checked>Step Times \n");
            out.write("     <input type=checkbox name='result' onclick='toggleVis(5,this.checked)' checked>Results\n");
            out.write("    </td>\n");
            out.write(END_ROW);
            out.write("  </table>\n");
            out.write("  <table id='all_results'>\n");
            out.write(START_ROW);
            out.write("    <th align='center'>Step</th><th style='text-align:center'>Action</th>" +
                    "<th style='text-align:center'>Expected Result</th>" +
                    "<th style='text-align:center'>Actual Result</th>" +
                    "<th style='text-align:center'>Step Times</th><th style='text-align:center'>Pass/Fail</th>\n");
            out.write(END_ROW);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Ends and closes the output test reporter. The HTML is properly ended, and the
     * reporter is analyzed to determine if the test passed or failed, and that
     * information is updated, along with the overall timing of the test
     */
    public void finalizeOutputFile(int testStatus) {
        // reopen the reporter
        try (FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw)) {
            out.write("  </table>\n");
            if (app.getDriver() != null && Device.CHROME.equals(app.getDevice())) {
                LogEntries logEntries = app.getDriver().manage().logs().get(LogType.BROWSER);
                out.write(" <div align='center' font-size='big' style='cursor:pointer;color: blue;' onclick='this" +
                        ".nextSibling.style.display == \"none\" ? this.nextSibling.style.display=\"block\" : this" +
                        ".nextSibling.style.display=\"none\";'>");
                out.write("<font size='5'> Chrome Logs </font></div><div style='display:none;margin-left:auto;" +
                        "margin-right:auto;width:90%;'>");
                for (LogEntry entry : logEntries) {
                    out.write(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage() +
                            "<br>");
                }
                out.write("</div>");
            }
            out.write(" </body>\n");
            out.write("</html>\n");
        } catch (IOException e) {
            log.error(e);
        }
        if (app.getDriver() != null) {
            app.getDriver().quit();
        }
        // Record the metrics
        int passes = countInstancesOf("<td class='pass'>Pass</td>");
        int fails = countInstancesOf("<td class='fail'>Fail</td>");
        replaceInFile("STEPSPERFORMED", Integer.toString(fails + passes));
        replaceInFile("STEPSPASSED", Integer.toString(passes));
        replaceInFile("STEPSFAILED", Integer.toString(fails));
        if (fails == 0 && errors == 0 && testStatus == 1) {
            replaceInFile(PASSORFAIL, "<font size='+2' class='pass'><b>SUCCESS</b></font>");
        } else if (fails == 0 && errors == 0) {
            replaceInFile(PASSORFAIL, "<font size='+2' class='warning'><b>" + Result.values()[testStatus] + "</b></font>");
        } else {
            replaceInFile(PASSORFAIL, "<font size='+2' class='fail'><b>FAILURE</b></font>");
        }
        // record the time
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        String timeNow = stf.format(new Date());
        long totalTime = (new Date()).getTime() - startTime;
        long time = totalTime / 1000;
        StringBuilder seconds = new StringBuilder(Integer.toString((int) (time % 60)));
        StringBuilder minutes = new StringBuilder(Integer.toString((int) ((time % 3600) / 60)));
        StringBuilder hours = new StringBuilder(Integer.toString((int) (time / 3600)));
        for (int i = 0; i < 2; i++) {
            if (seconds.length() < 2) {
                seconds.insert(0, "0");
            }
            if (minutes.length() < 2) {
                minutes.insert(0, "0");
            }
            if (hours.length() < 2) {
                hours.insert(0, "0");
            }
        }
        replaceInFile("RUNTIME", hours + ":" + minutes + ":" + seconds);
        replaceInFile("TIMEFINISHED", timeNow);
        if (System.getProperty("packageResults") != null && "true".equals(System.getProperty("packageResults"))) {
            packageTestResults();
        }
    }

    /**
     * Generates the HTML friendly link for the image
     *
     * @param imageName the name of the image being embedded
     * @return String: the link for the image which can be written out to the
     * html file
     */
    private String generateImageLink(String imageName) {
        String imageLink = "<br/>";
        if (imageName.length() >= directory.length() + 1) {
            imageLink += "<a href='javascript:void(0)' onclick='toggleImage(\"" +
                    imageName.substring(directory.length() + 1) + "\")'>Toggle Screenshot Thumbnail</a>";
            imageLink += " <a href='javascript:void(0)' onclick='displayImage(\"" +
                    imageName.substring(directory.length() + 1) + "\")'>View Screenshot Fullscreen</a>";
            imageLink += "<br/><img id='" + imageName.substring(directory.length() + 1) + "' border='1px' src='" +
                    imageName.substring(directory.length() + 1) + "' width='" + EMBEDDED_IMAGE_WIDTH +
                    "px' style='display:none;'>";
        } else {
            imageLink += "<b><font class='fail'>No Image Preview</font></b>";
        }
        return imageLink;
    }

    /**
     * Generates a unique image name
     *
     * @return String: the name of the image file as a PNG
     */
    private String generateImageName() {
        long timeInSeconds = new Date().getTime();
        String randomChars = getRandomString(10);
        return directory + "/" + timeInSeconds + "_" + randomChars + ".png";
    }

    /**
     * Retrieves the current error count of the test
     *
     * @return Integer: the number of errors current encountered on the current
     * test
     */
    public int getErrors() {
        return errors;
    }

    /**
     * Packages the test result reporter along with screenshots into a zip file
     */
    private void packageTestResults() {
        File f = new File(directory, filename + "_RESULTS.zip");
        try (// Create new zip outputfile
             ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f))) {

            // Add html results to zip outputfile
            ZipEntry e = new ZipEntry(filename);
            out.putNextEntry(e);
            Path path = FileSystems.getDefault().getPath(directory, filename);
            byte[] data = Files.readAllBytes(path);
            out.write(data, 0, data.length);
            out.closeEntry();

            // Add screenshots to zip outputfile
            for (String screenshot : screenshots) {
                ZipEntry s = new ZipEntry(screenshot.replaceAll(".*\\/", ""));
                out.putNextEntry(s);
                Path screenPath = FileSystems.getDefault().getPath(screenshot);
                byte[] screenData = Files.readAllBytes(screenPath);
                out.write(screenData, 0, screenData.length);
                out.closeEntry();
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void pass(String action, String expectedResult, String actualResult) {
        recordAction(action, expectedResult, actualResult, Result.SUCCESS);
        scenario.write("Pass. " + getScenarioWriteString(expectedResult, actualResult));
    }

    public void pass(String actualResult) {
        recordActual(actualResult, Success.PASS);
        scenario.write("Success: " + getScenarioWriteString("", actualResult));
    }

    public void warn(String action, String expectedResult, String actualResult) {
        recordAction(action, expectedResult, actualResult, Result.WARNING);
        scenario.write("Warn. " + getScenarioWriteString(expectedResult, actualResult));
    }

    public void fail(String action, String expectedResult, String actualResult) {
        recordAction(action, expectedResult, actualResult, Result.FAILURE);
        scenario.write("Fail. Expected: " + getScenarioWriteString(expectedResult, actualResult));
        Assert.fail(getScenarioWriteString(expectedResult, actualResult));
    }

    public void fail(String actualResult) {
        recordActual(actualResult, Success.FAIL);
        scenario.write("Fail: " + getScenarioWriteString("", actualResult));
        Assert.fail(getScenarioWriteString("", actualResult));
    }

    public void apiFail(String action, String expectedResult, String actualResult) {
        recordAction(action, expectedResult, actualResult, Result.APIFAILURE);
        scenario.write("APIFail. " + getScenarioWriteString(expectedResult, actualResult));
        Assert.fail(getScenarioWriteString(expectedResult, actualResult));
    }

    private String getScenarioWriteString(String expectedResult, String actualResult) {
        return "Expected: " + expectedResult + "\nActual: " + actualResult + "\n";
    }

    /**
     * Writes an action that was performed out to the output reporter. If the action
     * is considered a failure, and a 'real' browser is being used (not NONE or
     * HTMLUNIT), then a screenshot will automatically be taken
     *
     * @param action         - the step that was performed
     * @param expectedResult - the result that was expected to occur
     * @param actualResult   - the result that actually occurred
     * @param result         - the result of the action
     */
    private void recordAction(String action, String expectedResult, String actualResult, Result result) {
        stepNum++;
        String success = "Check";
        String imageLink = "";
        if (result == Result.SUCCESS) {
            success = "Pass";
        }
        if (result == Result.FAILURE || result == Result.APIFAILURE) {
            success = "Fail";
        }
        if (screenshotNeeded(result, success)) {
            // get a screen shot of the action
            imageLink = captureEntirePageScreenshot();
        }
        // determine time differences
        Date currentTime = new Date();
        long dTime = currentTime.getTime() - lastTime;
        long tTime = currentTime.getTime() - startTime;
        lastTime = currentTime.getTime();
        try (
                // Reopen outputfile
                FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw)) {
            // record the action
            out.write(START_ROW);
            out.write("    <td align='center'>" + stepNum + ".</td>\n");
            out.write(START_CELL + action + END_CELL);
            out.write(START_CELL + expectedResult + END_CELL);
            out.write("    <td class='" + result.toString().toLowerCase() + "'>" + actualResult + imageLink + END_CELL);
            out.write(START_CELL + dTime + "ms / " + tTime + "ms</td>\n");
            out.write("    <td class='" + success.toLowerCase() + "'>" + success + END_CELL);
            out.write(END_ROW);
        } catch (IOException e) {
            log.error(e);
        }
    }

    private boolean screenshotNeeded(Result result, String success) {
        return !"Pass".equals(success) && device != Device.HTMLUNIT && result != Result.APIFAILURE &&
                Property.takeScreenshot() && !this.labels.contains("api");
    }

    /**
     * Writes to the output reporter the actual outcome of an event. A screenshot is
     * automatically taken to provide tracability for and proof of success or
     * failure. This method should only be used after first writing the expected
     * result, using the recordExpected method.
     *
     * @param actualOutcome - what the actual outcome was
     * @param result        - whether this result is a pass or a failure
     */
    private void recordActual(String actualOutcome, Success result) {
        try (
                // reopen the log outputfile
                FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw)) {
            // get a screen shot of the action
            String imageLink = captureEntirePageScreenshot();
            // determine time differences
            Date currentTime = new Date();
            long dTime = currentTime.getTime() - lastTime;
            long tTime = currentTime.getTime() - startTime;
            lastTime = currentTime.getTime();
            // write out the actual outcome
            out.write(START_CELL + actualOutcome + imageLink + END_CELL);
            out.write(START_CELL + dTime + "ms / " + tTime + "ms</td>\n");
            // write out the pass or fail result
            if (result == Success.PASS) {
                out.write("    <td class='pass'>Pass</td>\n");
            } else {
                out.write("    <td class='fail'>Fail</td>\n");
            }
            // end the row
            out.write(END_ROW);
        } catch (IOException e) {
            log.error(e);
        }
    }

    ///////////////////////////////////////////////////////////////////
    // this enum will be for a pass/fail
    ///////////////////////////////////////////////////////////////////

    /**
     * Writes to the output reporter the expected outcome of an event. This method
     * should always be followed the recordActual method to record what actually
     * happened.
     *
     * @param expectedOutcome - what the expected outcome is
     */
    public void recordExpected(String expectedOutcome) {
        stepNum++;

        try (
                // reopen the log output file
                FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw)) {
            // start the row
            out.write(START_ROW);
            // log the step number
            out.write("    <td align='center'>" + stepNum + ".</td>\n");
            // leave the step blank as this is simply a check
            out.write("    <td> </td>\n");
            // write out the expected outcome
            out.write(START_CELL + expectedOutcome + END_CELL);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Replaces an occurrence of a string within a file
     *
     * @param oldText - the text to be replaced
     * @param newText - the text to be replaced with
     */
    private void replaceInFile(String oldText, String newText) {
        StringBuilder oldContent = new StringBuilder();

        try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                oldContent.append(line).append("\r\n");
            }
        } catch (IOException e) {
            log.error(e);
        }

        // replace a word in a outputfile
        String newContent = oldContent.toString().replaceAll(oldText, newText);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(newContent);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Sets the App class which controls all actions within the browser
     *
     * @param app - the application to be tested, contains all control elements
     */
    public void setApp(App app) {
        this.app = app;
    }

    /**
     * Determines the current time and sets the 'last time' to the current time
     */
    private void setStartTime() {
        startTime = (new Date()).getTime();
        lastTime = startTime;
    }

    /**
     * Creates the directory and reporter to hold the test output file
     */
    private void setupFile() {
        if (!new File(directory).exists()) {
            new File(directory).mkdirs();
        }
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create output file");
                }
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    /**
     * Gives status for each test step
     *
     * @author Max Saperstone
     */
    public enum Result {
        WARNING, SUCCESS, FAILURE, SKIPPED, APIFAILURE
    }

    /**
     * Determines if the tests pass or fail
     *
     * @author Max Saperstone
     */
    public enum Success {
        PASS, FAIL
    }
}
