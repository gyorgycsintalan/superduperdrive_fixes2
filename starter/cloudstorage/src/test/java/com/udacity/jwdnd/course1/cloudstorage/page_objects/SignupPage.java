package com.udacity.jwdnd.course1.cloudstorage.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signupUser(String firstname, String lastname, String username, int password) {
        inputFirstName.clear();
        inputFirstName.sendKeys(firstname);

        inputLastName.clear();
        inputLastName.sendKeys(lastname);

        inputUsername.clear();
        inputUsername.sendKeys(username);

        inputPassword.clear();
        inputPassword.sendKeys(String.valueOf(password));

        submitButton.click();
    }
}
