package core;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for Notes based on their last edited date.
 */
public class EditedDateComparator implements Comparator<Note>, Serializable {

  /**
   * Compares two notes based on their last edited date.
   *
   * @param note1 the first note to be compared
   * @param note2 the second note to be compared
   * @return a negative integer, zero, or a positive integer if the last edited date of the first
   *         note is is respectively before, equal to, or after last edited date of the second note
   * @see java.util.Comparator#compare(Object, Object)
   */
  @Override
  public int compare(Note note1, Note note2) {
    return note1.getEditedDate().compareTo(note2.getEditedDate());
  }
}
