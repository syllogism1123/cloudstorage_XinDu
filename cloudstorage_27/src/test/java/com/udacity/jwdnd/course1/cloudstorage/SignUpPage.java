package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    @FindBy(id = "inputFirstName")

    private WebElement firstnameField;

    @FindBy(id = "inputLastName")
    private WebElement lastnameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement signupButton;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    void signup(String firstname, String lastname, String username, String password) {
        this.firstnameField.sendKeys(firstname);
        this.lastnameField.sendKeys(lastname);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.signupButton.click();
    }


}
