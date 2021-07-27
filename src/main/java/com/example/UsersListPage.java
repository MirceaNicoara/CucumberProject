package com.example;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class UsersListPage {

    WebDriver driver;

    @FindBy(xpath = "/html/body/app-root/app-users/app-header/mat-toolbar/button[2]/span[1]/span")
    private WebElement addUserButton;
    @FindBy(xpath = "(//button[contains(.,'Edit')])[1]")
    private WebElement editUserButton;

    public UsersListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
