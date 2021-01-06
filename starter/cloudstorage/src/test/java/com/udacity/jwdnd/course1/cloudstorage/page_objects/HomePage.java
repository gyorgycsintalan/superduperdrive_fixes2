package com.udacity.jwdnd.course1.cloudstorage.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    @FindBy(id = "logoutButton")
    private WebElement buttonLogout;

    @FindBy(id = "nav-notes-tab")
    private WebElement  navNotesTab;

    @FindBy(id = "btnAddNewNote")
    private WebElement btnAddNewNote;

    @FindBy(id = "note-title")
    private WebElement inputNoteTitle;

    @FindBy(id = "note-description")
    private WebElement inputNoteDesctription;

    @FindBy(id = "btnNoteSubmit")
    private WebElement noteSubmit;

    @FindBy(className = "cell-notetitle")
    private List<WebElement> cellsNotetitle;

    @FindBy(className = "cell-notedescription")
    private List<WebElement> cellsNotedescription;

    @FindBy(className = "btnNoteEdit")
    private List<WebElement> buttonsNoteEdit;

    @FindBy(className = "linkNoteDelete")
    private List<WebElement> linksNoteDelete;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(className = "credentialRow")
    private List<WebElement> credentialRows;
    
    @FindBy(className = "buttonCredentialEdit")
    public List<WebElement> credentialEditButtons;
    
    @FindBy(className = "linkCredentialDelete")
    public List<WebElement> linksCredentialDelete;
    
    @FindBy(className = "credentialUrl")
    private List<WebElement> credentialUrls;
    
    @FindBy(className = "credentialUsername")
    private List<WebElement> credentialUsernames;
    
    @FindBy(className = "credentialPassword")
    private List<WebElement> credentialPasswords;
    
    @FindBy(id = "credential-url")
    private WebElement inputCredentialUrl;
    
    @FindBy(id = "credential-username")
    private WebElement inputCredentialUsername;
    
    @FindBy(id = "credential-password")
    private WebElement inputCredentialPassword;
    
    @FindBy(id = "buttonCredentialSubmit")
    private WebElement inputCredentialSubmit;
    
    @FindBy(id = "btnNewCredential")
    private WebElement buttonNewCredential;
    
    @FindBy(id = "buttonCredentialClose")
    private WebElement buttonCredentialClose;

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void logoutUser() {
        buttonLogout.click();
    }

    public int getNoteCount() {
        return cellsNotedescription.size();
    }

    public void toNotesTab() {
        navNotesTab.click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes"))
        ));
    }

    public void createNote(String noteTitle, String noteDescription) {
        btnAddNewNote.click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("noteModal"))
        ));

        inputNoteTitle.clear();
        inputNoteTitle.sendKeys(noteTitle);

        inputNoteDesctription.clear();
        inputNoteDesctription.sendKeys(noteDescription);

        noteSubmit.click();
    }

    public String getNthNoteTitle(int n) {
        return cellsNotetitle.get(n).getText();
    }

    public String getNthNoteDescription(int n) {
        return cellsNotedescription.get(n).getText();
    }

    public void setNthNoteDescription(int n, String description) {
        buttonsNoteEdit.get(n).click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("noteModal"))
        ));

        inputNoteDesctription.clear();
        inputNoteDesctription.sendKeys(description);

        noteSubmit.click();
    }

    public void deleteNthNote(int n) {
        linksNoteDelete.get(n).click();
    }


    public void toCredentialsTab() {
        navCredentialsTab.click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials"))
        ));
    }
    
    public int getCredentialCount() {
        return credentialRows.size();
    }

    public String getNthCredentialUrl(int n) {
        return credentialUrls.get(n).getText();
    }
    
    public String getNthCredentialUsername(int n) {
        return credentialUsernames.get(n).getText();
    }
    
    public String getNthCredentialEncryptedPassword(int n) {
        return credentialPasswords.get(n).getText();
    }

    public String getNthCredentialUnencryptedPassword(int n) {
        credentialEditButtons.get(n).click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal"))
        ));
        String unencryptedPassword = inputCredentialPassword.getAttribute("value");
        buttonCredentialClose.click();

        return unencryptedPassword;
    }

    public String getCredentialUrl() {
        return inputCredentialUrl.getAttribute("value");
    }

    public String getCredentialUsername() {
        return inputCredentialUsername.getAttribute("value");
    }

    public String getCredentialUnencryptedPassword() {
        return inputCredentialPassword.getAttribute("value");
    }

    public void setCredentialUrl(String newUrl) {
        inputCredentialUrl.clear();
        inputCredentialUrl.sendKeys(newUrl);
    }

    public void setCredentialUsername(String newUsername) {
        inputCredentialUsername.clear();
        inputCredentialUsername.sendKeys(newUsername);
    }

    public void setCredentialPassword(String newPassword) {
        inputCredentialPassword.clear();
        inputCredentialPassword.sendKeys(newPassword);
    }

    public void submitCredentialChanges() {
        inputCredentialSubmit.click();
    }

    public void createCredential(String url, String username, String password) {
        buttonNewCredential.click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal"))
        ));

        inputCredentialUrl.clear();
        inputCredentialUrl.sendKeys(url);

        inputCredentialUsername.clear();
        inputCredentialUsername.sendKeys(username);

        inputCredentialPassword.clear();
        inputCredentialPassword.sendKeys(password);

        inputCredentialSubmit.click();
    }

    public void deleteNthCredential(int n) {
        linksCredentialDelete.get(n).click();
    }

}
