# Website Status Monitor

Website Status Monitor is a Java program that allows you to monitor the status of a website and receive notifications when it comes back online.

## Background

During a period when the popular coding platform LeetCode underwent maintenance, I saw an opportunity to further hone my coding skills. The result was "Website Status Monitor" – a Java program designed to monitor website statuses, particularly LeetCode, and notify me when they're back online. It started as a personal project in between assignments and has evolved into a tool that might benefit others who want real-time updates on their favorite websites.


## How to Use

1. Clone the repository to your local machine or open it in your preferred Java IDE.

2. Build the project using Maven by running the following command: `mvn clean install`.

3. Once the build is successful, create a `.env` file in the root directory of the project.

4. Open the `.env` file and add the following environment variables with your AWS credentials:
```plaintext
    AWS_ACCESS_KEY_ID=XXX
    AWS_SECRET_ACCESS_KEY=XXX
    AWS_REGION=XXX
```

5. Save the `.env` file.

6. Add .env to the .gitignore file to prevents the file from being committed to the Git repository. 

7. Run the program with the following command:
`java -cp target/website-checker-1.0-SNAPSHOT.jar com.example.WebsiteStatusMonitor`

8. The program will prompt you to enter the website URL to monitor. You can enter the website name (if it ends in ".com"), the full website URL, or press Enter to use the default URL, which is "https://leetcode.com".

9. The program will continuously check the website's status. If the website is online (HTTP response code 200), it will display the message "Website is back online!" and send an email notification. If the website is offline, it will display the message "Website seems down... waiting for it to come back online." and continue monitoring.

Feel free to customize or extend the program according to your needs.
Note: The program includes a delay of 60 seconds between each check. You can modify this duration by changing the value in the Thread.sleep() method.

## Future Improvements

Currently, I am focusing on exploring how to send emails using the JavaMail API and Amazon SES. However, I also plan to incorporate Twilio for sending text alerts. This way, I can utilize both email and text notifications for different purposes.

In the future, I plan to implement the following improvements:

- Integrate the JavaMail API with Amazon SES to send email alerts when the website comes back online.
- Incorporate Twilio's SMS gateway service to send text alerts for immediate notifications.
- Enhance the notification system by allowing users to configure their own email and phone number for personalized alerts.
- Implement a web-based configuration interface to easily set up the website monitoring and notification settings.
- Provide additional flexibility for monitoring multiple websites simultaneously.

Please note that these improvements are currently under development, and I will update the README with detailed instructions once they are completed.

Feel free to contribute to the project by suggesting ideas, reporting issues, or submitting pull requests.

Your contributions are welcome and will be greatly appreciated!



## Contributing
Contributions to the Website Status Monitor project are welcome! If you find any issues or have suggestions for improvement, please open an issue or submit a pull request.

## License
This project is licensed under the [MIT License](LICENCE). See the [LICENSE](LICENCE) file for more details.

Feel free to modify and expand upon this README to provide more specific instructions or additional details about your project.
