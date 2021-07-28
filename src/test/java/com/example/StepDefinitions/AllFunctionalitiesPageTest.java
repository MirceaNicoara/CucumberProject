package com.example.StepDefinitions;

import com.example.AddOrEditUserPage;
import com.example.LoginPage;
import com.example.UsersListPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class AllFunctionalitiesPageTest {
    LoginPage loginPageObject;
    AddOrEditUserPage addOrEditUserPageObject;
    UsersListPage usersListPageObject;
    WebDriver driver;
    String randomUsername, randomEmail, randomFullName, randomPassword;
    WebDriverWait myWaitVariable;

    @Given("^Customer logs into the application$")
    public void launchProgram() {
        driver = new FirefoxDriver();
        loginPageObject = new LoginPage(driver);
        addOrEditUserPageObject = new AddOrEditUserPage(driver);
        usersListPageObject = new UsersListPage(driver);
        myWaitVariable = new WebDriverWait(driver, 5);
        driver.get("http://localhost:4200");
        loginPageObject.getUsernameForLogin().sendKeys("loginUsername");
        loginPageObject.getPasswordForLogin().sendKeys("loginPassword");
        driver.findElement(By.tagName("button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/users");
    }

    @Then("Customer is able to add a new user using these values {string}, {string}, {string} and {string}")
    public void customerIsAbleToAddANewUserUsingTheseValuesAnd(String username, String email, String fullName, String password) {
        loginPageObject.getAddUserButton().click();
        loginPageObject.insertCredentials(username, email, fullName, password);
        loginPageObject.selectTraitsAndGender();
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(loginPageObject.getSubmitButtonAddOrEdit())).click();
        List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
        int lastCardId = usersList.size();
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastCardId + "]/div/div[2]/span"))
                .getText(), username);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastCardId + "]/div/div[3]/span"))
                .getText(), email);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastCardId + "]/div/div[1]/a/h1"))
                .getText(), fullName);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastCardId + "]/div/div[4]/span"))
                .getText(), password);
    }

    @And("^Customer is able to edit an existing user$")
    public void checkEditUserSuccessful() {
        driver.get("http://localhost:4200/users");

        // generating new random names to be inserted in the required fields
        randomUsername = loginPageObject.getRandomString();
        randomEmail = randomUsername + "_email@gmail.com";
        randomFullName = randomUsername + randomUsername;
        randomPassword = randomUsername + "123";

        // accessing edit functionality for user 1
        loginPageObject.getEditUserButton().click();
        myWaitVariable.until(ExpectedConditions.urlContains("http://localhost:4200/edit/1"));

        // clearing fields
        loginPageObject.clearEditForm();

        // inserting new values
        loginPageObject.insertCredentials(randomUsername, randomEmail, randomFullName, randomPassword);

        // submitting form
        loginPageObject.getSubmitButtonAddOrEdit().click();
        myWaitVariable.until(ExpectedConditions.urlContains("http://localhost:4200/users"));

        // checking values
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[1]/div/div[1]/a/h1")).getText(),
                randomFullName);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[1]/div/div[2]/span")).getText(),
                randomUsername);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[1]/div/div[3]/span")).getText(),
                randomEmail);
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[1]/div/div[4]/span")).getText(),
                randomPassword);
    }

    @And("^Customer is able to delete a user$")
    public void checkDeleteUserSuccessful() {
        // getting list size for deleting last element on next step
        List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
        int lastElementToBeDeleted = usersList.size();
        // clicking delete button for last element
        driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastElementToBeDeleted +"]/div/div[7]/button[2]")).click();
        // clicking confirmation button
        driver.findElement(By.xpath("/html/body/app-root/app-users/div[2]/app-deletemodal/div/div/button[2]/span[1]")).click();
        // saving presence of deleted element in boolean primitive
        boolean deletedElement;
        try{
            driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" +
                    lastElementToBeDeleted + "]/div/div[7]/button[2]"));
            deletedElement = true;
        } catch (NoSuchElementException nsee) {
            deletedElement = false;
        }
        // checking presence == false
        Assert.assertEquals(deletedElement, false);
    }
}
