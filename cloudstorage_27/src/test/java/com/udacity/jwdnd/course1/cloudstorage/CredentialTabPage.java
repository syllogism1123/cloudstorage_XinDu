package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CredentialTabPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "add-newCredential")
    private WebElement addButton;

    @FindBy(id = "edit-credential")
    private WebElement editButton;

    @FindBy(id = "delete-credential")
    private WebElement deleteButton;

    @FindBy(id = "credential-url")
    private WebElement urlInputField;

    @FindBy(id = "credential-username")
    private WebElement usernameInputField;

    @FindBy(id = "credential-password")
    private WebElement passwordInputField;

    @FindBy(id = "save-credential")
    private WebElement saveButton;


    public CredentialTabPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    void addNewCredential(WebDriver driver, String url, String username, String password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab)).click();

        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(urlInputField)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(usernameInputField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(passwordInputField)).sendKeys(password);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        Thread.sleep(1000);

    }

    void editCredential(WebDriver driver, String url, String username, String password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab)).click();

        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(urlInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(urlInputField)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(usernameInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(usernameInputField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(passwordInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(passwordInputField)).sendKeys(password);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        Thread.sleep(1000);

    }

    void deleteCredential(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        Thread.sleep(1000);

    }


}
