package core;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for Notes based on their title.
 */
public class TitleComparator implements Comparator<Note>, Serializable {

  /**
   * Compares two notes based on their titles in a case-insensitive manner.
   *
   * @param note1 the first note to be compared
   * @param note2 the second note to be compared
   * @return a negative integer, zero, or a positive integer if the title of the first note is is
   *         lexicographically before, equal to, or after the title of the second note when ignoring
   *         case differences.
   * @see java.util.Comparator#compare(Object, Object)
   */
  @Override
  public int compare(Note note1, Note note2) {
    return note1.getTitle().toLowerCase().compareTo(note2.getTitle().toLowerCase());
  }
}
