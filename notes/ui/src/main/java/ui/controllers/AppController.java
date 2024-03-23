package ui.controllers;

import core.Note;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import ui.CustomListCell;

/**
 * AppController manages the main interface of the note-taking application.
 * Handles displaying the list of notes, and actions like creating, editing, and deleting notes.
 */
public class AppController extends AbstractController {

  private List<String> sortList = Arrays.asList("Date created", "Last edited date", "Title (A-Z)");

  @FXML
  private AnchorPane noteoverviewpane;
  @FXML
  private ListView<Note> noteListView;
  @FXML
  private Button newNoteButton;
  @FXML
  private Button deleteNoteButton;
  @FXML
  private ComboBox<String> sortComboBox;
  @FXML
  private Text errorMessage;

  /**
    * Constructor for the AppController when in test mode.
    */
  public AppController() {
  }

  /**
    * Initializes the main scene, including loading notes and setting up listeners.
    */
  public void startScene() {
    noteListView.getItems().clear();
    noteListView.getItems().addAll(dataAccess.getUserNoteOverview().getNotes());
    noteListView.setCellFactory(new Callback<ListView<Note>, ListCell<Note>>() {
      @Override
      public ListCell<Note> call(ListView<Note> listView) {
        return new CustomListCell();
      }
    });
    sortComboBox.getItems().addAll(sortList);
  }

  /**
    * Creates a new note and transitions to the note creation scene.
    *
    * @param event The ActionEvent triggered by clicking the "New Note" button.
    * @throws IOException If there is an issue with transitioning scenes.
    */
  @FXML
  public void newNote(ActionEvent event) throws IOException {
    setScene(Controllers.NOTE, event, dataAccess);
  }

  /**
    * Deletes a selected note.
    *
    * @param event the ActionEvent triggered by the "Delete" button click.
    * @throws IOException if an error occurs during note deletion
    */
  @FXML
  public void deleteNote(ActionEvent event) throws IOException {
    int selectedNoteIndex = noteListView.getSelectionModel().getSelectedIndex();
    try {
      dataAccess.deleteNote(selectedNoteIndex);
      updateView();
    } catch (IllegalArgumentException e) {
      errorMessage.setText(e.getMessage());
    }
  }

  /**
    * Edits a selected note by deleting it and sending it to the note editing
    * scene.
    *
    * @param event the ActionEvent triggered by the "Edit" button click.
    * @throws IOException if an error occurs during the transition to the note
    *                     editing scene.
    */
  @FXML
  public void editNote(ActionEvent event) throws IOException {
    int selectedNoteIndex = noteListView.getSelectionModel().getSelectedIndex();
    try {
      dataAccess.setSelectedIndex(selectedNoteIndex);
      setScene(Controllers.NOTE_EDIT, event, getDataAccess());
    } catch (IllegalArgumentException e) {
      errorMessage.setText(e.getMessage());
    }
  }

  /**
    * Sorts the NoteOverview using the selected sorting algorithm.
    */
  @FXML
  public void sortNoteOverview() {
    String sort = sortComboBox.getValue();
    if (sort == null) {
      return;
    }
    if (sort.equals(sortList.get(0))) { // "Created date"
      dataAccess.sortNotesByCreatedDate();
    } else if (sort.equals(sortList.get(1))) { // "Last edited date"
      dataAccess.sortNotesByLastEditedDate();
    } else if (sort.equals(sortList.get(2))) { // "Title (A-Z)"
      dataAccess.sortNotesByTitle();
    }
    updateView();
  }

  /**
    * Updates the view of the note list, refreshing the displayed notes.
    */
  public void updateView() {
    noteListView.getItems().clear();
    noteListView.getItems().addAll(dataAccess.getUserNoteOverview().getNotes());
    noteListView.setCellFactory(new Callback<ListView<Note>, ListCell<Note>>() {
      @Override
      public ListCell<Note> call(ListView<Note> listView) {
        return new CustomListCell();
      }
    });
    errorMessage.setText("");
  }
}