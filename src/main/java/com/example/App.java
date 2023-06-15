package com.example;

public class App {
    public static void main(String[] args) {
        WebsiteStatusMonitor monitor = new WebsiteStatusMonitor();
        monitor.startMonitoring();
    }
}
