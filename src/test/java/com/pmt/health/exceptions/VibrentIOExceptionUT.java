package com.pmt.health.exceptions;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VibrentIOExceptionUT {
    private VibrentIOException vioe;

    @BeforeClass
    public void setup() {
        vioe = new VibrentIOException("Message", 202, "response");
    }

    @Test
    public void testGetResponseData() {
        try {
            throw vioe;
        } catch (VibrentIOException e) {
            Assert.assertEquals(e.getResponseData(), "response");
        }
    }

    @Test
    public void testGetResponseCode() {
        try {
            throw vioe;
        } catch (VibrentIOException e) {
            Assert.assertEquals(e.getResponseCode(), 202);
        }
    }
}
