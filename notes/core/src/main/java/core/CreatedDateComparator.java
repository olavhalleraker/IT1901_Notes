package core;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for Notes based on their created date.
 */
public class CreatedDateComparator implements Comparator<Note>, Serializable {

  /**
   * Compares two notes based on their created date.
   *
   * @param note1 the first note to be compared
   * @param note2 the second note to be compared
   * @return a negative integer, zero, or a positive integer if the creation date of the first note
   *         is is respectively before, equal to, or after the creation date of the second note
   * @see java.util.Comparator#compare(Object, Object)
   */
  @Override
  public int compare(Note note1, Note note2) {
    return note1.getCreatedDate().compareTo(note2.getCreatedDate());
  }
}
