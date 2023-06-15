package com.example;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class WebsiteStatusMonitor {
    public static void main(String[] args) {
        while (true) {
            try {
                URL url = new URL("https://leetcode.com/explore/interview/card/facebook/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    System.out.println("Leetcode website is back online!");
                    sendEmail(); // Trigger email notification when website is back online
                    break;
                } else {
                    System.out.println("Leetcode website seems down... waiting for it to come back online.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            try {
                Thread.sleep(60000); // Wait for 60 seconds before checking the website status again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends an email notification using JavaMail API and Amazon SES.
     */
    private static void sendEmail() {
        String recipientEmail = "email@example.com";
        String senderEmail = "ses.gaetanobarreca.dev";
        String awsAccessKeyId = System.getenv("AWS_ACCESS_KEY_ID");
        String awsSecretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String awsRegion = "your-aws-region"; // Replace with your AWS region

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "aws");
        properties.put("mail.aws.user", awsAccessKeyId);
        properties.put("mail.aws.password", awsSecretAccessKey);
        properties.put("mail.aws.region", awsRegion);

        Session session = Session.getInstance(properties);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Website Status Alert");
            message.setText("The website is back online!");

            Transport.send(message); // Send the email using JavaMail API and Amazon SES

            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
