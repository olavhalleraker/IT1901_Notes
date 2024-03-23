package ui.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import core.Note;
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
 * This class provides test cases for the NoteEditController class.
 * 
 */
public class NoteEditControllerTest extends ApplicationTest {

    private TextField noteInputTitle;
    private Button saveNoteButton, undoChangesButton;
    private TextArea noteInputText;
    private Text errorMessage;
    private NotesAccess dataAccess = new LocalNotesAccess();
    private FxRobot robot = new FxRobot();

    private Note note;

    /**
     * Overrides the start method from ApplicationTest to set up the JavaFX stage
     * and load the FXML file for the note editing screen.
     *
     * @param stage The JavaFX stage to set up.
     * @throws IOException If an error occurs during the loading of the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        dataAccess.setTestMode();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("NoteEdit.fxml"));

        NoteEditController controller = new NoteEditController();
        fxmlLoader.setController(controller);

        controller.setDataAccess(dataAccess);

        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();

        loginUser();
    }

    /**
     * Login a user and create a note before running the tests.
     *
     * @throws IOException If an error occurs during user login or note creation.
     */
    public void loginUser() throws IOException {

        NoteOverview noteoverview = new NoteOverview();
        User user = new User("User1", "Password1", noteoverview);

        if (!dataAccess.readAccounts().containsUser(user)) {
            dataAccess.createUser(user);
        }
        dataAccess.userLogin("User1", "Password1");

        // creates Note to edit
        LocalDate editedDate = LocalDate.parse("2023-10-11");
        LocalDate createdDate = LocalDate.parse("2023-10-05");
        note = new Note("Selected Note", "Text", createdDate, editedDate);
        dataAccess.addNote(note);
    }

    /**
     * Initializes the user interface elements before each test method.
     */
    @BeforeEach
    public void initFields() {
        noteInputTitle = lookup("#noteInputTitle").query();
        noteInputText = lookup("#noteInputText").query();
        errorMessage = lookup("#errorMessage").query();
        saveNoteButton = lookup("#saveNoteButton").query();
        undoChangesButton = lookup("#undoChangesButton").query();

        interact(() -> noteInputTitle.setText(note.getTitle()));
        interact(() -> noteInputText.setText(note.getText()));
    }

    /**
     * Tests the existence of UI components on the note editing screen.
     */
    @Test
    public void testUIComponentsExist() {
        assertNotNull(noteInputTitle);
        assertNotNull(noteInputText);
        assertNotNull(errorMessage);
        assertNotNull(saveNoteButton);
        assertNotNull(undoChangesButton);
    }

    /**
     * Tests the behavior of the "Undo" button when changes are made to the note.
     */
    @Test
    public void testUndoButton() {

        String editedTitle = "Edited Note Title";
        String editedText = "Edited Text";
        robot.clickOn(noteInputTitle).eraseText(13).write(editedTitle);
        robot.clickOn(noteInputText).eraseText(4).write(editedText);

        clickOn(undoChangesButton);

        assertEquals("Selected Note", dataAccess.getLoggedInUser().getNote(note).getTitle());
        assertEquals("Text", dataAccess.getLoggedInUser().getNote(note).getText());
    }

    /**
     * Tests the behavior of the "save" button when note is saved.
     */
    @Test 
    public void testSaveButton(){
        String editedTitle = "Edited Note Title";
        String editedText = "Edited Text";
        robot.clickOn(noteInputTitle).eraseText(13).write(editedTitle);
        robot.clickOn(noteInputText).eraseText(4).write(editedText);

        clickOn(saveNoteButton);
        int index = dataAccess.getLoggedInUser().getNoteOverview().getNotes().size()-1;
        assertEquals("Edited Note Title", dataAccess.getLoggedInUser().getNoteByIndex(index).getTitle());
        assertEquals("Edited Text", dataAccess.getLoggedInUser().getNoteByIndex(index).getText());
    }

    /**
     * Tests the behavior of the "Save" button when changes are made to the note that are not valid.
     */
    @Test
    public void testSaveButtonRemoveFields() {
        robot.clickOn(noteInputTitle).eraseText(13);
        robot.clickOn(noteInputText).eraseText(4);

        clickOn(saveNoteButton);
        
        // Verify that an error message is displayed
        assertFalse(errorMessage.getText().isEmpty());
    }
    /**
     * Deletes testfile after all the tests are run.
     */
    @AfterAll
    public static void tearDown(){
        Path.of(System.getProperty("user.home") + "AccountsTest.json").toFile().delete();
    }
}
