/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -vijayyv.
 */

package com.azuga.training.week4;


import java.io.File;
import java.io.IOException;

/**
 * This class is used to call all the methods required to call methods of mail class
 */

public class MailSender {
    public static void main(String[] args) throws IOException {
        MailS mailSend = new MailS();
        String from = "www.vijay8452992328@gmail.com";
        String[] toAll={"vijayyv@azuga.com"};/*"sudharshan@codeops.tech","krupa@codeops.tech","adarshs@azuga.com",
                "aparajitam@azuga.com","ashoop@azuga.com","dushyants@azuga.com","indukurimr@azuga.com",
                "kartiks@azuga.com", "lokanathk@azuga.com","pruthvikp@azuga.com","rajatt@azuga.com",
                "rishabh@azuga.com","satvikm@azuga.com","suryaps@azuga.com"};*/
        String msg = "Here is the zip file containing the reports for all the converted files extracted from the museum api";
        String sub = "Sending Csv Generated Reports";
        File file = new File("/Users/azuga/Desktop/b.zip");
        if (mailSend.sendEmail(msg, sub, toAll, from,file) == -1) {
            System.out.println("error occurred");
        }
    }
}
