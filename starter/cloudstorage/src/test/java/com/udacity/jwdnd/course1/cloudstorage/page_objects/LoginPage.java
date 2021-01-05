package com.udacity.jwdnd.course1.cloudstorage.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void loginUser(String username, String password) {
        inputUsername.clear();
        inputUsername.sendKeys(username);

        inputPassword.clear();
        inputPassword.sendKeys(password);

        submitButton.click();
    }

}
