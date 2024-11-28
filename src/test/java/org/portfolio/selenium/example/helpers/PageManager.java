package org.portfolio.selenium.example.helpers;

import org.openqa.selenium.WebDriver;
import org.portfolio.selenium.example.pages.FormPage;

public class PageManager {
    private WebDriver driver;
    private FormPage formPage;

    public PageManager(WebDriver driver) {
        this.driver = driver;
    }

    public FormPage getFormPage() {
        return (formPage == null) ? (formPage = new FormPage(driver)) : formPage;
    }
}
