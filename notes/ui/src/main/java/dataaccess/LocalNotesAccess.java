package dataaccess;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import core.Accounts;
import core.Note;
import core.NoteOverview;
import core.User;
import java.io.IOException;
import json.AccountsPersistence;

/**
 * LocalNotesAccess provides local data access functionalities for the application.
 * It handles the storage and retrieval of user and note information using a JSON file.
 */
public class LocalNotesAccess implements NotesAccess {

  private Accounts accounts;
  private User user;
  private final AccountsPersistence persistence = new AccountsPersistence();
  private int selectedIndex;

  /**
   * Constructor for LocalNotesAccess.
   * Loads user accounts from a JSON file. 
   * If the file is not found, a new Accounts object is created.
   */
  public LocalNotesAccess() {
    persistence.setFilePath("Accounts.json");
    try {
      this.accounts = persistence.loadAccounts();
    } catch (IllegalStateException | IOException e) {
      this.accounts = new Accounts();
      try {
        persistence.saveAccounts(accounts);
      } catch (IllegalStateException | IOException e1) {
        System.out.println(e1.getMessage());
      }
    }
  }

  /**
   * Switches the data access to test mode by changing the file path to a test JSON file.
   */
  @Override
  public void setTestMode() {
    persistence.setFilePath("AccountsTest.json");
    try {
      this.accounts = persistence.loadAccounts();
    } catch (IllegalStateException | IOException e) {
      this.accounts = new Accounts();
      try {
        persistence.saveAccounts(accounts);
      } catch (IllegalStateException | IOException e1) {
        System.out.println(e1.getMessage());
      }
    }

  }

  /**
   * Creates a new user and adds it to the accounts.
   *
   * @param user The user to be created and added.
   */
  @Override
  public void createUser(User user) {
    if (user != null) {
      accounts.addUser(user);
      update();
    }
  }

  /**
   * Reads and returns all accounts.
   *
   * @return Accounts containing all user data.
   * @throws IOException If there is an error reading the accounts.
   */
  @Override
  public Accounts readAccounts() throws IOException {
    return persistence.loadAccounts();
  }

  /**
   * Authenticates a user based on username and password.
   *
   * @param username The username of the user.
   * @param password The password of the user.
   * @return The authenticated User object, or null if authentication fails.
   */
  @Override
  public User userLogin(String username, String password) {
    this.user = accounts.getUser(username, password);
    return user;

  }

  /**
   * Retrieves the currently logged-in user.
   *
   * @return The logged-in User object.
   * @throws IllegalArgumentException If no user is currently logged in.
   */
  @Override
  public User getLoggedInUser() {
    if (user == null) {
      throw new IllegalArgumentException("User not logged in"); 
    }
    return this.user;
  }

  /**
   * Adds a note to the logged-in user's note overview.
   *
   * @param note The note to be added.
   */
  @Override
  public void addNote(Note note) {
    getLoggedInUser().addNote(note);
    update();
  }

  /**
   * Updates the notes for a given username.
   *
   * @param username The username whose notes are to be updated.
   */
  @Override
  public void updateNotes(String username) {
    update();
  }

  /**
   * Saves the current state of accounts to the JSON file.
   */
  public void update() {
    try {
      persistence.saveAccounts(accounts);
    } catch (StreamWriteException e) {
      // TODO: error
    } catch (DatabindException e) {
      // TODO: error
    } catch (IOException e) {
      // TODO: error
    }
  }

  /**
   * Retrieves the note overview of the logged-in user.
   *
   * @return NoteOverview containing the user's notes.
   */
  @Override
  public NoteOverview getUserNoteOverview() {
    return getLoggedInUser().getNoteOverview();
  }

  /**
   * Deletes a note from the logged-in user's note overview.
   *
   * @param index The index of the note to be deleted.
   */
  @Override
  public void deleteNote(int index) {
    getUserNoteOverview().deleteNote(index);
    update();
  }

  /**
   * Sorts the notes of the logged-in user by their creation date.
   */
  @Override
  public void sortNotesByCreatedDate() {
    getUserNoteOverview().sortNotesByCreatedDate();
    update();
  }

  /**
   * Sorts the notes of the logged-in user alphabetically by their title.
   */
  @Override
  public void sortNotesByTitle() {
    getUserNoteOverview().sortNotesByTitle();
    update();
  }

  /**
   * Sorts the notes of the logged-in user by their last edited date.
   */
  @Override
  public void sortNotesByLastEditedDate() {
    getUserNoteOverview().sortNotesByLastEditedDate();
    update();
  }

  /**
   * Gets a specific note of the logged-in user.
   *
   * @param username The username of the user.
   * @param index The index of the note to retrieve.
   * @return The requested Note object.
   */
  @Override
  public Note getNote(String username, int index) {
    return user.getNoteByIndex(index);
  }

  /**
   * Sets the index of the currently selected note.
   *
   * @param index The index of the selected note.
   */
  @Override
  public void setSelectedIndex(int index) {
    this.selectedIndex = index;
  }

  /**
   * Gets the index of the currently selected note.
   *
   * @return The index of the selected note.
   */
  @Override
  public int getSelectedIndex() {
    return this.selectedIndex;
  }
}
