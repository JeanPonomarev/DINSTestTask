package com.some_domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// TODO: this is class template for authorization. Now it's not working because of wrong CSS locators and login page url
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By loginFieldLocator = By.id("id for login text field");
    private By passwordFieldLocator = By.id("id for password text field");
    private By loginButtonLocator = By.id("id for login button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public void login(String login, String password) {
        String loginPageURL = "https://www.bookvoed.ru/my/auth?backTo=%2Fmy";
        driver.get(loginPageURL);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginFieldLocator));
        WebElement loginField = driver.findElement(loginFieldLocator);
        loginField.sendKeys(login);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(passwordFieldLocator));
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        passwordField.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        WebElement button = driver.findElement(loginButtonLocator);
        button.click();
    }
}
