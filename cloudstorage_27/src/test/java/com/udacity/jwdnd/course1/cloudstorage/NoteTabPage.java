package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NoteTabPage {

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "add-newNote")
    private WebElement addButton;

    @FindBy(id = "edit-note")
    private WebElement editButton;

    @FindBy(id = "delete-note")
    private WebElement deleteButton;

    @FindBy(id = "note-title")
    private WebElement titleInputField;

    @FindBy(id = "note-description")
    private WebElement descriptionInputField;

    @FindBy(id = "save-note")
    private WebElement saveButton;


    public NoteTabPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    void addNewNote(WebDriver driver, String title, String description) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab)).click();

        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(titleInputField)).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(descriptionInputField)).sendKeys(description);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(this.saveButton)).click();
        Thread.sleep(1000);
    }


    void editNote(WebDriver driver, String title, String description) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab)).click();

        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(titleInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(titleInputField)).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(descriptionInputField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(descriptionInputField)).sendKeys(description);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(this.saveButton)).click();
        Thread.sleep(1000);
    }

    void deleteNote(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(navNotesTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        Thread.sleep(1000);
    }


}