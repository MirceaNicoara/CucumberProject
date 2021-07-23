package com.example.StepDefinitions;

import com.example.AddUserPage;
import com.example.RandomStringGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class AddUserTest {
    AddUserPage addUserPageObject;

    // generating random names to be inserted in the required fields
    RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
    String randomName = randomStringGenerator.getRandomString();
    String randomEmail = randomName + "_email@gmail.com";

    WebDriver driver;

    @Given("^Customer  opens addUser modal$")
    public void openAddUserModal() {
        driver = new FirefoxDriver();
        addUserPageObject = new AddUserPage(driver);
        driver.get("http://localhost:4200/users");
        addUserPageObject.getAddUserButton().click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/add");
    }

    @When("^Customer introduces valid credentials$")
    public void insertValidCredentials() {

        // inserting data to complete form
        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(addUserPageObject.getUsernameToAdd(), randomName)
                .sendKeys(addUserPageObject.getEmailToAdd(), randomEmail)
                .sendKeys(addUserPageObject.getFullNameToAdd(), randomName + " " + randomName)
                .sendKeys(addUserPageObject.getPasswordToAdd(), "1aa")
                .click(addUserPageObject.getTraitToAdd())
                .click(addUserPageObject.getGenderToAdd())
                .build();
        addUser.perform();

        Assert.assertEquals(addUserPageObject.getUsernameToAdd().getAttribute("value"), randomName);
        Assert.assertEquals(addUserPageObject.getEmailToAdd().getAttribute("value"), randomEmail);
        Assert.assertEquals(addUserPageObject.getFullNameToAdd().getAttribute("value"), randomName + " " + randomName);
        Assert.assertEquals(addUserPageObject.getPasswordToAdd().getAttribute("value"), "1aa");

    }

    @Then("^Upon submit a new user is created$")
    public void checkNewUserCreated() {
        // clicking Submit button when its becoming enabled
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();

        // counting users list after user added and comparing it to the initial count
        List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
        int lastCardId = usersList.size();
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastCardId + "]/div/div[2]/span"))
                .getText(), randomName);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastCardId + "]/div/div[3]/span"))
                .getText(), randomEmail);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastCardId + "]/div/div[1]/a/h1"))
                .getText(), randomName + " " + randomName);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastCardId + "]/div/div[4]/span"))
                .getText(), "1aa");

        driver.close();
    }
}
