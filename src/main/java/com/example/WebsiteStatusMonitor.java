package com.example;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.*;
import javax.mail.internet.*;

public class WebsiteStatusMonitor {
    public void startMonitoring() {
        // Prompt the user to enter the website URL to monitor (or use the default)
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the website URL to monitor (or press Enter to use the default):");
        String websiteURL = scanner.nextLine();
        scanner.close();

        // Set the default website URL if the user didn't provide any input
        if (websiteURL.isEmpty()) {
            websiteURL = "https://leetcode.com/explore/interview/card/facebook/";
        }

        while (true) {
            try {
                URL url = new URL(websiteURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    System.out.println(websiteURL + " is back online!");
                    sendEmail(); // Send email when website is back online
                    break;
                } else {
                    System.out.println(websiteURL + " seems down... waiting for it to come back online.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                Thread.sleep(60000); // Wait for 60 seconds before checking again
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
        // Retrieve AWS access key ID, secret access key, and region from environment
        // variables
        String awsAccessKeyId = System.getenv("AWS_ACCESS_KEY_ID");
        String awsSecretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String awsRegion = System.getenv("AWS_REGION");

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

            Transport.send(message); // Send the email

            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
