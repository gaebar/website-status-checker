package com.example;

import java.net.HttpURLConnection;
import java.net.URL;

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
                    break;
                } else {
                    System.out.println("Leetcode website seems down... waiting for it to come back online.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
