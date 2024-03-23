package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class that tests the entire User class
 */
public class UserTest {

  private NoteOverview noteOverview;
  private User user;

  /**
   * Makes the setup before each test
   */
  @BeforeEach
  public void setUp() {
    noteOverview = new NoteOverview();
    user = new User("Username", "Password123", noteOverview);
  }

  /**
   * Tests the constructor for the class
   */
  @Test
  public void testConstructors() {
    NoteOverview testOverview = new NoteOverview();
    User smallConstructor = new User("Tests", "Tester123", testOverview);
    assertEquals("Tests", smallConstructor.getUsername());
    assertEquals("Tester123", smallConstructor.getPassword());
    assertEquals(testOverview, smallConstructor.getNoteOverview());
  }


  /**
   * Tests the getters for the class
   */
  @Test
  public void testGetters() {
    Assertions.assertEquals("Username", user.getUsername());
    Assertions.assertEquals("Password123", user.getPassword());
    Assertions.assertEquals(noteOverview, user.getNoteOverview());
    Note note = new Note("Note", "Text");
    noteOverview.addNote(note);
    Assertions.assertEquals(note, user.getNote(note));
    assertThrows(IllegalArgumentException.class, () -> user.getNoteByIndex(-1));
    assertThrows(IllegalArgumentException.class, () -> user.getNoteByIndex(2));
    Assertions.assertEquals(note, user.getNoteByIndex(0));



  }

  /**
   * Tests the setters in the class
   */
  @Test
  public void testSetters() {
    user.setUsername("NewUsername");
    Assertions.assertEquals("NewUsername", user.getUsername());
    user.setPassword("NewPassword123");
    Assertions.assertEquals("NewPassword123", user.getPassword());

  }

  /**
   * Tests the addNote method in the class
   */
  @Test
  public void testAddNote() {
    // Test adding a new note to the user's note overview
    Note note = new Note("Note", "Text");
    user.addNote(note);
    Assertions.assertTrue(user.noteExists(note)); // Check if the note exists

    // Test adding the same note again (should not be added twice)
    user.addNote(note);
    Assertions.assertEquals(1, user.getNoteOverview().getNotes().size());
  }

  /**
   * Tests the noteExists method in the class
   */
  @Test
  public void testNoteExists() {
    // Test noteExists method when a note exists and when it doesn't
    Note existingNote = new Note("Existing Note", "Text");
    user.addNote(existingNote);
    Assertions.assertTrue(user.noteExists(existingNote));

    Note nonExistingNote = new Note("Non-Existing Note", "Text");
    Assertions.assertFalse(user.noteExists(nonExistingNote));
  }
}
