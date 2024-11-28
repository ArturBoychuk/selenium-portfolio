package org.portfolio.selenium.example.tests;

import org.openqa.selenium.By;
import org.portfolio.selenium.example.helpers.PageManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.portfolio.selenium.example.pages.FormPage;
import org.portfolio.selenium.example.config.ConfigLoader;

import static org.junit.Assert.assertTrue;

public class FormTest {

    private WebDriver driver;
    private PageManager pageManager;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
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
        assertTrue("Invalid phone number field is not displayed", isPhoneFieldInvalid);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
