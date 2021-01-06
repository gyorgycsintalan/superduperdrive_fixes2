package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page_objects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page_objects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page_objects.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.page_objects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private ResultPage resultPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void unauthorizedHomeRequest() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void unauthorizedResultRequest() {
		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void unauthorizedSignupRequest() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(5)
	public void signupLoginLogout() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		signupPage = new SignupPage(driver);
		signupPage.signupUser("Test", "User", "testuser", 123);
		Thread.sleep(1000);

		driver.get("http://localhost:" + port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		loginPage = new LoginPage(driver);
		loginPage.loginUser("testuser", "123");
		Thread.sleep(1000);

		Assertions.assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		homePage.logoutUser();
		Thread.sleep(1000);

		Assertions.assertEquals("Login", driver.getTitle());

		unauthorizedHomeRequest();
	}

	@Test
	@Order(6)
	public void signupLogin() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		signupPage = new SignupPage(driver);
		signupPage.signupUser("Test", "User", "testuser2", 123);
		Thread.sleep(1000);

		driver.get("http://localhost:" + port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		loginPage = new LoginPage(driver);
		loginPage.loginUser("testuser2", "123");
		Thread.sleep(1000);

		Assertions.assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		homePage.logoutUser();
	}

	void loginAsUser(String username, String password) throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		loginPage = new LoginPage(driver);
		loginPage.loginUser(username, password);
		Thread.sleep(1000);
	}

	@Test
	@Order(7)
	void noteCreation() throws InterruptedException {
		loginAsUser("testuser2", "123");

		Assertions.assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		homePage.toNotesTab();
		int noteCount = homePage.getNoteCount();

		homePage.createNote("title", "description body");
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		Assertions.assertTrue(resultPage.isSuccess());

		resultPage.backToHome();
		Thread.sleep(1000);

		homePage.toNotesTab();

		Assertions.assertEquals(noteCount+1, homePage.getNoteCount());
		Assertions.assertEquals("title", homePage.getNthNoteTitle(noteCount));
		Assertions.assertEquals("description body", homePage.getNthNoteDescription(noteCount));

		homePage.logoutUser();
	}

	@Test
	@Order(8)
	void secondNoteCreation() throws InterruptedException {
		loginAsUser("testuser2", "123");

		Assertions.assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		homePage.toNotesTab();
		int noteCount = homePage.getNoteCount();

		homePage.createNote("title2", "description body2");
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		Assertions.assertTrue(resultPage.isSuccess());

		resultPage.backToHome();
		Thread.sleep(1000);

		homePage.toNotesTab();

		Assertions.assertEquals(noteCount+1, homePage.getNoteCount());
		Assertions.assertEquals("title2", homePage.getNthNoteTitle(noteCount));
		Assertions.assertEquals("description body2", homePage.getNthNoteDescription(noteCount));

		homePage.logoutUser();
	}

	@Test
	@Order(9)
	void noteModification() throws InterruptedException {
		loginAsUser("testuser2", "123");

		homePage = new HomePage(driver);
		homePage.toNotesTab();
		homePage.setNthNoteDescription(0, "New Description");
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		Assertions.assertTrue(resultPage.isSuccess());

		resultPage.backToHome();
		Thread.sleep(1000);

		homePage.toNotesTab();
		Assertions.assertEquals("New Description", homePage.getNthNoteDescription(0));
		homePage.logoutUser();
	}

	@Test
	@Order(10)
	void noteDeletion() throws InterruptedException {
		loginAsUser("testuser2", "123");

		homePage = new HomePage(driver);
		homePage.toNotesTab();
		int notecountBefore = homePage.getNoteCount();
		String noteToDeleteDescription = homePage.getNthNoteDescription(0);

		homePage.deleteNthNote(0);
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		Assertions.assertTrue(resultPage.isSuccess());

		resultPage.backToHome();
		Thread.sleep(1000);

		homePage.toNotesTab();
		Assertions.assertEquals(notecountBefore-1, homePage.getNoteCount());
		Assertions.assertNotEquals(noteToDeleteDescription, homePage.getNthNoteDescription(0));
		homePage.logoutUser();
	}

	@Test
	@Order(11)
	void createCredential() throws InterruptedException {
		loginAsUser("testuser2", "123");

		homePage = new HomePage(driver);
		homePage.toCredentialsTab();
		int initialCredentialCount = homePage.getCredentialCount();
		homePage.createCredential("www.example.com", "user", "password");
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		Assertions.assertTrue(resultPage.isSuccess());
		resultPage.backToHome();
		Thread.sleep(1000);

		homePage.toCredentialsTab();
		homePage.createCredential("www.example2.com", "user2", "password2");
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		Assertions.assertTrue(resultPage.isSuccess());
		resultPage.backToHome();
		Thread.sleep(1000);

		homePage.toCredentialsTab();
		Assertions.assertEquals(initialCredentialCount+2, homePage.getCredentialCount());
		Assertions.assertEquals("www.example.com", homePage.getNthCredentialUrl(initialCredentialCount));
		Assertions.assertEquals("user", homePage.getNthCredentialUsername(initialCredentialCount));
		Assertions.assertNotEquals("password", homePage.getNthCredentialEncryptedPassword(initialCredentialCount));

		homePage.logoutUser();
	}

	@Test
	@Order(12)
	public void viewAndEditCredential() throws InterruptedException {
		loginAsUser("testuser2", "123");

		homePage = new HomePage(driver);
		homePage.toCredentialsTab();

		Assertions.assertNotEquals(homePage.getNthCredentialEncryptedPassword(0), homePage.getNthCredentialUnencryptedPassword(0));

		homePage.credentialEditButtons.get(0).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.or(
				ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal"))
		));

		homePage.setCredentialUrl("www.modified.com");
		homePage.setCredentialUsername("modifiedUsername");
		homePage.setCredentialPassword("modifiedPassword");
		homePage.submitCredentialChanges();
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		Assertions.assertTrue(resultPage.isSuccess());
		resultPage.backToHome();
		Thread.sleep(1000);

		homePage.toCredentialsTab();
		Thread.sleep(1000);

		Assertions.assertEquals("www.modified.com", homePage.getNthCredentialUrl(0));
		Assertions.assertEquals("modifiedUsername", homePage.getNthCredentialUsername(0));
		Assertions.assertNotEquals("modifiedPassword", homePage.getNthCredentialEncryptedPassword(0));
		Assertions.assertEquals("modifiedPassword", homePage.getNthCredentialUnencryptedPassword(0));

		homePage.logoutUser();
	}

	@Test
	@Order(13)
	public void testCredentialDeletion() throws InterruptedException {
		loginAsUser("testuser2", "123");

		homePage = new HomePage(driver);
		int initialCredentialCount = homePage.getCredentialCount();
		homePage.toCredentialsTab();

		String originalEncryptedPassword = homePage.getNthCredentialEncryptedPassword(0);
		String originalUnencryptedPassword = homePage.getNthCredentialUnencryptedPassword(0);
		String originalUrl = homePage.getNthCredentialUrl(0);
		String originalUsername = homePage.getNthCredentialUsername(0);

		homePage.deleteNthCredential(0);
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		Assertions.assertTrue(resultPage.isSuccess());
		resultPage.backToHome();
		Thread.sleep(1000);

		homePage.toCredentialsTab();

		Assertions.assertEquals(initialCredentialCount-1, homePage.getCredentialCount());
		Assertions.assertNotEquals(originalUrl, homePage.getNthCredentialUrl(0));
		Assertions.assertNotEquals(originalUsername, homePage.getNthCredentialUsername(0));
		Assertions.assertNotEquals(originalEncryptedPassword, homePage.getNthCredentialEncryptedPassword(0));
		Assertions.assertNotEquals(originalUnencryptedPassword, homePage.getNthCredentialUnencryptedPassword(0));

		homePage.logoutUser();
	}

}
