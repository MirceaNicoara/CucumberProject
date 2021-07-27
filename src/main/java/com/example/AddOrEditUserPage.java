package com.example;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;

@Getter
public class AddOrEditUserPage {

    WebDriver driver;

    @FindBy(id = "mat-input-0")
    private WebElement usernameToAddOrEdit;
    @FindBy(id = "mat-input-1")
    private WebElement emailToAddOrEdit;
    @FindBy(id = "mat-input-2")
    private WebElement fullNameToAddOrEdit;
    @FindBy(id = "mat-input-3")
    private WebElement passwordToAddOrEdit;
    @FindBy(xpath = "(//span[@class='mat-checkbox-inner-container'])[1]")
    private WebElement traitToAddOrEdit1;
    @FindBy(xpath = "(//span[@class='mat-checkbox-inner-container'])[2]")
    private WebElement traitToAddOrEdit2;
    @FindBy(xpath = "(//span[@class='mat-checkbox-inner-container'])[3]")
    private WebElement traitToAddOrEdit3;
    @FindBy(xpath = "(//span[@class='mat-checkbox-inner-container'])[4]")
    private WebElement traitToAddOrEdit4;
    @FindBy(xpath = "(//span[@class='mat-radio-outer-circle'])[1]")
    private WebElement genderToAddOrEdit1;
    @FindBy(xpath = "(//span[@class='mat-radio-outer-circle'])[2]")
    private WebElement genderToAddOrEdit2;
    @FindBy(xpath = "(//span[@class='mat-radio-outer-circle'])[3]")
    private WebElement genderToAddOrEdit3;
    @FindBy(xpath = "//span[contains(.,'Submit')]")
    private WebElement submitButtonAddOrEdit;

    public AddOrEditUserPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clearEditForm() {
        clearInputFields();
        traitToAddOrEdit1.click();
        traitToAddOrEdit2.click();
        traitToAddOrEdit3.click();
        traitToAddOrEdit4.click();
    }

    public void clearInputFields() {
        usernameToAddOrEdit.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        emailToAddOrEdit.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        fullNameToAddOrEdit.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        passwordToAddOrEdit.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    public String getRandomString() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int stringLength = 5;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        generatedString = generatedString.substring(0, 1).toUpperCase() + generatedString.substring(1).toLowerCase();
        return generatedString;
    }

    public void insertCredentials(String username, String email, String fullName, String password) {
        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(usernameToAddOrEdit, username)
                .sendKeys(emailToAddOrEdit, email)
                .sendKeys(fullNameToAddOrEdit, fullName)
                .sendKeys(passwordToAddOrEdit, password)
                .build();
        addUser.perform();
    }

    public void selectTraitsAndGender() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click()", traitToAddOrEdit1);
        jsExecutor.executeScript("arguments[0].click()", traitToAddOrEdit2);
        jsExecutor.executeScript("arguments[0].click()", genderToAddOrEdit3);
    }

}
