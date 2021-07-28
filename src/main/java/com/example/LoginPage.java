package com.example;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class LoginPage {

    private WebDriver driver;

    @FindBy(xpath = "//input[@type='text']")
    private WebElement usernameForLogin;
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordForLogin;
    @FindBy(tagName = "button")
    private WebElement loginButton;
    @FindBy(tagName = "i")
    private WebElement keylikeIcon;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}