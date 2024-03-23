package ui.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import core.Errors;
import dataaccess.LocalNotesAccess;
import dataaccess.NotesAccess;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.App;

/**
 * This class provides test cases for the CreateUserController class.
 */
public class CreateUserControllerTest extends ApplicationTest {

    private TextField createUsernameInput;
    private TextField createPasswordInput;
    private TextField confirmPasswordInput;
    private Button createUserButton;
    private FxRobot robot = new FxRobot();
    private NotesAccess dataAccess = new LocalNotesAccess();
    private Text errorMessageDisplay; 

    @Override
    public void start(Stage stage) throws IOException {
        dataAccess.setTestMode();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("CreateUser.fxml"));

        CreateUserController controller = new CreateUserController();
        fxmlLoader.setController(controller);

        controller.setDataAccess(dataAccess);

        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /**
     * Initializes the user interface elements before each test method.
     */
    @BeforeEach
    public void initFields() {
        createUsernameInput = lookup("#createUsernameInput").query();
        createPasswordInput = lookup("#createPasswordInput").query();
        confirmPasswordInput = lookup("#confirmPasswordInput").query();
        createUserButton = lookup("#createUserButton").query();
        errorMessageDisplay = lookup("#errorMessageDisplay").query();
    }

    /**
     * Test if UI components, such as createUsernameInput, createPasswordInput, 
     * confirmPasswordInput, createUserButton, and errorMessageDisplay, exist.
     */
    @Test
    public void testUIComponentsExist() {
        assertNotNull(createUsernameInput);
        assertNotNull(createPasswordInput);
        assertNotNull(confirmPasswordInput);
        assertNotNull(createUserButton);
        assertNotNull(errorMessageDisplay);  
    }

    /**
     * Test the behavior of the "Create User" button when the entered passwords do not match,
     * by checking if the appropriate error message is displayed.
     */
    @Test
    public void testCreateUserButtonNonMatchingPassword(){

        robot.clickOn(createUsernameInput).write("TestUser");
        robot.clickOn(createPasswordInput).write("TestPassword1"); 
        robot.clickOn(confirmPasswordInput).write("TestPassword2"); //Non-matching passwords

        robot.clickOn(createUserButton);

        assertNotNull(errorMessageDisplay, "Error message should be displayed");
        assertEquals(Errors.NOT_EQUAL_PASSWORD.getMessage(), errorMessageDisplay.getText());
    }

    /**
     * Test the behavior of the "Create User" button when the entered passwords match,
     * by checking if the user is created and no error message is displayed.
     */
    @Test
    public void testCreateUserButtonMatchingPassword(){
        createUsernameInput.clear();
        createPasswordInput.clear();
        confirmPasswordInput.clear();

        robot.clickOn(createUsernameInput).write("TestUserTwo");
        robot.clickOn(createPasswordInput).write("TestPassword2"); 
        robot.clickOn(confirmPasswordInput).write("TestPassword2"); //Matching passwords

        robot.clickOn(createUserButton);
        dataAccess.userLogin("TestUserTwo", "TestPassword2");
        assertNotNull(dataAccess.getLoggedInUser());
                
        assertNotNull(errorMessageDisplay, "Error message should be displayed");
        assertNotEquals(Errors.NOT_EQUAL_PASSWORD.getMessage(), errorMessageDisplay.getText());
    }

    /**
     * Method that deletes file after all the tests are completed.
     */
    @AfterAll
    public static void tearDown(){
        Path.of(System.getProperty("user.home") + "AccountsTest.json").toFile().delete();
    } 
}
