/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmt.health.exceptions;

/**
 * @author Jeff Voight <jeff.voight@coveros.com>
 */
public class VibrentException extends Exception {

    /**
     * Creates a new instance of <code>VibrentException</code> without detail
     * message.
     */
    public VibrentException() {
    }

    /**
     * Constructs an instance of <code>VibrentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VibrentException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>VibrentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     * @param e   The exception that caused this exception
     */
    public VibrentException(String msg, Exception e) {
        super(msg, e);
    }

}
