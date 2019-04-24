/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmt.health.exceptions;

/**
 * @author Jeff Voight <jeff.voight@coveros.com>
 */
public class PageNotFoundException extends VibrentException {

    /**
     * Creates a new instance of <code>PageNotFoundException</code> without
     * detail message.
     */
    public PageNotFoundException() {
    }

    /**
     * Constructs an instance of <code>PageNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PageNotFoundException(String msg) {
        super(msg);
    }
}
