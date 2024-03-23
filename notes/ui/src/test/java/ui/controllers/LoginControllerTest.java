package ui.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import core.Errors;
import core.NoteOverview;
import core.User;
import dataaccess.LocalNotesAccess;
import dataaccess.NotesAccess;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import ui.App;

/**
 * This class provides test cases for the LoginController class.
 */
public class LoginControllerTest extends ApplicationTest {

    private TextField usernameField;
    private TextField passwordField;
    private NotesAccess dataAccess = new LocalNotesAccess();
    private Text errorMessage;
    private FxRobot robot = new FxRobot();

    @Override
    public void start(Stage stage) throws IOException {
        dataAccess.setTestMode();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("Login.fxml"));

        LoginController controller = new LoginController();
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
        usernameField = lookup("#usernameInput").query();
        passwordField = lookup("#passwordInput").query();
        errorMessage = lookup("#errorMessage").query();
    }

    /**
     * Test if UI components, such as usernameField, passwordField, and
     * errorMessage, exist.
     */
    @Test
    public void testUIComponentsExist() {
        assertNotNull(usernameField);
        assertNotNull(passwordField);
        assertNotNull(errorMessage);
    }

    /**
     * Test the behavior of the "Create User" button by checking if the scene
     * transition works as expected.
     */
    @Test
    public void testCreateUserButton() {
        // Check if the button can be clicked
        robot.clickOn("#createUserButton");

        // Load the CreateUser scene
        FXMLLoader loader = new FXMLLoader(App.class.getResource("CreateUser.fxml"));
        CreateUserController createUserController = new CreateUserController();
        loader.setController(createUserController);
        AnchorPane expectedPane;
        try {
            expectedPane = loader.load();
        } catch (IOException e) {
            throw new AssertionError("Failed to load CreateUser.fxml", e);
        }

        // Get the current window's root pane
        Window currentWindow = robot.window(0); // the primary window
        Scene currentScene = currentWindow.getScene();
        Parent currentRoot = currentScene.getRoot();
        if (!(currentRoot instanceof AnchorPane)) {
            throw new AssertionError("Expected root to be instance of AnchorPane");
        }
        AnchorPane currentPane = (AnchorPane) currentRoot;

        // Compare children nodes, ensure that the scene transition works as expected
        ObservableList<Node> nodeListCurrentWindow = currentPane.getChildren();
        ObservableList<Node> nodeListExpectedWindow = expectedPane.getChildren();
        for (int i = 0; i < nodeListCurrentWindow.size(); i++) {
            assertEquals(nodeListCurrentWindow.get(i).getId(), nodeListExpectedWindow.get(i).getId());
        }
    }

    /**
     * Test the behavior of the "Login" button for an existing user by checking if
     * the scene transition works as expected.
     */
    @Test
    public void testLoginButtonExistingUser() throws IOException {

        createTestUser();

        // Type into the Username and Password fields and press the Login button
        robot.clickOn(usernameField).write("testUser");
        robot.clickOn(passwordField).write("testUserPassword1");
        robot.clickOn("#loginButton");

        assertTrue(errorMessage.getText().isEmpty());

        // Load the Add scene
        FXMLLoader loader = new FXMLLoader(App.class.getResource("App.fxml"));
        AppController createUserController = new AppController();
        loader.setController(createUserController);
        AnchorPane expectedPane;
        try {
            expectedPane = loader.load();
        } catch (IOException e) {
            throw new AssertionError("Failed to load App.fxml", e);
        }

        // Get the current window's root pane
        Window currentWindow = robot.window(0); // the primary window
        Scene currentScene = currentWindow.getScene();
        Parent currentRoot = currentScene.getRoot();
        if (!(currentRoot instanceof AnchorPane)) {
            throw new AssertionError("Expected root to be instance of AnchorPane");
        }
        AnchorPane currentPane = (AnchorPane) currentRoot;

        // Compare children nodes, ensure that the scene transition works as expected
        ObservableList<Node> nodeListCurrentWindow = currentPane.getChildren();
        ObservableList<Node> nodeListExpectedWindow = expectedPane.getChildren();
        for (int i = 0; i < nodeListCurrentWindow.size(); i++) {
            assertEquals(nodeListCurrentWindow.get(i).getId(), nodeListExpectedWindow.get(i).getId());
        }
    }

    /**
     * Test the behavior of the "Login" button for a non-existing user by checking
     * if the appropriate error message is displayed.
     */
    @Test
    public void testLoginButtonNonExistingUser() throws IOException {
        // Username and Password fields are empty
        assertTrue(usernameField.getText().isEmpty());
        assertTrue(passwordField.getText().isEmpty());

        createTestUser();

        // Type into the Username and Password fields and press the Login button
        robot.clickOn(usernameField).write("testUserNonExisting");
        robot.clickOn(passwordField).write("testUserPassword1");
        robot.clickOn("#loginButton");

        assertNotNull(errorMessage, "Error message should be displayed");
        assertEquals(Errors.NOT_REGISTERED.getMessage(), errorMessage.getText());
    }

    /**
     * Method that adds a user for test purposes.
     */
    private void createTestUser() throws IOException {
        try {
            NoteOverview noteOverview = new NoteOverview();

            User user = new User("testUser",
                    "testUserPassword1", noteOverview);

            dataAccess.createUser(user);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Method that deletes file after tests are done.
     */
    @AfterAll
    public static void tearDown(){
        Path.of(System.getProperty("user.home") + "AccountsTest.json").toFile().delete();

    }
}
