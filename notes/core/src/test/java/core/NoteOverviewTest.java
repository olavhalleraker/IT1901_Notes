package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class that tests the NoteOverview
 */

public class NoteOverviewTest {

  private NoteOverview noteOverview;
  private List<Note> list = new ArrayList<Note>();
  LocalDate editedDate = LocalDate.parse("2023-10-06");
  LocalDate createdDate = LocalDate.parse("2023-10-05");
  Note noteStart = new Note("ATitle", "Chores i have to do", createdDate, editedDate);
  Note noteStart2 = new Note("BTitle2", "Chores i have to do");
  private Note note1;
  private Note note2;

  /**
   * Setup for each test run in the class
   */
  @BeforeEach
  void setUp() {

    list.add(noteStart);
    list.add(noteStart2);
    note1 = new Note("Note 1", "Text 1");
    note2 = new Note("Note 2", "Text 2");
    this.noteOverview = new NoteOverview(list);

  }

  /**
   * Tests the constructor in the class
   */
  @Test
  public void testConstructor() {
    assertEquals(list, noteOverview.getNotes());

  }

  /**
   * Tests the addNote method in the class
   */
  @Test
  public void testAdd() {
    Note note = new Note("Title3", "Content");
    noteOverview.addNote(note);
    assertTrue(noteOverview.getNotes().contains(note));

    // Test adding a null note
    noteOverview.addNote(null);
    assertTrue(noteOverview.getNotes().size() == 3);

    // Test adding a note with an existing title
    Note note1 = new Note("Title4", "Content1");
    Note note2 = new Note("Title4", "Content2");
    noteOverview.addNote(note1);
    assertThrows(IllegalArgumentException.class, () -> noteOverview.addNote(note2));
    assertTrue(noteOverview.getNotes().contains(note1));
    assertFalse(noteOverview.getNotes().contains(note2));

  }

  /**
   * Tests the deleteNote method in the class
   */
  @Test
  public void testDelete() {
    Note note = new Note("Title3", "Content");
    noteOverview.addNote(note);
    noteOverview.deleteNote(note);
    assertFalse(noteOverview.getNotes().contains(note));

    // Test delete negative index note
    assertThrows(IllegalArgumentException.class, () -> noteOverview.deleteNote(-1));

    // Test deleting a null note
    assertThrows(IllegalArgumentException.class, () -> noteOverview.deleteNote(null));

    // Test deleting a non-existing note
    Note nonExistingNote = new Note("Non-Existing Title", "Non-Existing Content");
    assertThrows(IllegalArgumentException.class, () -> noteOverview.deleteNote(nonExistingNote));

    Note note1 = new Note("Title 1", "Content 1");
    Note note2 = new Note("Title 2", "Content 2");
    noteOverview.addNote(note1);
    noteOverview.addNote(note2);

    noteOverview.deleteNote(0);
    assertFalse(noteOverview.getNotes().contains(note));
    assertTrue(noteOverview.getNotes().contains(note1));


    // Test deleting an invalid index (out of bounds)
    assertThrows(IllegalArgumentException.class, () -> noteOverview.deleteNote(3));



  }

  /**
   * Tests the sort methods in the class
   */
  @Test
  public void testSort() {
    noteOverview.sortNotesByCreatedDate();
    assertEquals(this.noteStart2, noteOverview.getNotes().get(0));
    Note note =
        new Note("CTitleee", "TExt", LocalDate.parse("2023-10-05"), LocalDate.parse("2023-10-07"));
    noteOverview.addNote(note);
    noteOverview.sortNotesByLastEditedDate();
    assertEquals(note, noteOverview.getNotes().get(1));
    assertEquals(this.noteStart2, noteOverview.getNotes().get(0));
    noteOverview.sortNotesByTitle();
    assertEquals(note, noteOverview.getNotes().get(2));
    assertEquals(this.noteStart, noteOverview.getNotes().get(0));

  }

  /**
   * Tests the iterator in the class
   */
  @Test
  public void testNotesIterator() {
    // Test iterating over notes
    noteOverview.deleteNote(0);
    noteOverview.deleteNote(0);
    noteOverview.addNote(note1);
    noteOverview.addNote(note2);
    Iterator<Note> iterator = noteOverview.notesIterator();
    Assertions.assertTrue(iterator.hasNext());
    Assertions.assertEquals(note1, iterator.next());
    Assertions.assertTrue(iterator.hasNext());
    Assertions.assertEquals(note2, iterator.next());
    Assertions.assertFalse(iterator.hasNext());
  }



}
