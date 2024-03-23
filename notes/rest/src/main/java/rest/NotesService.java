package rest;

import core.Accounts;
import core.Note;
import core.NoteOverview;
import core.User;
import java.io.IOException;
import json.AccountsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.exceptions.FileException;

/**
 * Service class for managing notes and user accounts.
 */
@Service
public class NotesService {

  private Accounts accounts;
  private static final AccountsPersistence PERSISTENCE = new AccountsPersistence();

  /**
   * Constructor for NotesService with Accounts.
   *
   * @param accounts Accounts object
   */
  public NotesService(Accounts accounts) {
    this.accounts = accounts;
    PERSISTENCE.setFilePath("springbootserver-notes.json");
    save();
  }

  /**
   * Default constructor.
   */
  @Autowired
  public NotesService() {
    PERSISTENCE.setFilePath("springbootserver-notes.json");
    try {
      this.accounts = loadAccounts();
    } catch (Exception e) {
      this.accounts = manuallyCreateAccounts();
      save();
    }
  }

  /**
   * Sets the service to test mode. 
   */
  public void setTestMode() {
    PERSISTENCE.setFilePath("springbootserver-test.json");
    this.accounts = createTestAccounts();
    save();
    loadAccounts();
  }

  /**
   * Sets the service to normal mode. 
   */
  public void setNormalMode() {
    PERSISTENCE.setFilePath("springbootserver-notes.json");
    this.accounts = loadAccounts();
    save();
  }

  /**
   * Creates test accounts for the service. 
   *
   * @return The created test accounts. 
   */
  public Accounts createTestAccounts() {
    NoteOverview noteOverview = new NoteOverview();
    NoteOverview noteOverview2 = new NoteOverview();

    noteOverview.addNote(new Note("title", "text"));
    noteOverview2.addNote(new Note("title", "text"));
    noteOverview.addNote(new Note("title2", "text2"));
    noteOverview2.addNote(new Note("title2", "text2"));

    Accounts accounts = new Accounts();
    accounts.addUser(new User("testuserone", "Password1", noteOverview));
    accounts.addUser(new User("testusertwo", "Password2", noteOverview2));
    return accounts;
  }

  /**
   * Get the persistence manager for accounts data.
   *
   * @return The AccountsPersistence instance.
   */
  public AccountsPersistence getPersistence() {
    return PERSISTENCE;
  }


  /**
   * Load user accounts data from the persistent storage using the AccountsPersistence instance.
   *
   * @return The loaded user accounts data
   * @throws FileException If there is an issue loading the data from the file. 
   */
  private static Accounts loadAccounts() {
    try {
      return PERSISTENCE.loadAccounts();
    } catch (Exception e) {
      throw new FileException();
    }
  }

  /**
   * Manually creates accounts for testing purposes.
   *
   * @return Created accounts. 
   */
  private static Accounts manuallyCreateAccounts() {
    // For testing
    NoteOverview noteOverview = new NoteOverview();
    NoteOverview noteOverview2 = new NoteOverview();
    noteOverview.addNote(new Note("title", "text"));
    noteOverview2.addNote(new Note("title", "text"));
    noteOverview.addNote(new Note("title2", "text2"));
    noteOverview2.addNote(new Note("title2", "text2"));

    Accounts acc = new Accounts();
    acc.addUser(new User("userone", "Password1", noteOverview));
    acc.addUser(new User("usertwo", "Password2", noteOverview2));
    return acc;
  }

  /**
   * Get the accounts managed by this service. 
   *
   * @return The accounts. 
   */
  public Accounts getAccounts() {
    return accounts;
  }

  /**
   * Check if a given username and password combination is a valid login.
   *
   * @param username The username to check. 
   * @param password The password to check. 
   * @return 'true' if login is valid, 'false' otherwise.
   */
  public boolean validLogin(String username, String password) {
    return accounts.checkValidUserLogin(username, password);
  }

  /**
   * Gets a user by its username. 
   *
   * @param username The username of the user to get. 
   * @return The User object associated this the username. 
   */
  public User getUserByUsername(String username) {
    return accounts.getUser(username);
  }

  /**
   * Gets the NoteOverview object for a user by their username.
   *
   * @param username The username of the user to get NoteOverview for.
   * @return The NoteOverview for the specified user. 
   */
  public NoteOverview getNoteOverviewByUsername(String username) {
    if (accounts.getUser(username) == null) {
      throw new IllegalArgumentException();
    }
    return accounts.getUser(username).getNoteOverview();
  }

  /**
   * Save the user accounts to persistanet storage. 
   */
  protected void save() {
    try {
      PERSISTENCE.saveAccounts(accounts);
    } catch (IOException e) {
      throw new FileException();
    }
  }

  /**
   * Update and save notes for a specific user. 
   *
   * @param username The username of the user whose notes should be updated.
   */
  public void updateNotes(String username) {
    save();
  }

  /**
   * Add a note for a specific user. 
   *
   * @param username The username of the user whom the note should be added.
   * @param note The note to be added. 
   */
  public void addNote(String username, Note note) {
    getUserByUsername(username).addNote(note);
    save();
  }

  /**
   * Adds a new user to the accounts. 
   *
   * @param user The user to be added. 
   */
  public void createUser(User user) {
    accounts.addUser(user);
    save();
  }

  /**
   * Get a NoteOverview for a user by their username. 
   *
   * @param username The username of the user whose NoteOverview is requested.
   * @return The NoteOverview for the sepcified user. 
   */
  public NoteOverview getUserNoteOverview(String username) {
    return getUserByUsername(username).getNoteOverview();
  }

  /**
   * Delete a note for a user by index. 
   *
   * @param username The username of the user whose note should be deleted. 
   * @param index The index of the note to be deleted.
   */
  public void deleteNote(String username, int index) {
    getUserByUsername(username).getNoteOverview().deleteNote(index);
    save();
  }

  /**
   * Get a user by their username and password. 
   *
   * @param username The username of the user to get.
   * @param password The password of the user to get. 
   * @return The User object. 
   */
  public User getUser(String username, String password) {
    return accounts.getUser(username, password);
  }

  /**
   * Sort a user's notes by created date and save the changes. 
   *
   * @param username The username of the user whose notes should be sorted. 
   */
  public void sortNotesByCreatedDate(String username) {
    NoteOverview noteOverview = getNoteOverviewByUsername(username);
    noteOverview.sortNotesByCreatedDate();
    save();
  }

  /**
   * Sort a user's notes by last edited date and save the changes. 
   *
   * @param username The username of the user whose notes should be sorted. 
   */
  public void sortNotesByLastEditedDate(String username) {
    NoteOverview noteOverview = getNoteOverviewByUsername(username);
    noteOverview.sortNotesByLastEditedDate();
    save();
  }

  /**
   * Sort a user's notes alphabetically (A-Z) and save the changes. 
   *
   * @param username The username of the user whose notes should be sorted. 
   */
  public void sortNotesByTitle(String username) {
    NoteOverview noteOverview = getNoteOverviewByUsername(username);
    noteOverview.sortNotesByTitle();
    save();
  }

  /**
   * Get a note by its index for a specific user. 
   *
   * @param username The username of the user to get note from. 
   * @param index The index of the note to get. 
   * @return The Note object at the specified index. 
   */
  public Note getNote(String username, int index) {
    return getUserByUsername(username).getNoteByIndex(index);
  }
}
