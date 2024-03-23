package ui.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import core.Errors;
import core.Note;
import core.NoteOverview;
import core.User;
import dataaccess.LocalNotesAccess;
import dataaccess.NotesAccess;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import ui.App;

 /**
* Test for the AppController
*/

public class AppControllerTest extends ApplicationTest{

    private Button deleteButton;
    private Button editButton;
    private Button newNoteButton;
    private ListView<String> listView;
    private Text errorMessage;
    private ComboBox<String> comboBox;
    private AnchorPane anchorpane;
    
    private FxRobot robot = new FxRobot();
    private NotesAccess dataAccess = new LocalNotesAccess();
    private NoteOverview noteoverview = new NoteOverview();
    private User user = new User("Osman", "Password3", noteoverview);
    private LocalDate editedDate = LocalDate.parse("2023-10-11");
    private LocalDate createdDate = LocalDate.parse("2023-10-05");    
    private LocalDate editedDate2 = LocalDate.parse("2023-10-12");
    private LocalDate createdDate2 = LocalDate.parse("2023-10-10");   
    private Note note = new Note("B test", "Test Text",createdDate,editedDate2);
    private Note note2 = new Note("A test", "Test text",createdDate2, editedDate);
  
    @Override
    public void start(Stage stage) throws IOException {
        dataAccess.setTestMode();
        loginUser();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("App.fxml"));

        AppController controller = new AppController();
        fxmlLoader.setController(controller);

        controller.setDataAccess(dataAccess);

        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
        controller.startScene();   
    }
    /**
    * Help method that looks up that elements in ui are there.
    */
    @BeforeEach
    public void initFields() {
        deleteButton = lookup("#deleteNoteButton").query();
        editButton = lookup("#editNoteButton").query();
        newNoteButton = lookup("#newNoteButton").query();
        listView = lookup("#noteListView").query();
        comboBox = lookup("#sortComboBox").query();
        errorMessage = lookup("#errorMessage").query();
    }

    /**
     * Login a user and create a note before running the tests.
     *
     * @throws IOException If an error occurs during user login or note creation.
     */
    public void loginUser() throws IOException {

        if (!dataAccess.readAccounts().containsUser(user)) {
            dataAccess.createUser(user);
        }
        dataAccess.userLogin("Osman", "Password3");

        
        dataAccess.addNote(note);
        dataAccess.addNote(note2);
    }

    /**
     * Tests that the components are not null.
     */
    @Test
    public void testUIComponentsExist() {
        assertNotNull(deleteButton);
        assertNotNull(editButton);
        assertNotNull(newNoteButton);
        assertNotNull(listView);
        assertNotNull(comboBox);  
        assertNotNull(errorMessage);
    }

    /**
     * Tests the deleteNote button.
     * Checks both deleting with a selcted note and without.
     */
    @Test
    public void deleteNote(){
        listView.refresh();

        robot.clickOn("#deleteNoteButton");
        assertEquals(Errors.SELECT_NOTE.getMessage(), errorMessage.getText());
        robot.clickOn("#newNoteButton");        

         // Load the NewNote scene
        FXMLLoader loader = new FXMLLoader(App.class.getResource("Note.fxml"));
        NoteController noteController = new NoteController();
        loader.setController(noteController);
        AnchorPane expectedPane;
        try {
            expectedPane = loader.load();
        } catch (IOException e) {
            throw new AssertionError("Failed to load Note.fxml", e);
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
        robot.clickOn("#newNoteInputTitle").write("Note to delete");
        robot.clickOn("#newNoteInputText").write("Delete");
        robot.clickOn("#saveNoteButton");
        ListView<String> node2 = lookup("#noteListView").query();


        // Add an item to the ListView using the controller
        robot.clickOn("Note to delete");
        robot.clickOn("#deleteNoteButton");
        assertTrue(node2.getItems().size()==3);
    }

    /**
     * Tests the editbutton.
     * Checks both editing with a selcted note and without.
     */
    @Test
    public void editNote(){
        listView.refresh();
        robot.clickOn("#editNoteButton");
        assertEquals(Errors.SELECT_NOTE.getMessage(), errorMessage.getText());
        robot.clickOn("#newNoteButton");    
          // Load the NewNote scene
        FXMLLoader loader = new FXMLLoader(App.class.getResource("Note.fxml"));
        NoteController noteController = new NoteController();
        loader.setController(noteController);
        AnchorPane expectedPane;
        try {
            expectedPane = loader.load();
        } catch (IOException e) {
            throw new AssertionError("Failed to load Note.fxml", e);
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
        robot.clickOn("#newNoteInputTitle").write("Note to edit");
        robot.clickOn("#newNoteInputText").write("Edit");
        robot.clickOn("#saveNoteButton");    
        
        robot.clickOn("Note to edit");   
        robot.clickOn("#editNoteButton");
        anchorpane = lookup("#noteeditpane").query();
        assertTrue(anchorpane.isVisible());
    }

    /**
     * Tests the sortNote button.
     * Checks all three possibilities.
     */
    @Test
    public void testSort(){
        assertNotNull(comboBox.getItems());

        // Sort by Title (A-Z)
        robot.clickOn("#sortComboBox");
        robot.clickOn("Title (A-Z)");
        sleep(500); // Wait for the sorting to complete

        // Check order after sorting by Title
        verifyOrderOfNotes("alphabetically", 0);

        // Sort by Last edited date
        robot.clickOn("#sortComboBox");
        robot.clickOn("Last edited date");
        sleep(500); // Wait for the sorting to complete

        // Check order after sorting by Last edited date
        verifyOrderOfNotes("by last edited date", 1);

        // Sort by Date created
        robot.clickOn("#sortComboBox");
        robot.clickOn("Date created");
        sleep(500); // Wait for the sorting to complete

        // Check order after sorting by Date created
        verifyOrderOfNotes("by creation date", 2);
    }

    /**
     * Help method to verify that sorting button works.
     */
    private void verifyOrderOfNotes(String orderDescription, int k) {
        ListView<Note> notesListView = lookup("#noteListView").query();
        ObservableList<Note> items = notesListView.getItems();
        if(k==0){
            String firstNoteTitle = items.get(0).getTitle();
            String secondNoteTitle = items.get(1).getTitle();
            System.out.println(firstNoteTitle);

            assertTrue(firstNoteTitle.compareTo(secondNoteTitle) < 0, "After sorting " + orderDescription + ", the first note should be before the second note.");  
        }else if(k==1){
            LocalDate firstNoteEditDate = items.get(0).getEditedDate();  
            LocalDate secondNoteEditDate = items.get(1).getEditedDate();
            assertTrue(secondNoteEditDate.isBefore(firstNoteEditDate), "The first note's editing date should be after the second note's editing date.");
        }else{
            LocalDate firstNoteCreatedDate = items.get(0).getCreatedDate();  
            LocalDate secondNoteCreatedDate = items.get(1).getCreatedDate();
            assertTrue(secondNoteCreatedDate.isBefore(firstNoteCreatedDate), "The first note's creation date should be before the second note's creation date.");
        }
    }
   
    /**
     * Method that deletes file after tests are completed.
     */
    @AfterAll
    public static void tearDown(){
        Path.of(System.getProperty("user.home"), "AccountsTest.json").toFile().delete();
    }
}


