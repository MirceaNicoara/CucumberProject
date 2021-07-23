package com.example.StepDefinitions;

import com.example.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class LoginPageTest {
    LoginPage loginPageObject;
    WebDriver driver;

    @Given("^Open Firefox and access http://localhost:4200/$")
    public void launchProgram() {
        driver = new FirefoxDriver();
        loginPageObject = new LoginPage(driver);
        driver.get("http://localhost:4200");
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/");
        Assert.assertEquals(driver.getTitle(), "Frontend");
    }

    @When("^Customer introduces valid username and password$")
    public void enterValidCredentials() {
        loginPageObject.getUsernameElement().sendKeys("User0");
        loginPageObject.getPasswordElement().sendKeys("1abc");
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/app-root/app-home/div/div/div[2]/div[3]/div/form/div[1]/input"))
            .getAttribute("value").contains("User0"));
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/app-root/app-home/div/div/div[2]/div[3]/div/form/div[2]/input"))
            .getAttribute("value").contains("1abc"));
    }

    @Then("^Upon click, customer is logged in successfully and redirected on http://localhost:4200/users$")
    public void checkLoginSuccessful() {
        driver.findElement(By.tagName("button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/users");

        driver.close();
    }
}
