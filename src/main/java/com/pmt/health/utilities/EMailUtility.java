/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmt.health.utilities;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.codec.binary.Base64;
import org.testng.log4testng.Logger;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.SubjectTerm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This utility class will connect to GMail in READ_ONLY mode and retrieve
 * messages for a given user.
 *
 * @author jeff
 */
// https://javaee.github.io/javamail/FAQ.html#gmailauth
// You need to read and understand this. There are GMail Settings that
// can only be done through your browser.
public class EMailUtility {

    String protocol;
    String host;
    String port;
    String userName;
    String password;
    Folder inbox;
    Store store;
    boolean isGmail = false;
    String envString = "dev/";
    String accessKey;
    String secretKey;
    AmazonS3 s3Client;
    String smtpServer;
    String smtpPort;
    Session session;
    Logger log = Logger.getLogger(EMailUtility.class);
    private Properties properties;
    private Pattern emailEnvironmentPattern = Pattern.compile(".*\\+(.*)\\+.*");

    /**
     * Custom constructor
     *
     * @param userName A String containing the user name for GMail Login
     * @param password A String containing the user password for GMail Login
     * @throws NoSuchProviderException if the GMail driver is missing
     * @throws MessagingException      if there is any other error connecting to
     *                                 GMail.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public EMailUtility(String userName, String password) throws MessagingException {
        isGmail = true;
        protocol = "imap";
        host = "imap.gmail.com";
        port = "993";
        this.userName = userName;
        this.password = password;
        properties = getServerProperties(protocol, host, port);
        session = Session.getDefaultInstance(properties);
        store = session.getStore(protocol);
        store.connect(userName, password);
        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
    }

    /**
     * Custom constructor
     *
     * @throws NoSuchProviderException if the GMail driver is missing
     */
    public EMailUtility() {
        Properties p = System.getProperties();

        smtpServer = Property.getProgramProperty("email.smtp.server");
        smtpPort = Property.getProgramProperty("email.smtp.port");

        isGmail = false;
        host = p.getProperty("s3bucket");
        protocol = "https";
        port = "443";
        accessKey = p.getProperty("s3access");
        secretKey = p.getProperty("s3secret");
        final String emailname = p.getProperty("email.username");
        final String emailpassword = p.getProperty("email.password");
        if (isEmpty(accessKey) || isEmpty(secretKey) || isEmpty(host)) {
            log.error("s3access, s3secret, and s3bucket must be set as a -Dparam.");
        }

        properties = new Properties();
        properties.setProperty("mail.smtp.host", smtpServer);
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailname, emailpassword);
            }
        };
        session = Session.getInstance(properties, auth);

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = new AmazonS3Client(awsCreds);
    }

    private boolean isEmpty(String theString) {
        return theString == null || "".equals(theString);
    }

    public void sendEmail(String toAddress, String subject, String messageText) {
        Message msg = new MimeMessage(session);
        try {
            InternetAddress addressFrom = new InternetAddress("testin@vibrenthealthtest.com");
            InternetAddress addressTo = new InternetAddress(toAddress);
            msg.setFrom(addressFrom);
            msg.setRecipient(RecipientType.TO, addressTo);
            msg.setSubject(subject);
            msg.setContent(messageText, "text/plain");
            Transport.send(msg);
        } catch (MessagingException e) {
            log.error("Sending of test email failed.", e);
        }
    }

    /**
     * finalize is necessary because we can't close any connections while this
     * is running.
     *
     * @throws Throwable If GMail is unable to be closed.
     */
    @Override
    @SuppressWarnings({"squid:ObjectFinalizeOverridenCheck", "squid:S00108", "FinalizeDeclaration", "squid:S1166"})
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            if (isGmail) {
                inbox.close(false);
                store.close();
            }
        } catch (MessagingException ignored) {
        }
    }

    /**
     * Sets the server properties
     *
     * @param protocol A String containing the name of the protocol (imap, pop3)
     * @param host     A String containing the mail host
     * @param port     A String containing the port number of the host
     * @return A Properties object containing custom properties from the passed
     * parameters
     */
    private Properties getServerProperties(String protocol, String host, String port) {
        properties = new Properties();
        properties.setProperty(String.format("mail.%s.host", protocol), host);
        properties.setProperty(String.format("mail.%s.port", protocol), port);
        properties
                .setProperty(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
        properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));
        return properties;
    }

    /**
     * Gets all messages in this mailbox.
     *
     * @return An array of Message objects
     */
    public Message[] getNewEmails() {
        if (!isGmail) {
            throw new UnsupportedOperationException("This method is not supported without gmail.");
        }
        Message[] messages = null;
        try {
            int count = inbox.getMessageCount();
            messages = inbox.getMessages(1, count);
        } catch (MessagingException ex) {
            log.error("Could not connect to the message store", ex);
        }
        return messages;
    }

    /**
     * Uses recipient and partial subject line to locate and retrieve messages
     * Example: Message[]
     * m=gmailUtil.recipientSubjectSearchEmails("vibrentqa3+blah1", "Reset Your
     * All of Us Password");
     *
     * @param recipient A String containing the exact recipient address
     * @param subject   A String containing a subject line to partially match
     * @return An array of Message objects.
     */
    public Message[] recipientSubjectSearchEmails(String recipient, String subject) {
        return recipientSubjectSearchEmails(recipient, subject, 20);
    }

    /**
     * Uses recipient and partial subject line to locate and retrieve messages
     *
     * @param recipient     A String containing the exact recipient address
     * @param subject       A String containing a subject line to partially match
     * @param secondsToWait Number of seconds to wait for the mail before giving
     *                      up
     * @return An array of Message objects.
     */
    public Message[] recipientSubjectSearchEmails(String recipient, String subject, int secondsToWait) {
        if (isGmail) {
            return gmailRecipientSubjectSearchEmails(recipient, subject, secondsToWait);
        } else {
            return s3RecipientSubjectSearchEmails(recipient, subject, secondsToWait);
        }
    }

    /**
     * Uses recipient and complete subject line to locate and retrieve messages
     *
     * @param recipient     A String containing the exact recipient address
     * @param subject       A String containing a subject line to partially match
     * @param secondsToWait Number of seconds to wait for the mail before giving
     *                      up
     * @return An array of Message objects.
     */
    @SuppressWarnings("squid:S2142")
    private Message[] s3RecipientSubjectSearchEmails(String recipient, String subject, int secondsToWait) {
        Message[] messages = new Message[1];
        messages[0] = new MimeMessage(Session.getDefaultInstance(new Properties()));
        envString = getEnvFromRecipient(recipient);
        String hashtext = hashHex(subject, recipient) + ".txt";
        long milliseconds = secondsToWait * (long) 1000;
        long sysmillis = System.currentTimeMillis();
        long timeout = sysmillis + milliseconds;
        try {
            while (messages[0].getAllRecipients() == null && System.currentTimeMillis() < timeout) {
                grabMail(hashtext, messages, recipient, subject, secondsToWait);
            }
        } catch (IOException ioe) {
            log.error("Something went wrong collecting s3 data.", ioe);
        } catch (MessagingException ex) {
            log.error("Something went wrong reconstructing the message", ex);
        }
        return messages;
    }

    @SuppressWarnings({"squid:S1166", "squid:RedundantThrowsDeclarationCheck"})
    private void grabMail(String hashtext, Message[] messages, String recipient, String subject, int secondsToWait) throws SdkClientException, MessagingException, IOException {
        try {
            log.info(host + " : " + envString + hashtext);
            S3Object s3object = s3Client.getObject(new GetObjectRequest(host, envString + hashtext));
            log.info("Found matching email. Content-Type: " + s3object.getObjectMetadata().getContentType());
            messages[0].addRecipient(RecipientType.TO, new InternetAddress(recipient));
            messages[0].setSubject(subject);
            messages[0].setText(getWebContent(s3object.getObjectContent()));
        } catch (AmazonS3Exception s3e) {
            snooze(secondsToWait);
        }
    }

    /**
     * Sleeps for a specified time.
     *
     * @param secondsToWait
     */
    @SuppressWarnings("squid:S2142")
    private void snooze(int secondsToWait) {
        try {
            Thread.sleep((secondsToWait * 1000) / 5);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(EMailUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieves the environment from the email address
     *
     * @param recipient The email address to use
     * @return String containing the environment
     */
    private String getEnvFromRecipient(String recipient) {
        Matcher m = this.emailEnvironmentPattern.matcher(recipient);
        if (m.find()) {
            return m.group(1).toUpperCase() + "/";
        } else {
            return "dev/".toUpperCase();
        }
    }

    /**
     * Retrieves the content of the InputStream and returns it as a String
     *
     * @param stream InputStream from which to read
     * @return String containing the text of the input stream
     * @throws IOException
     */
    private String getWebContent(InputStream stream) throws IOException {
        StringBuilder returnString = new StringBuilder();
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));

        while ((line = in.readLine()) != null) {
            returnString.append(line).append("\n");
        }
        in.close();

        return returnString.toString();
    }

    /**
     * Uses SHA1 algorithm to make a cross-platform hash of the email subject
     * and To addressee
     *
     * @param emailSubject The subject line to hash
     * @param emailTo      The email address to hash
     * @return A base64 encoded representation of the hash
     */
    private String hashHex(String emailSubject, String emailTo) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(EMailUtility.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        log.info("Subject: " + emailSubject);
        log.info("To: " + emailTo);
        messageDigest.update((emailSubject.trim() + emailTo.trim()).getBytes());
        String encryptedString = new String(messageDigest.digest());
        return Base64.encodeBase64URLSafeString(encryptedString.getBytes());
    }

    @SuppressWarnings("SleepWhileInLoop")
    private Message[] gmailRecipientSubjectSearchEmails(String recipient, String subject, int secondsToWait) {
        Message[] messages = null;
        long milliseconds = secondsToWait * (long) 1000;
        long sysmillis = System.currentTimeMillis();
        long timeout = sysmillis + milliseconds;
        while ((messages == null || messages.length < 1) && System.currentTimeMillis() < timeout) {
            try {
                SubjectTerm subTerm = new SubjectTerm(subject);
                RecipientStringTerm recipTerm = new RecipientStringTerm(RecipientType.TO, recipient);
                AndTerm andTerm = new AndTerm(subTerm, recipTerm);
                inbox.close();
                inbox.open(Folder.READ_ONLY);
                messages = inbox.search(andTerm);
            } catch (MessagingException ex) {
                log.error("EMailUtility encountered an error searching recipient/subject", ex);
            }
            if (messages == null || messages.length == 0) {
                try {
                    Thread.sleep(milliseconds / 5);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(EMailUtility.class.getName()).log(Level.SEVERE, null, ex);
                    Thread.currentThread().interrupt();
                }
            }
        }
        return messages;
    }

    /**
     * Retrieves all messages using specific parameters instead of defaults
     *
     * @param protocol A String containing the protocol to use (imap/pop3)
     * @param host     A String containing the hostname of the gmail server
     * @param port     A String containing the port number of the gmail server
     * @param userName A String containing the user name of the gmail user
     * @param password A String containing the password of the gmail user
     * @return An array of Message objects
     * @throws MessagingException If an error occurs getting the
     *                                       messages
     */
    public Message[] getNewEmails(String protocol, String host, String port, String userName,
                                  String password) throws MessagingException {
        Properties p = getServerProperties(protocol, host, port);
        session = Session.getDefaultInstance(p);
        store = session.getStore(protocol);
        store.connect(userName, password);
        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = null;
        try {
            int count = inbox.getMessageCount();
            messages = inbox.getMessages(1, count);
        } catch (MessagingException ex) {
            log.error("Could not connect to the message store", ex);
        }
        return messages;
    }
}
