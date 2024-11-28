package org.portfolio.selenium.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.portfolio.selenium.example.helpers.PageManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.portfolio.selenium.example.pages.FormPage;
import org.portfolio.selenium.example.config.ConfigLoader;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.testng.Assert.assertTrue;

public class FormTest {

    private WebDriver driver;
    private PageManager pageManager;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        driver.get(ConfigLoader.getProperty("BASE_URL"));

        pageManager = new PageManager(driver);
    }

    @Test
    public void testFillingFormWithValidData() {
        FormPage formPage = pageManager.getFormPage();
        formPage.goToFormPage();

        formPage.fillFirstName(ConfigLoader.getProperty("FIRST_NAME"));
        formPage.fillLastName(ConfigLoader.getProperty("LAST_NAME"));
        formPage.fillEmail(ConfigLoader.getProperty("EMAIL"));
        formPage.chooseGender();
        formPage.fillPhoneNumber(ConfigLoader.getProperty("PHONE"));
        formPage.chooseDateOfBirth("1993-10-15");
        formPage.chooseHobbies();
        formPage.chooseSubjects();
        formPage.uploadPhoto();
        formPage.fillAddress();
        formPage.chooseState();
        formPage.chooseCity();

        formPage.submitForm();
    }

    @Test
    public void testFillingFormWithInvalidPhone() {
        FormPage formPage = pageManager.getFormPage();
        formPage.goToFormPage();

        formPage.fillFirstName(ConfigLoader.getProperty("FIRST_NAME"));
        formPage.fillLastName(ConfigLoader.getProperty("LAST_NAME"));
        formPage.fillEmail(ConfigLoader.getProperty("EMAIL"));
        formPage.chooseGender();
        formPage.fillPhoneNumber("abcdedfgh");
        formPage.chooseDateOfBirth("1993-10-15");
        formPage.chooseHobbies();
        formPage.chooseSubjects();
        formPage.uploadPhoto();
        formPage.fillAddress();
        formPage.chooseState();
        formPage.chooseCity();

        formPage.submitForm();

        boolean isPhoneFieldInvalid = driver.findElement(
                By.cssSelector("#userNumber-wrapper .form-control:invalid")
        ).isDisplayed();
        assertTrue(isPhoneFieldInvalid);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
