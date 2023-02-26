package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement selectFileInputField;

    @FindBy(id = "uploadButton")
    private WebElement uploadButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        this.logoutButton.click();
    }

    void selectFile(String filePath) {
        this.selectFileInputField.sendKeys(filePath);
    }

    void upload() {
        this.uploadButton.click();
    }


}
