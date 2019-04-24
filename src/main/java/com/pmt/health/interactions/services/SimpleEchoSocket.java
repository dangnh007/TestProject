package com.pmt.health.interactions.services;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.testng.log4testng.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Basic Echo Client Socket
 */
@WebSocket(maxTextMessageSize = 64 * 1024)
class SimpleEchoSocket {
    private final CountDownLatch closeLatch;
    private Logger log = Logger.getLogger(SimpleEchoSocket.class);
    @SuppressWarnings("unused")
    private Session session;

    SimpleEchoSocket() {
        this.closeLatch = new CountDownLatch(1);
    }

    void awaitClose() throws InterruptedException {
        if (!this.closeLatch.await(5, TimeUnit.SECONDS)) {
            log.info("CloseLatch returned false.");
        }
    }
}
