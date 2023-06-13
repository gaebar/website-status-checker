# Website Status Monitor

Website Status Monitor is a Java program that allows you to monitor the status of a website and receive notifications when it comes back online. This program is specifically designed to monitor the LeetCode website, but you can modify it to monitor any other website.

## How to Use

1. Clone the repository to your local machine or open it in your preferred Java IDE.

2. Build the project using Maven by running the following command: `mvn clean install`.

3.  Once the build is successful, you can run the program whit the following command:
`java -cp target/website-checker-1.0-SNAPSHOT.jar com.example.WebsiteStatusMonitor`


4. The program will continuously check the LeetCode website's status. If the website is online (HTTP response code 200), it will display the message "LeetCode website is back online!" and terminate. If the website is offline, it will display the message "LeetCode website seems down... waiting for it to come back online" and continue monitoring.

5. You can modify the program to monitor a different website by updating the URL in the `WebsiteStatusMonitor.java` file:

`URL url = new URL("https://leetcode.com/explore/interview/card/facebook/");`

Replace the URL with the desired website URL.

Feel free to customize or extend the program according to your needs.
Note: The program includes a delay of 60 seconds between each check. You can modify this duration by changing the value in the Thread.sleep() method.

Contributing
Contributions to the Website Status Monitor project are welcome! If you find any issues or have suggestions for improvement, please open an issue or submit a pull request.

License
This project is licensed under the [MIT License](LICENCE). See the [LICENSE](LICENCE) file for more details.

Feel free to modify and expand upon this README to provide more specific instructions or additional details about your project.
