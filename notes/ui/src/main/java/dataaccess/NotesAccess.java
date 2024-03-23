package dataaccess;

import core.Accounts;
import core.Note;
import core.NoteOverview;
import core.User;
import java.io.IOException;

/**
 * NotesAccess interface defines the contract for data access operations within the application.
 */
public interface NotesAccess {

  /**
   * Gets all the accounts registred to use Notes.
   *
   * @return all the users
   * @throws IOException if not found
   */

  public Accounts readAccounts() throws IOException;

  /**
   * Method to log in as a regular user.
   *
   * @param username of the user
   * @param password of the user
   * @return the user
   */
  public User userLogin(String username, String password);

  /**
   * Creates a new user and adds it to Accounts.
   *
   * @param user the user to create.
   */
  public void createUser(User user);

  /**
   * Gets the logged in user.
   *
   * @return logged in user.
   */
  public User getLoggedInUser();

  /**
   * Adds a new note to the logged in user.
   *
   * @param note note to add
   */
  public void addNote(Note note);

  /**
   * Updates notes.
   *
   * @param username username of the logged-in user.
   */
  public void updateNotes(String username);

  /**
   * Gets the noteoverview of the logged in user.
   *
   * @return noteoverview
   */
  public NoteOverview getUserNoteOverview();

  /**
   * Deletes note of the logged in user.
   *
   * @param index of the note
   */
  public void deleteNote(int index);

  /**
   * Sorts notes by created date.
   */
  public void sortNotesByCreatedDate();

  /**
   * Sorts notes by title.
   */
  public void sortNotesByTitle();

  /**
   * Sorts notes by edited date.
   */
  public void sortNotesByLastEditedDate();

  /**
   * Gets note by username and index.
   *
   * @param username username of user
   * @param index index of note
   * @return the Note.
   */
  public Note getNote(String username, int index);

  /**
   * Sets selected note index.
   *
   * @param index index of selected note
   */
  public void setSelectedIndex(int index);

  /**
   * Retrieves the index of the selected note.
   *
   * @return The index of the selected note.
   */
  public int getSelectedIndex();

  /**
   * Sets the application to test mode. 
   */
  public void setTestMode();
}
