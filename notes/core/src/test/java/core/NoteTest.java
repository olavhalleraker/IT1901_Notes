package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class that tests the entire Note class
 */

public class NoteTest {
  private Note note;
  private Note note2;
  LocalDate editedDate = LocalDate.parse("2023-10-06");
  LocalDate createdDate = LocalDate.parse("2023-10-05");

  /**
   * Makes the setup before each test
   */
  @BeforeEach
  void setUp() {
    this.note = new Note("Title", "Chores i have to do", createdDate, editedDate);
    this.note2 = new Note("Title2", "Here i want to test");

  }

  /**
   * Tests the constructor in the class
   */
  @Test
  public void testConstructor() {

    assertEquals("Title", note.getTitle());
    assertEquals("Chores i have to do", note.getText());
    assertEquals(LocalDate.parse("2023-10-05"), note.getCreatedDate());
    assertEquals(LocalDate.parse("2023-10-06"), note.getEditedDate());
    assertThrows(IllegalArgumentException.class, () -> {
      new Note("", "text", createdDate, editedDate);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Note("title", "", createdDate, editedDate);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Note("", "", createdDate, editedDate);
    });



  }

  /**
   * Tests setting a wrong date in constructor
   */
  @Test
  public void testWrongDate() {
    // created date is after edited date
    assertThrows(IllegalArgumentException.class, () -> {
      new Note("Title", "Chores i have to do", LocalDate.parse("2023-10-08"),
          LocalDate.parse("2023-10-07"));;
    }, "Created date should be before edited date!");
  }

  /**
   * Tests the other constructor in the class
   */
  @Test
  public void testConstructor2() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Note("", "text");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Note("title", "");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new Note("", "");
    });
    assertEquals("Title2", note2.getTitle());
    assertEquals("Here i want to test", note2.getText());
    assertEquals(LocalDate.now(), note2.getCreatedDate());
    assertEquals(LocalDate.now(), note2.getEditedDate());

  }

  /**
   * Tests the setDate method in the class
   */
  @Test
  public void testSetDate() {
    note.setEditedDate();
    assertEquals(LocalDate.now(), note.getEditedDate());

  }

  /**
   * Tests the setTitle method in the class
   */
  @Test
  public void testSetTitle() {
    assertThrows(IllegalArgumentException.class, () -> note.setTitle(""));
    note.setTitle("New Title");
    assertEquals("New Title", note.getTitle());
  }

  /**
   * Tests the setText method in the class
   */
  @Test
  public void testSetText() {
    assertThrows(IllegalArgumentException.class, () -> note.setText(""));
    note.setText("New text");
    assertEquals("New text", note.getText());
  }



}
