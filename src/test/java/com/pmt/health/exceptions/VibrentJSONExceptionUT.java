package com.pmt.health.exceptions;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VibrentJSONExceptionUT {

    VibrentJSONException vjso;

    @BeforeClass
    public void setup() {
        vjso = new VibrentJSONException("Message", 202, "response");
    }

    @Test
    public void testGetUnexpectedJSON() {
        try {
            throw vjso;
        } catch (VibrentJSONException e) {
            Assert.assertEquals(e.getUnexpectedJSON(), "response");
        }
    }

    @Test
    public void testGetStatusCode() {
        try {
            throw vjso;
        } catch (VibrentJSONException e) {
            Assert.assertEquals(e.getStatusCode(), 202);
        }
    }
}
