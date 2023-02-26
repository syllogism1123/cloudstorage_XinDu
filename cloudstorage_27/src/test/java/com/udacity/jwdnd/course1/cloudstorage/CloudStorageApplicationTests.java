package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {
    @LocalServerPort
    private Integer port;
    private static WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private CredentialTabPage credentialTabPage;
    private NoteTabPage noteTabPage;
    private SignUpPage signUpPage;
    private String baseUrl;
    private String firstname;
    private String lastname;
    private String username;
    private String password;


    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterAll
    static void close() {
        driver.quit();
    }

    @BeforeEach
    void init() {
        homePage = new HomePage(driver);
        signUpPage = new SignUpPage(driver);
        loginPage = new LoginPage(driver);
        credentialTabPage = new CredentialTabPage(driver);
        noteTabPage = new NoteTabPage(driver);
        baseUrl = "http://localhost:" + port;
        firstname = "Xin";
        lastname = "Du";
        username = "syllogism";
        password = "Syl123#456";

    }

    @Order(1)
    @Test
    void testLoginSignupLogoutAndRedirection() throws InterruptedException {
        driver.get(baseUrl + "/login");
        assertEquals("Login", driver.getTitle());
        loginPage.login(username, password); //user has not registered yet
        assertEquals("Invalid username or password", loginPage.errorMsg()); //get errorMsg
        Thread.sleep(2000);

        driver.get(baseUrl + "/signup");
        assertEquals("Sign Up", driver.getTitle());
        signUpPage.signup(firstname, lastname, username, password);
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText()
                .contains("You successfully signed up!"));
        Thread.sleep(2000);

        signUpPage.signup("Albert", "Einstein", username, "1234567"); //The username already exists.get errorMsg
        Thread.sleep(2000);

        driver.get(baseUrl + "/login");
        loginPage.login(username, password);

        driver.get(baseUrl + "/home");
        assertEquals("Home", driver.getTitle());
        Thread.sleep(2000);

        homePage.logout();
        assertEquals(baseUrl + "/login", driver.getCurrentUrl());
        Thread.sleep(2000);
    }

    @Order(3)
    @Test
    public void testBadUrl() throws InterruptedException {
        driver.get(baseUrl + "/signup");
        signUpPage.signup(firstname, lastname, username, password);

        driver.get(baseUrl + "/login");
        loginPage.login(username, password);

        String badUrl = "/fghkla123i#pocv";
        driver.get(baseUrl + badUrl);
        Assertions.assertTrue(driver.findElement(By.id("error-msg")).getText()
                .contains("This page does not exist"));
        Thread.sleep(2000);
    }

    @Order(4)
    @Test
    public void testLargeUpload() throws InterruptedException {
        driver.get(baseUrl + "/signup");
        signUpPage.signup(firstname, lastname, username, password);

        driver.get(baseUrl + "/login");
        loginPage.login(username, password);
        driver.get(baseUrl + "/home");
        File uploadFile = new File("upload5m.zip");
        homePage.selectFile(uploadFile.getAbsolutePath());
        homePage.upload();
        Thread.sleep(2000);

    }

    @Order(5)
    @ParameterizedTest
    @MethodSource("noteArgumentsProvider")
    public void testNoteCRUD(String note, String description, String newNote, String newDescription)
            throws InterruptedException {
        driver.get(baseUrl + "/signup");
        signUpPage.signup(firstname, lastname, username, password);

        driver.get(baseUrl + "/login");
        loginPage.login(username, password);

        driver.get(baseUrl + "/home");
        noteTabPage.addNewNote(driver, note, description);//adds a new note
        driver.get(baseUrl + "/home");

        noteTabPage.editNote(driver, newNote, newDescription); //edit a note
        driver.get(baseUrl + "/home");
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        noteTabPage.deleteNote(driver); //delete a note
        driver.get(baseUrl + "/home");
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);
    }

    private static Stream<Arguments> noteArgumentsProvider() {
        return Stream.of(
                Arguments.of("1.Note", "1st. Note", "2.Note", "2nd. Note"),
                Arguments.of("3.Note", "3rd. Note", "4.Note", "4th. Note"));
    }

    @Order(6)
    @ParameterizedTest
    @MethodSource("credentialArgumentsProvider")
    public void testCredentialCRUD(String URL, String credentialUsername, String credentialPassword,
                                   String newURL, String newCredentialUsername, String newCredentialPassword)
            throws InterruptedException {
        driver.get(baseUrl + "/signup");
        signUpPage.signup(firstname, lastname, username, password);

        driver.get(baseUrl + "/login");
        loginPage.login(username, password);

        driver.get(baseUrl + "/home");
        credentialTabPage.addNewCredential(driver, URL, credentialUsername, credentialPassword);
        driver.get(baseUrl + "/home");

        credentialTabPage.editCredential(driver, newURL, newCredentialUsername, newCredentialPassword);
        driver.get(baseUrl + "/home");
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);

        credentialTabPage.deleteCredential(driver);
        driver.get(baseUrl + "/home");
        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(1000);

    }

    private static Stream<Arguments> credentialArgumentsProvider() {
        return Stream.of(
                Arguments.of("www.example.com", "credentialUser_syl", "Syl12#45$78!90",
                        "www.udacity.com", "udacityUser", "Udacity#123$456"));
    }


}
