package ui.controllers;

import core.Note;  
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * The NoteEditController class is responsible for controlling the note editing.
 * It allows users to make changes to an existing note, save these changes, or undo them.
 */
public class NoteEditController extends AbstractController {

  private Note oldNote;
  private Note newNote;

  @FXML
  private AnchorPane noteeditpane;

  @FXML
  private TextField noteInputTitle;

  @FXML
  private Button saveNoteButton;

  @FXML
  private Button undoChangesButton;

  @FXML
  private TextArea noteInputText;

  @FXML
  private Text errorMessage;

  /**
   * Sets the current note for editing to the selected note from AppController. 
   * Updates the text fields in the with the details of the provided note object.
   */
  public void loadEditInfo() {
    this.oldNote = dataAccess.getNote(dataAccess.getLoggedInUser().getUsername(),
        dataAccess.getSelectedIndex());
    setText(oldNote);
  }

  /**
   * Sets the title and text fields of the note editing interface with the details of the provided
   * note object.
   *
   * @param note from which to extract title and text.
   */
  public void setText(Note note) {
    String titel = note.getTitle();
    String text = note.getText();

    noteInputTitle.setText(titel);
    noteInputText.setText(text);
  }

  /**
   * Handles the save action for a edited note, validating and saving changes made to the note.
   *
   * @param event the ActionEvent triggered by the "Save" button click.
   * @throws IOException if an error occurs during scene transition.
   */
  @FXML
  public void saveNote(ActionEvent event) throws IOException {
    String title = noteInputTitle.getText();
    String noteText = noteInputText.getText();
    this.oldNote = dataAccess.getNote(dataAccess.getLoggedInUser().getUsername(),
        dataAccess.getSelectedIndex());

    try {
      newNote = new Note(title, noteText, oldNote.getCreatedDate(), oldNote.getEditedDate());
      dataAccess.deleteNote(dataAccess.getSelectedIndex());
      dataAccess.addNote(newNote);

      setScene(Controllers.NOTEOVERVIEW, event, dataAccess);
    } catch (IllegalArgumentException e) {
      errorMessage.setText(e.getMessage());
    }
  }

  /**
   * Handles the undo action, reverting changes made to the note.
   *
   * @param event the ActionEvent triggered by the "Undo" button click.
   * @throws IOException If an error occurs during scene transition.
   */
  @FXML
  public void undo(ActionEvent event) throws IOException {
    setScene(Controllers.NOTEOVERVIEW, event, dataAccess);
  }
}