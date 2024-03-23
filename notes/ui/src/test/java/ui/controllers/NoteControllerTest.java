package ui.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.NoteOverview;
import core.User;
import dataaccess.LocalNotesAccess;
import dataaccess.NotesAccess;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.App;

/**
* Tests the NoteController class.
*/
public class NoteControllerTest extends ApplicationTest {

    private TextArea newNoteInputText;
    private TextField newNoteInputTitle;
    private NotesAccess dataAccess = new LocalNotesAccess();
    private Text errorMessage;
    private Button saveNoteButton;

    @Override
    public void start(Stage stage) throws IOException {
        dataAccess.setTestMode();
        loginUser();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("Note.fxml"));

        NoteController controller = new NoteController();
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
        newNoteInputText = lookup("#newNoteInputText").query();
        newNoteInputTitle = lookup("#newNoteInputTitle").query();
        errorMessage = lookup("#errorMessage").query();
        saveNoteButton = lookup("#saveNoteButton").query();
    }
    /**
     * Tests to make sure the components of teh scene are visible.
     */
    @Test
    public void testUIComponentsExist() {
        assertNotNull(newNoteInputText);
        assertNotNull(newNoteInputTitle);
        assertNotNull(errorMessage);
        assertNotNull(saveNoteButton);  
    }

    /**
     * Help method that logs in a user.
     *
     * @throws Exception if userlogin fails
     */
    public void loginUser() throws IOException {

        NoteOverview noteoverview = new NoteOverview();
        User user = new User("User", "Password1", noteoverview);

        if (!dataAccess.readAccounts().containsUser(user)) {
            dataAccess.createUser(user);
        }
        dataAccess.userLogin("User", "Password1");
    }

    /**
     * Tests the behavior of the button that makes a new note with out any title/text.
     */
    @Test
    public void testNewNoteEmptyFields() {
        // Click "Save Note" without entering any text
        clickOn(saveNoteButton);

        // Verify: An error message should be displayed
        assertNotNull(errorMessage);

        // Verify: No new notes should be added to the user's note overview
        assertEquals(0, dataAccess.getUserNoteOverview().getNotes().size());
    }

    /**
     * Tests the behavior of the "Save" button when changes are made to the note that are not valid.
     */
    @AfterAll
    public static void tearDown(){
        Path.of(System.getProperty("user.home") + "AccountsTest.json").toFile().delete();
    }
}
