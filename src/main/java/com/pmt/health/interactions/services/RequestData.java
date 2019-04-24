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

import java.util.HashMap;
import java.util.Map;

/**
 * A class designed to hold data needed to provide to the HTTP calls.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/11/2017
 */
public class RequestData {
    private JsonElement json = null;
    private Map<String, String> multipartData = null;
    private Map<String, String> params = null;
    private Map headers = new HashMap<>();

    public RequestData() {
        // constructor needs to be empty for pico factory
    }

    public Map<String, String> getMultipartData() {
        return multipartData;
    }

    public void setMultipartData(Map<String, String> multipartData) {
        this.multipartData = multipartData;
    }

    public JsonElement getJSON() {
        return json;
    }

    public void setJSON(JsonElement json) {
        this.json = json;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map getHeaders() {
        return headers;
    }

    public void setHeaders(Map headers) {
        this.headers = headers;
    }
}
