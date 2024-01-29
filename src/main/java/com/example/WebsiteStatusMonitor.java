package com.example;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.*;
import javax.mail.internet.*;

public class WebsiteStatusMonitor {
    private static boolean isWebsiteDown = false; // Flag to track if the website is down

    public void startMonitoring() {
        // Prompt the user to enter the website URL or name to monitor (or use the default)
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the website URL or name to monitor (or press Enter to use the default): ");
        String input = scanner.nextLine();
        scanner.close();

        // Determine the website URL based on user input
        String websiteURL = determineWebsiteURL(input);

        while (true) {
            try {
                URL url = new URL(websiteURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    if (isWebsiteDown) {
                        sendEmail(); // Send email when the website is back online, and the user was waiting
                        isWebsiteDown = false;
                    }
                    System.out.println(websiteURL + " is online!");
                    break;
                } else {
                    System.out.println(websiteURL + " seems down... waiting for it to come back online.");
                    checkAndNotifyAWSCredentials(); // Check AWS credentials only when the website is down
                    waitAndContinue(60); // Wait for 60 seconds before checking again
                    isWebsiteDown = true;
                }
            } catch (java.net.UnknownHostException e) {
                System.out.println("Error: The website URL is invalid.");
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                checkAndNotifyAWSCredentials(); // Check AWS credentials only when the website is down
                waitAndContinue(60); // Wait for 60 seconds before checking again
                isWebsiteDown = true;
            }
        }
    }

    /**
     * Determines the website URL based on user input.
     *
     * @param input The user input for the website name or URL.
     * @return The determined website URL.
     */
    private String determineWebsiteURL(String input) {
        String defaultURL = "https://leetcode.com/";

        if (input.isEmpty()) {
            return defaultURL;
        } else if (input.startsWith("http://") || input.startsWith("https://")) {
            return input;
        } else {
            // Check if input ends with ".com" and construct the URL accordingly
            return (input.endsWith(".com")) ? "https://" + input : "https://" + input + ".com";
        }
    }

    /**
     * Sends an email notification using the JavaMail API and Amazon SES.
     */
    private static void sendEmail() {
        String recipientEmail = "email@example.com";
        String senderEmail = "ses.gaetanobarreca.dev";

        // Retrieve AWS access key ID, secret access key, and region from environment variables
        String awsAccessKeyId = System.getenv("AWS_ACCESS_KEY_ID");
        String awsSecretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String awsRegion = System.getenv("AWS_REGION");

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "aws");

        // Check if AWS credentials and region are set
        if (awsAccessKeyId != null && awsSecretAccessKey != null && awsRegion != null) {
            properties.put("mail.aws.user", awsAccessKeyId);
            properties.put("mail.aws.password", awsSecretAccessKey);
            properties.put("mail.aws.region", awsRegion);
        }

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

    /**
     * Checks AWS credentials and region and notifies the user if they are not set.
     */
    private void checkAndNotifyAWSCredentials() {
        String awsAccessKeyId = System.getenv("AWS_ACCESS_KEY_ID");
        String awsSecretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String awsRegion = System.getenv("AWS_REGION");

        if (isWebsiteDown && (awsAccessKeyId == null || awsSecretAccessKey == null || awsRegion == null)) {
            System.out.println("AWS credentials or region not set. If you wish to receive email notifications via Amazon SES, please update these details.");
        }
    }

    /**
     * Waits for the specified duration in seconds before continuing.
     *
     * @param seconds The number of seconds to wait.
     */
    private void waitAndContinue(int seconds) {
        try {
            System.out.println("Waiting for " + seconds + " seconds before checking again...");
            Thread.sleep(seconds * 1000); // Convert seconds to milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebsiteStatusMonitor monitor = new WebsiteStatusMonitor();
        monitor.startMonitoring();
    }
}
