package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class for storing multiple notes.
 */
public class NoteOverview {
  private List<Note> notes = new ArrayList<Note>();

  /**
   * Constructs a new NoteOverview with the specified list of notes.
   *
   * @param notes the list of Note objects to be associated with this overview
   */
  public NoteOverview(List<Note> notes) {
    this.notes = notes;
  }

  /**
   * Constructs a new, empty NoteOverview.
   */
  public NoteOverview() {}

  /**
   * Adds the specified Note to the internal collection of notes.
   *
   * @param note the Note to be added
   * @throws IllegalArgumentException if a note with the same title already exists in the collection
   */
  public void addNote(Note note) {
    if (note == null) {
      return;
    }
    if (notes.stream().anyMatch(x -> x.getTitle().equals(note.getTitle()))) {
      throw new IllegalArgumentException(Errors.EQUAL_NOTE_TITLE.getMessage());
    }
    notes.add(note);
  }

  /**
   * Removes the specified Note from the internal collection of notes.
   *
   * @param note the Note to be removed
   * @throws IllegalArgumentException if the specified note is not found in the collection
   */
  public void deleteNote(Note note) {
    if (!notes.contains(note)) {
      throw new IllegalArgumentException(Errors.NOTE_DOESNT_EXIST.getMessage());
    }
    notes.remove(note);
  }

  /**
   * Removes the note at a specified position in the internal collection of notes.
   *
   * @param index the position of the Note to be removed
   * @throws IllegalArgumentException if the index is out of range
   */
  public void deleteNote(int index) {
    if (index < 0) {
      throw new IllegalArgumentException(Errors.SELECT_NOTE.getMessage());
    }
    if (notes.size() - 1 < index) {
      throw new IllegalArgumentException(Errors.NOTE_DOESNT_EXIST.getMessage());
    }
    notes.remove(index);
  }

  /**
   * Retrieves a copy of the current list of notes.
   *
   * @return a new list containing all the notes from the internal collection
   */
  public List<Note> getNotes() {
    return new ArrayList<Note>(this.notes);
  }

  /**
   * Provides an iterator over the list of notes.
   *
   * @return an iterator over the notes in the internal collection
   */
  public Iterator<Note> notesIterator() {
    return this.notes.iterator();
  }

  /**
   * Sorts the internal list of notes by their created date in descending order.
   */
  public void sortNotesByCreatedDate() {
    notes.sort(new CreatedDateComparator().reversed());
  }

  /**
   * Sorts the internal list of notes by their last edited date in descending order.
   */
  public void sortNotesByLastEditedDate() {
    notes.sort(new EditedDateComparator().reversed());

  }

  /**
   * Sorts the internal list of notes alphabetically by their title.
   */
  public void sortNotesByTitle() {
    notes.sort(new TitleComparator());
  }
}
