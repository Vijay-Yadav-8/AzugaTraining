/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 *This class is used to send emails
 */
public class MailS {
    private static final Logger logger = LogManager.getLogger(MailS.class.getName());

    /**
     * This method is used to send the mail
     * @param msg - it is the body of the mail
     * @param sub - it is the subject of the mail
     * @param to - recipient email address
     * @param from - sender email address
     * @param file - it is the file which can be attached along with body of the mail
     * @return - returns 1 if mail is sent successfully else returns -1
     */
    int sendEmail(String msg, String sub, String[] to, String from, File file) {
        long start = System.currentTimeMillis();
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        logger.debug("system properties" + properties);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        logger.debug("system properties after changing" + properties);
        Session session = Session.getInstance(properties, new Authenticator() {
            /**
             * this method authenticates with Google server
             * @return authentication state
             */
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("www.vijay8452992328@gmail.com", "bgmhozdmcqzcvccs");
            }
        });

        logger.debug("Mail sending from {}",from);
        logger.debug("Mail body is {}",msg);
        logger.debug("Mail Subject is {}",sub);
        logger.debug("File attached in mail is {}",file.getName());
        for (String s : to) {
            MimeMessage message = new MimeMessage(session);

            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            try {
                message.setFrom(from);
                message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(s)));
                logger.debug("Mail sending to {}",s);
                message.setSubject(sub);
                message.setText(msg);
                mimeBodyPart.attachFile(file);
                mimeMultipart.addBodyPart(mimeBodyPart);
                message.setContent(mimeMultipart);
                Transport.send(message);
            } catch (MessagingException e) {
                logger.error("{} occurred while trying to send the mail {}", e, s);
                return -1;
            } catch (IOException e) {
                logger.error("{} occurred while reading file with name {}", e, file.getName());
                return -1;
            }
        }
        long end = System.currentTimeMillis();
        logger.info("hello sendEmail() is executed in {} ms", (end - start));
        return 1;
    }
}
