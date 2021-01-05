package com.udacity.jwdnd.course1.cloudstorage.page_objects;

import com.google.errorprone.annotations.FormatMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {
    @FindBy(id = "h1Success")
    private WebElement headerSuccess;

    @FindBy(id = "h1Fail")
    private WebElement headerFail;

    @FindBy(id = "h1Error")
    private  WebElement headerError;

    @FindBy(css = "body a:nth-of-type(1)")
    private WebElement linkToHome;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isSuccess() {
        return headerSuccess.isDisplayed();
    }

    public boolean isFail() {
        return headerFail.isDisplayed();
    }

    public boolean isError() {
        return headerError.isDisplayed();
    }

    public void backToHome() {
        linkToHome.click();
    }

}
