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
    @FindBy(xpath = "//span[contains(.,'Focused')]")
    private WebElement traitFocused;
    @FindBy(xpath = "//span[contains(.,'Caring')]")
    private WebElement traitCaring;
    @FindBy(xpath = "//span[contains(.,'Perfectionist')]")
    private WebElement traitPerfectionist;
    @FindBy(xpath = "//span[contains(.,'Courageous')]")
    private WebElement traitCourageous;
    @FindBy(xpath = "//span[contains(.,'Male')]")
    private WebElement genderMale;
    @FindBy(xpath = "//span[contains(.,'Female')]")
    private WebElement genderFemale;
    @FindBy(xpath = "//span[contains(.,'Apache Helicopter')]")
    private WebElement genderApacheH;
    @FindBy(xpath = "//span[contains(.,'Submit')]")
    private WebElement submitButtonAddOrEdit;

    public AddOrEditUserPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clearEditForm() {
        clearInputFields();
        traitFocused.click();
        traitCaring.click();
        traitPerfectionist.click();
        traitCourageous.click();
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
        jsExecutor.executeScript("arguments[0].click()", traitFocused);
        jsExecutor.executeScript("arguments[0].click()", traitCaring);
        jsExecutor.executeScript("arguments[0].click()", genderApacheH);
    }
}
