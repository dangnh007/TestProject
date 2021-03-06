/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmt.health.exceptions;

/**
 * @author Matt Grasberger <matthew.grasberger@coveros.com>
 */
public class FormNotPresentException extends RuntimeException {

    /**
     * Creates a new instance of <code>FormNotPresentException</code> without
     * detail message.
     */
    public FormNotPresentException() {
    }

    /**
     * Constructs an instance of <code>FormNotPresentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FormNotPresentException(String msg) {
        super(msg);
    }
}
