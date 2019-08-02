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

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.pmt.health.exceptions.EndpointException;
import com.pmt.health.exceptions.VibrentIOException;
import com.pmt.health.interactions.services.Response.ResponseData;
import com.pmt.health.steps.Configuration;
import com.pmt.health.utilities.Property;
import com.pmt.health.utilities.Reporter;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.testng.log4testng.Logger;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * A class designed to make HTTP calls. This is wrapped by the Action and Assert
 * classes to ensure calls are properly written to logs, and data can be easily
 * accessed
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class HTTP {

    private static final Logger log = Logger.getLogger(HTTP.class);
    private static final String BOUNDARY = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
    private static final String NEWLINE = "\r\n";

    private static final String GET = "GET";
    private static final String PATCH = "PATCH";
    private static final String DELETE = "DELETE";
    private static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE = "Content-Type";

    private final String serviceBaseUrl;
    private String user = "";
    private String pass = "";
    private Map<String, String> extraHeaders = new HashMap<>();

    private String SESSION = null;

    private Reporter reporter;

    /**
     * Instantiates a HTTP session for making web service calls without any
     * authentication
     *
     * @param serviceBaseUrl - the base url of the services location
     */
    public HTTP(String serviceBaseUrl) {
        this.serviceBaseUrl = serviceBaseUrl;
        addMaintenanceCookie();
    }

    /**
     * Instantiates a HTTP session for making web service calls without any
     * authentication
     *
     * @param serviceBaseUrl - the base url of the services location
     */
    public HTTP(String serviceBaseUrl, Reporter reporter) {
        this.serviceBaseUrl = serviceBaseUrl;
        this.reporter = reporter;
        addMaintenanceCookie();
    }

    /**
     * Instantiates a HTTP session for making web service calls with basic
     * username/password authentication
     *
     * @param serviceBaseUrl - the base url of the services location
     * @param user           - the username required for authentication
     * @param pass           - the password required for authentication
     */
    public HTTP(String serviceBaseUrl, String user, String pass) {
        this.serviceBaseUrl = serviceBaseUrl;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Instantiates a HTTP session for making web service calls with basic
     * username/password authentication
     *
     * @param serviceBaseUrl - the base url of the services location
     * @param user           - the username required for authentication
     * @param pass           - the password required for authentication
     * @param reporter       - the reporter for recording actions
     */
    public HTTP(String serviceBaseUrl, String user, String pass, Reporter reporter) {
        this.serviceBaseUrl = serviceBaseUrl;
        this.user = user;
        this.pass = pass;
        this.reporter = reporter;
    }

    public void addHeaders(Map<String, String> headers) {
        this.extraHeaders = headers;
    }

    public void resetHeaders() {
        this.extraHeaders = new HashMap<>();
    }

    private void addMaintenanceCookie() {
        if (Property.maintenanceMode()) {
            Map<String, String> cookie = new HashMap<>();
            cookie.put("Cookie", Property.getMaintenanceHeader());
            addHeaders(cookie);
        }
    }

    /**
     * Helper method to prepare url request params
     *
     * @param requestData - contains params for requests
     * @return parameters for url request
     */
    private StringBuilder prepareParams(RequestData requestData) throws UnsupportedEncodingException {
        StringBuilder params = new StringBuilder();
        if (requestData != null && requestData.getParams() != null) {
            params.append("?");
            for (String key : requestData.getParams().keySet()) {
                params.append(key);
                params.append("=");
                String param = getValueString(requestData, key);
                params.append(param);
                params.append("&");
            }
            params.setLength(params.length() - 1);
        }
        return params;
    }

    private String getValueString(RequestData requestData, String key) throws UnsupportedEncodingException {
        String param = "";
        if (APPLICATION_X_WWW_FORM_URLENCODED.equals(extraHeaders.get(CONTENT_TYPE))) {
            param = URLEncoder.encode(requestData.getParams().get(key), "UTF-8");
        } else {
            param = requestData.getParams().get(key);
        }
        return param;
    }

    private HttpURLConnection establishHttpUrlConnection(String call, String service, StringBuilder params) throws IOException {
        HttpURLConnection connection = null;
        URL url = getUrl(service, params);
        connection = getConnection(url);
        // ensure that we trust all SSL certs
        SSLUtilities.trustAllHostnames();
        SSLUtilities.trustAllHttpsCertificates();

        // setup the connection
        setRequestMethod(connection, call);
        setHeaders(connection);

        // configure connection
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setAllowUserInteraction(false);

        setCredentials(connection);
        setSESSIONToken(connection, SESSION != null, SESSION);
        return connection;
    }

    /**
     * A basic generic http call with basic json input
     *
     * @param call        - what method are we calling
     * @param service     - the endpoint of the service under test
     * @param requestData - the parameters to be passed to the endpoint for the service
     *                    call
     * @return Response: the response provided from the http call
     */
    private Response simpleCall(String call, String service, RequestData requestData, ResponseData expectedResponseData, String filePrefix) throws IOException {
        StringBuilder params = prepareParams(requestData);
        HttpURLConnection connection = null;
        try {
            connection = establishHttpUrlConnection(call, service, params);
            connection.setRequestProperty(CONTENT_TYPE, "application/json; charset=UTF-8");
            if (requestData != null && requestData.getJSON() != null) {
                try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream())) {
                    wr.write(requestData.getJSON().toString());
                    wr.flush();
                }
            } else {
                connection.connect();
            }
            return getResponse(connection, call, service, requestData, expectedResponseData, filePrefix);
        } finally {
            disconnect(connection);
        }
    }

    /**
     * A basic generic http for multipart form-data as input
     *
     * @param call        - what method are we calling
     * @param service     - the endpoint of the service under test
     * @param requestData - the parameters to be passed to the endpoint for the service
     *                    call
     * @return Response: the response provided from the http call
     */
    private Response call(String call, String service, RequestData requestData, File file, ResponseData expectedResponseData, String filePrefix) throws IOException {
        StringBuilder params = prepareParams(requestData);
        HttpURLConnection connection = null;
        try {
            connection = establishHttpUrlConnection(call, service, params);
            writeFormURLEncodedToBody(params, connection);
            // TODO: Optimize this. Determine what time works best
            connection.connect();
            writeDataRequest(connection, requestData, file);
            return getResponse(connection, call, service, requestData, expectedResponseData, filePrefix);
        } finally {
            disconnect(connection);
        }
    }

    private void disconnect(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    private void writeFormURLEncodedToBody(StringBuilder params, HttpURLConnection connection) throws IOException {
        if (isXWwwFormUrlencoded(connection)) {
            byte[] postData = params.substring(1).getBytes(StandardCharsets.UTF_8);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }
        }
    }

    private void setSESSIONToken(HttpURLConnection connection, boolean isSESSIONNotNull, String session) {
        if (isSESSIONNotNull) {
            connection.addRequestProperty("Cookie", "SESSION=" + session);
        }
    }

    private void setCredentials(HttpURLConnection connection) {
        if (useCredentials()) {
            String userpass = user + ":" + pass;
            String encoding = new String(Base64.encodeBase64(userpass.getBytes()));
            connection.setRequestProperty("Authorization", "Basic " + encoding);
        }
    }

    private void setHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, String> entry : extraHeaders.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        if (!isXWwwFormUrlencoded(connection) && connection.getRequestProperty("Content-length") == null) {
            connection.setRequestProperty("Content-length", "0");
        }
        if (connection.getRequestProperty(CONTENT_TYPE) == null) {
            connection.setRequestProperty(CONTENT_TYPE, "multipart/form-data; boundary=" + BOUNDARY);
        }
        if (connection.getRequestProperty("Accept") == null) {
            connection.setRequestProperty("Accept", "*/*");
        }
    }

    private void setRequestMethod(HttpURLConnection connection, String method) throws ProtocolException {
        if (PATCH.equals(method)) {
            connection.setRequestProperty("X-HTTP-Method-Override", PATCH);
            method = "POST";
        }
        connection.setRequestMethod(method);
    }

    private HttpURLConnection getConnection(URL url) throws IOException {
        Proxy proxy = Proxy.NO_PROXY;
        if (System.getProperty("proxy") != null && !Boolean.getBoolean("api.proxy.skip")) {
            String setProxy = System.getProperty("proxy");
            String proxyIP = setProxy.split(":")[0];
            String proxyPort = setProxy.split(":")[1];
            SocketAddress addr = new InetSocketAddress(proxyIP, Integer.parseInt(proxyPort));
            proxy = new Proxy(Proxy.Type.HTTP, addr);
        }
        return (HttpURLConnection) url.openConnection(proxy);
    }

    private URL getUrl(String service, StringBuilder params) throws MalformedURLException {
        URL url;
        if (APPLICATION_X_WWW_FORM_URLENCODED.equals(extraHeaders.get(CONTENT_TYPE))) {
            url = new URL(this.serviceBaseUrl + service);
        } else {
            url = new URL(this.serviceBaseUrl + service + params.toString());
        }
        return url;
    }

    /**
     * A basic http delete call
     *
     * @param service - the endpoint of the service under test
     * @return Response: the response provided from the http call
     */
    public Response simpleDelete(String service) throws IOException {
        return simpleCall(DELETE, service, null, ResponseData.JSON, null);
    }

    public Response delete(String service) throws IOException {
        return call(DELETE, service, null, null, ResponseData.JSON, null);
    }

    /**
     * A basic http delete call
     *
     * @param service     - the endpoint of the service under test
     * @param requestData - the parameters to be passed to the endpoint for the service
     *                    call
     * @return Response: the response provided from the http call
     */
    public Response simpleDelete(String service, RequestData requestData) throws IOException {
        return simpleCall(DELETE, service, requestData, ResponseData.JSON, null);
    }

    public Response delete(String service, RequestData requestData) throws IOException {
        return call(DELETE, service, requestData, null, ResponseData.JSON, null);
    }

    /**
     * A basic http get call
     *
     * @param service - the endpoint of the service under test
     * @return Response: the response provided from the http call
     */
    public Response simpleGet(String service) throws IOException {
        return simpleCall(GET, service, null, ResponseData.JSON, null);
    }

    public Response get(String service) throws IOException {
        return call(GET, service, null, null, ResponseData.JSON, null);
    }

    /**
     * A basic http get call
     *
     * @param service     - the endpoint of the service under test
     * @param requestData - the parameters to be passed to the endpoint for the service
     *                    call
     * @return Response: the response provided from the http call
     */
    public Response simpleGet(String service, RequestData requestData) throws IOException {
        return simpleCall(GET, service, requestData, ResponseData.JSON, null);
    }

    public Response get(String service, RequestData requestData) throws IOException {
        return call(GET, service, requestData, null, ResponseData.JSON, null);
    }

    /**
     * A basic http get call retrieving a file, instead of JSON
     *
     * @param service      - the endpoint of the service under test
     * @param requestData  - the parameters to be passed to the endpoint for the service
     *                     call
     * @param responseData - are we expecting a particular response type
     */
    public Response simpleGet(String service, RequestData requestData, ResponseData responseData, String filePrefix) throws IOException {
        return simpleCall(GET, service, requestData, responseData, filePrefix);
    }

    public Response get(String service, RequestData requestData, ResponseData responseData, String filePrefix) throws IOException {
        return call(GET, service, requestData, null, responseData, filePrefix);
    }

    /**
     * Extracts the response data from the open http connection
     *
     * @param connection  - the open connection of the http call
     * @param call        - what method are we calling
     * @param service     - the url of the call
     * @param requestData - request parameters of the call
     * @return Response: the response provided from the http call
     */
    private Response getResponse(HttpURLConnection connection, String call, String service, RequestData
            requestData, ResponseData expectedResponseData, String filePrefix) throws IOException {
        int status = connection.getResponseCode();
        if (connection.getHeaderFields().containsKey("Set-Cookie")) {
            String setCookie = connection.getHeaderFields().get("Set-Cookie").get(0);
            if (setCookie.contains("SESSION")) {
                setSESSION(setCookie.split(";", 2)[0].split("=")[1]);
            }
        }
        Response response = new Response();
        // TODO: Figure out why this needs to exist.
        response.initialize(status, reporter, call, service, requestData, connection.getHeaderFields());
        if (expectedResponseData == ResponseData.FILE) {
            String fileName = filePrefix + "_" + service.substring(service.lastIndexOf('/') + 1);
            // opens input stream from the HTTP connection
            // opens an output stream to save into
            InputStream inputStream = connection.getInputStream();

            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                int bytesRead = -1;
                byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            response.setData(ResponseData.FILE, fileName);
            response.setStatusCode(status);
        } else {
            String responseData = getHttpResponse(connection, requestData);
            response = captureJsonResponse(responseData);
            response.setStatusCode(status);
        }
        return response;
    }

    private String getHttpResponse(HttpURLConnection connection, RequestData requestData) throws IOException {
        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            // from - https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
            String decodedString;
            while ((decodedString = buff.readLine()) != null) {
                sb.append(decodedString);
            }
            buff.close();
            return sb.toString();
        } catch (IOException ioe) {
            log.info("Switching to HTTP Error Stream", ioe);
            BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            String decodedString;
            while ((decodedString = buff.readLine()) != null) {
                sb.append(decodedString);
            }
            buff.close();
            VibrentIOException vioe = new VibrentIOException(ioe.getMessage(), connection.getResponseCode(), sb.toString());
            vioe.setRequestData(requestData);
            throw vioe;
        }
    }

    /**
     * Extracts the Json object or array from the HTTP response, and puts in the Response object
     *
     * @param responseData - the String containing the raw response data.
     */
    private Response captureJsonResponse(String responseData) {
        Response response = new Response();
        response.setMessage(responseData);
        try {
            JsonParser parser = new JsonParser();
            JsonElement parsedData = parser.parse(responseData);
            response.setData(ResponseData.JSON, parsedData);
        } catch (JsonSyntaxException jse) {
            log.debug("This response is not json.", jse);
            response.setData(ResponseData.RAW, responseData);
        }
        return response;
    }

    /**
     * Retrieves the base url of the services location
     *
     * @return String: the base url of the services location
     */
    public String getServiceBaseUrl() {
        return serviceBaseUrl;
    }

    /**
     * Retrieves the username used for authentication with the application. If
     * none was set, an empty string will be returned
     *
     * @return user: the username required for authentication
     */
    public String getUser() {
        return user;
    }

    /**
     * A basic http patch call
     *
     * @param service     - the endpoint of the service under test
     * @param requestData - the parameters to be passed to the endpoint for the service
     *                    call
     * @return Response: the response provided from the http call
     */
    public Response patch(String service, RequestData requestData) throws IOException {
        return call(PATCH, service, requestData, null, ResponseData.JSON, null);
    }

    /**
     * A basic http post call
     *
     * @param service     - the endpoint of the service under test
     * @param requestData - the parameters to be passed to the endpoint for the service
     *                    call
     * @return Response: the response provided from the http call
     */
    public Response simplePost(String service, RequestData requestData) throws IOException {
        return simpleCall("POST", service, requestData, ResponseData.JSON, null);
    }

    public Response post(String service, RequestData requestData) throws IOException {
        return call("POST", service, requestData, null, ResponseData.JSON, null);
    }

    public Response post(String service, RequestData requestData, File file) throws IOException {
        return call("POST", service, requestData, file, ResponseData.JSON, null);
    }

    /**
     * A basic http put call
     *
     * @param service     - the endpoint of the service under test
     * @param requestData - the parameters to be passed to the endpoint for the service
     *                    call
     * @return Response: the response provided from the http call
     */
    public Response simplePut(String service, RequestData requestData) throws IOException {
        return simpleCall("PUT", service, requestData, ResponseData.JSON, null);
    }

    public Response put(String service, RequestData requestData) throws IOException {
        return call("PUT", service, requestData, null, ResponseData.JSON, null);
    }

    /**
     * Determines whether or not authentication should be used, by checking to
     * see if both username and password are set
     *
     * @return Boolean: are both the username and password set
     */
    private boolean useCredentials() {
        return !this.user.isEmpty() && !this.pass.isEmpty();
    }

    /**
     * Pushes request data to the open http connection
     *
     * @param connection  - the open connection of the http call
     * @param requestData - the parameters to be passed to the endpoint for the service
     *                    call
     */
    private void writeDataRequest(HttpURLConnection connection, RequestData requestData, File file) throws
            IOException {
        if ((!isXWwwFormUrlencoded(connection) && hasDataToWrite(requestData)) || hasFileToWrite(requestData, file)) {
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(NEWLINE);
            if (requestData.getJSON() != null) {
                writeJSONRequest(requestData, wr);
            }
            if (requestData.getMultipartData() != null) {
                writeMultipartRequest(requestData, wr);
            }
            if (file != null && file.exists()) {
                writeFileRequest(file, wr);
            }
            wr.writeBytes(NEWLINE + "--" + BOUNDARY + "--" + NEWLINE);
            wr.flush();
        }
    }

    private void writeFileRequest(File file, DataOutputStream wr) throws IOException {
        wr.writeBytes(NEWLINE + "--" + BOUNDARY + NEWLINE);
        wr.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"");
        wr.writeBytes(NEWLINE);
        wr.writeBytes("Content-Type: " + Files.probeContentType(file.toPath()));
        wr.writeBytes(NEWLINE + NEWLINE);
        byte[] bytes = Files.readAllBytes(file.toPath());
        wr.write(bytes);
    }

    private void writeMultipartRequest(RequestData requestData, DataOutputStream wr) throws IOException {
        for (String key : requestData.getMultipartData().keySet()) {
            wr.writeBytes(NEWLINE + "--" + BOUNDARY + NEWLINE);
            wr.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"");
            wr.writeBytes(NEWLINE + NEWLINE);
            wr.writeBytes(requestData.getMultipartData().get(key));
        }
    }

    private void writeJSONRequest(RequestData requestData, DataOutputStream wr) throws IOException {
        wr.writeBytes(NEWLINE + "--" + BOUNDARY + NEWLINE);
        wr.writeBytes("Content-Disposition: form-data; name=\"data\"");
        wr.writeBytes(NEWLINE + NEWLINE);
        wr.writeBytes(requestData.getJSON().toString());
    }

    private boolean hasFileToWrite(RequestData requestData, File file) {
        return requestData != null && file != null;
    }

    private boolean hasDataToWrite(RequestData requestData) {
        return requestData != null && (requestData.getMultipartData() != null || requestData.getJSON() != null);
    }

    private boolean isXWwwFormUrlencoded(HttpURLConnection connection) {
        return APPLICATION_X_WWW_FORM_URLENCODED.equals(connection.getRequestProperty(CONTENT_TYPE));
    }

    /**
     * converts base32 encoded secret keys to hex and uses the TOTP from RFC 6238 to turn them into
     * 6 digit codes based on the current time
     *
     * @param secretKey - secret key that is used to generate auth token, unique per environment, stored in property
     *                  files
     * @return 6 digit TOTP code
     */
    private static String getTOTPCode(String secretKey) {
        String normalizadBase32Key = secretKey.replace(" ", "").toUpperCase();
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(normalizadBase32Key);
        String hexKey = Hex.encodeHexString(bytes);
        long time = (System.currentTimeMillis() / 1000) / 30;
        String hexTime = Long.toHexString(time);

        return TOTP.generateTOTP(hexKey, hexTime, "6");
    }

    /**
     * Obtains a secret key from a property file, checks if it is same as previous one(they update every 30 secs) and
     * returns it
     *
     * @return secret key
     */
    private static String generateSecretKey() {
        String secretKey = Property.getProgramProperty(Configuration.getEnvironment() + ".admin.auth.token");
        if (secretKey == null) {
            return null;
        }
        // new 6 digit code for a current environment
        return getTOTPCode(secretKey);
    }

    /**
     * public method that can be used outside of this class, return the String with a 6 digit code we need for auth
     * for admins
     *
     * @return Oath2Key
     */
    public static String obtainOath2Key() {
        return generateSecretKey();
    }

    /**
     * Obtains a secret key from UI through the User object, checks if it is same as previous one(they update every 30 secs) and
     * returns it
     *
     * @return secret key
     */
    private static String generateSecretKeyCreatedUser(String secretKey) {
        if (secretKey == null) {
            return null;
        }
        // new 6 digit code for a current environment
        return getTOTPCode(secretKey);
    }

    /**
     * public method that can be used outside of this class, return the String with a 6 digit code we need for auth
     * for users
     *
     * @return Oath2Key
     */
    public static String obtainOath2KeyCreatedUser(String secretKey) {
        return generateSecretKeyCreatedUser(secretKey);
    }

    /**
     * Generates an invalid x-auth token and sets the HTTP to use that token.
     * The token should emulate the structure of a good one, but may be purely random.
     */
    public void setBadSession() {
        if (SESSION != null) { // Gets last 5 digits and increases their ascii value by 1
            int sessionLength = SESSION.length();
            StringBuilder modifiedEnd = new StringBuilder();
            for (int i = sessionLength - 1; i > sessionLength - 6; i--) {
                modifiedEnd.append(((int) SESSION.charAt(i) + 1));
            }
            SESSION = SESSION.substring(0, sessionLength - 6) + modifiedEnd.toString();
        } else {
            SESSION = "badsession";
        }
    }

    // TODO: What is this method doing? It doesn't appear to get anything besides an empty response with a hashmap stashed.
    public Response getWSResponse(String endpoint) throws EndpointException {
        int respCode = 0;
        WebSocketClient client = new WebSocketClient();
        SimpleEchoSocket socket = new SimpleEchoSocket();
        Map<String, String> reqParams = new HashMap<>();
        try {
            client.start();
            URI echoUri = new URI("wss://" + serviceBaseUrl.split("://")[1] + endpoint);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            Future<org.eclipse.jetty.websocket.api.Session> connection = client.connect(socket, echoUri, request);
            respCode = connection.get().getUpgradeResponse().getStatusCode();
            reqParams = convertParams(connection.get().getUpgradeRequest().getHeaders());
            // wait for closed socket connection.
            socket.awaitClose();
        } catch (Exception e) {
            log.info(e);
            throw new EndpointException(e.getMessage());
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                log.error(e);
            }
        }
        Response resp = new Response();
        RequestData requestData = new RequestData();
        requestData.setParams(reqParams);
        resp.initialize(respCode, this.reporter, "", "", requestData, new HashMap());
        return resp;
    }

    private Map<String, String> convertParams(Map<String, List<String>> toConvert) {
        Map<String, String> converted = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : toConvert.entrySet()) {
            converted.put(entry.getKey(), entry.getValue().toString());
        }
        return converted;
    }

    public String getSESSION() {
        return SESSION;
    }

    public void setSESSION(String SESSION) {
        this.SESSION = SESSION;
    }

    /*
    Gets the first active program's ID
     */
    public int getProgramID() throws IOException {
        Response programs = this.get("/api/programs/");
        if (programs.getArrayData().size() == 0) {
            throw new VibrentIOException("api/programs returned an empty array.", programs.getCode(), programs.getMessage());
        }
        return programs.getArrayData().get(0).getAsJsonObject().get("id").getAsInt();
    }
}
