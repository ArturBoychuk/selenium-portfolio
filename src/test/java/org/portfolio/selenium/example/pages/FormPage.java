package org.portfolio.selenium.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FormPage {
    private WebElement formElement = null;
    private final WebDriver driver;
    private final By formLocator = By.id("userForm");
    private final By submitButton = By.id("submit");

    public FormPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFirstName(String firstName) {
        WebElement firstNameElement = getFormElement().findElement(By.cssSelector("#firstName"));
        firstNameElement.sendKeys(firstName);
        assertEquals(
                firstName,
                firstNameElement.getAttribute("value"),
                "Inputted text is not the same"
        );
    }

    public void fillLastName(String lastName) {
        WebElement lastNameElement = getFormElement().findElement(By.cssSelector("#lastName"));
        lastNameElement.sendKeys(lastName);
        assertEquals(
                lastName,
                lastNameElement.getAttribute("value"),
                "Inputted text is not the same"
        );
    }

    public void fillEmail(String email) {
        WebElement emailElement = getFormElement().findElement(By.cssSelector("#userEmail"));
        emailElement.sendKeys(email);
        assertEquals(
                email,
                emailElement.getAttribute("value"),
                "Inputted text is not the same"
        );
    }

    public void chooseGender() {
        WebElement genderElement = getFormElement().findElement(
                By.xpath("//label[contains(@for, 'gender-radio') and text()='Male']")
        );
        genderElement.click();

        WebElement genderInput = getFormElement().findElement(
                By.xpath("//input[contains(@id, 'gender-radio') and contains(@value, 'Male')]")
        );

        assertTrue(genderInput.isSelected(), "Radio button is not selected");
    }

    public void fillPhoneNumber(String phone) {
        WebElement phoneElement = getFormElement().findElement(By.cssSelector("#userNumber"));
        phoneElement.sendKeys(phone);
        assertEquals(
                phone,
                phoneElement.getAttribute("value"),
                "Inputted text is not the same"
        );
    }

    public void chooseDateOfBirth(String dateOfBirth) {
        WebElement dateInput = getFormElement().findElement(By.cssSelector("#dateOfBirthInput"));
        dateInput.click();

        String[] dateParts = dateOfBirth.split("-");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];

        Select yearDropdown = new Select(getFormElement().findElement(By.className("react-datepicker__year-select")));
        yearDropdown.selectByValue(year);

        Select monthDropdown = new Select(getFormElement().findElement(By.className("react-datepicker__month-select")));
        monthDropdown.selectByIndex(Integer.parseInt(month) - 1);

        WebElement dayOfBirth = getFormElement().findElement(
                By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='" + day + "']")
        );
        dayOfBirth.click();

        String formattedDate = formatDate(dateOfBirth);

        String inputValue = dateInput.getAttribute("value");
        assertEquals(inputValue, formattedDate, "Data of birth is not that was chosen");
    }

    public void chooseHobbies() {
        WebElement hobbiesElement = getFormElement().findElement(
                By.xpath("//label[contains(@for, 'hobbies-checkbox') and text()='Reading']")
        );
        hobbiesElement.click();

        WebElement hobbiesInput = getFormElement().findElement(
                By.xpath("//input[contains(@id, 'hobbies-checkbox-2')]")
        );

        assertTrue(hobbiesInput.isSelected(), "Radio button is not selected");
    }

    public void chooseSubjects() {
        String subject = "Mat";
        WebElement subjectElement = getFormElement().findElement(By.cssSelector("#subjectsInput"));

        for (char character : subject.toCharArray()) {
            subjectElement.sendKeys(String.valueOf(character));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'subjects-auto-complete__option') and text()='Maths']"))
        );
        element.click();

        WebElement selectedElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'subjects-auto-complete__multi-value__label') " +
                        "and contains(text(), 'Maths')]")
        ));

        assertTrue(selectedElement.isDisplayed(), "Subject 'Maths' was not selected.");
    }

    public void uploadPhoto() {
        String photoPath = Paths.get("src/main/resources/photos/student_photo.jpg").toAbsolutePath().toString();
        WebElement uploadElement = getFormElement().findElement(By.id("uploadPicture"));
        uploadElement.sendKeys(photoPath);
    }

    public void fillAddress() {
        String address = "123 Main St, Springfield";
        WebElement addressElement = getFormElement().findElement(By.cssSelector("#currentAddress"));
        addressElement.sendKeys(address);

        assertEquals(
                address,
                addressElement.getAttribute("value"),
                "Inputted text is not the same"
        );
    }

    public void chooseState() {
        String state = "Haryana";
        WebElement stateElement = getFormElement().findElement(By.cssSelector("#state"));

        Actions actions = new Actions(driver);
        actions.moveToElement(stateElement)
                .scrollByAmount(0, 300)
                .perform();

        stateElement.click();

        getFormElement().findElement(
                By.xpath("//div[contains(@class, 'option') and text()='" + state + "']")
        ).click();
    }

    public void chooseCity() {
        String city = "Karnal";
        WebElement cityElement = getFormElement().findElement(By.cssSelector("#city"));
        cityElement.click();
        getFormElement().findElement(
                By.xpath("//div[contains(@class, 'option') and text()='" + city + "']")
        ).click();
    }

    public void submitForm() {
        WebElement submitButtonElement = getFormElement().findElement(submitButton);
        submitButtonElement.click();

        int invalidFieldsQuantity = driver.findElements(By.cssSelector(".form-control:invalid")).size();

        if (invalidFieldsQuantity == 0) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modalTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#example-modal-sizes-title-lg")));

            String actualText = modalTitle.getText();
            assertEquals(
                    "Thanks for submitting the form",
                    actualText,
                    "The modal title text does not match!"
            );
        }
    }

    public void goToFormPage() {
        driver.findElement(
                By.xpath("//div[contains(@class, 'card-body') and contains(., 'Forms')]")
        ).click();
        driver.findElement(
                By.xpath("//li[contains(@class, 'btn-light') and contains(., 'Practice Form')]")
        ).click();

        WebElement submitButtonElement = getFormElement().findElement(submitButton);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                submitButtonElement
        );
    }

    private WebElement getFormElement() {
        return (formElement == null || isElementStale(formElement))
                ? (formElement = driver.findElement(formLocator))
                : formElement;
    }

    private boolean isElementStale(WebElement element) {
        try {
            element.isDisplayed();
            return false;
        } catch (StaleElementReferenceException e) {
            return true;
        }
    }

    private String formatDate(String dateOfBirth) {
        LocalDate date = LocalDate.parse(dateOfBirth);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        return date.format(formatter);
    }
}
