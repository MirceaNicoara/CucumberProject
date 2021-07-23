package com.example.StepDefinitions;

import com.example.AddUserPage;
import com.example.EditUserPage;
import com.example.RandomStringGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class EditUserPageTest {

    EditUserPage editUserPageObject;
    WebDriver driver;
    int editedUserId = 1;

    // generating random names to be inserted in the required fields
    RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
    String randomName = randomStringGenerator.getRandomString();
    String randomEmail = randomName + "_email@gmail.com";

    @Given("^Customer  opens ediUser modal$")
    public void openEditUserModal() {
        driver = new FirefoxDriver();
        editUserPageObject = new EditUserPage(driver);
        driver.get("http://localhost:4200/users");
        //getting user 1 to be edited
        driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + editedUserId + "]/div/div[7]/button[1]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/edit/" + editedUserId);
    }

    @When("^Customer introduces new valid credentials$")
    public void updateUserData() {

        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.urlContains("http://localhost:4200/edit/" + editedUserId));

        // clearing fields
        editUserPageObject.clearEditForm();

        // inserting new values
        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(editUserPageObject.getUsernameToEdit(), randomName)
                .sendKeys(editUserPageObject.getEmailToEdit(), randomEmail)
                .sendKeys(editUserPageObject.getFullNameToEdit(), randomName + " " + randomName)
                .sendKeys(editUserPageObject.getPasswordToEdit(), "1aa")
                .build();
        addUser.perform();

        Assert.assertEquals(editUserPageObject.getUsernameToEdit().getAttribute("value"), randomName);
        Assert.assertEquals(editUserPageObject.getEmailToEdit().getAttribute("value"), randomEmail);
        Assert.assertEquals(editUserPageObject.getFullNameToEdit().getAttribute("value"), randomName + " " + randomName);
        Assert.assertEquals(editUserPageObject.getPasswordToEdit().getAttribute("value"), "1aa");


    }

    @Then("^Upon submit the new data is displayed for that user$")
    public void submitAndCheckNewData() {

        // submitting form
        editUserPageObject.getSubmitButton().click();
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.urlContains("http://localhost:4200/users"));

        // checking values
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + editedUserId + "]/div/div[1]/a/h1")).getText(),
                randomName + " " + randomName);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + editedUserId + "]/div/div[2]/span")).getText(),
                randomName);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + editedUserId + "]/div/div[3]/span")).getText(),
                randomEmail);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + editedUserId + "]/div/div[4]/span")).getText(),
                "1aa");

        driver.close();
    }
}
