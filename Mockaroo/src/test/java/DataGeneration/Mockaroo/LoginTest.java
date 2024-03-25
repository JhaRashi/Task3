package DataGeneration.Mockaroo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginTest {

    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "D:\\Applications\\chromedriver-win64\\chromedriver.exe");

     
        WebDriver driver = new ChromeDriver();

       
        driver.get("https://www.saucedemo.com/v1/index.html");

        // Read test data from the CSV file
        List<String[]> testData = readTestDataFromCSV("/Mockaroo/src/test/java/DataGeneration/Mockaroo/MOCK_DATA.csv");

        
        for (String[] data : testData) {
            String username = data[0];
            String password = data[1];

            // Find username and password input fields and enter test data
            WebElement usernameField = driver.findElement(By.id("username"));
            usernameField.sendKeys(username);

            WebElement passwordField = driver.findElement(By.id("password"));
            passwordField.sendKeys(password);

            // Submit the login form
            WebElement loginButton = driver.findElement(By.id("login-button"));
            loginButton.click();


            // Check if login was successful or if there was an error message
            if (driver.getCurrentUrl().equals("https://www.saucedemo.com/v1/index.html")) {
                System.out.println("Login successful for username: " + username);
                // Include additional assertions or actions for successful login
            } else {
                WebElement errorMessage = driver.findElement(By.id("error-message"));
                System.out.println("Login failed for username: " + username + ". Error message: " + errorMessage.getText());
                // Include additional assertions or actions for failed login
            }

            // Clear input fields for next iteration
            usernameField.clear();
            passwordField.clear();
        }

        // Close the WebDriver instance
        driver.quit();
    }

    private static List<String[]> readTestDataFromCSV(String filePath) {
        List<String[]> testData = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                testData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData;
    }
}


