package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class that tests the TitleComparator class
 */
public class TitleComparatorTest {

  private Note noteA;
  private Note noteB;

  // Create an instance of your comparator
  private TitleComparator comparator;

  /**
   * Setup before each test in the class
   */
  @BeforeEach
  public void setUp() {
    noteA = new Note("A", "Text to A");
    noteB = new Note("B", "Text to B");

    comparator = new TitleComparator();
  }

  /**
   * Tests the compare method in the class
   */
  @Test
  public void testCompare() {

    // Expect a negative value because the first title is lexicographically before the second
    assertTrue(comparator.compare(noteA, noteB) < 0);

    // Expect a positive value because the first title is lexicographically after the second
    assertTrue(comparator.compare(noteB, noteA) > 0);

    // Expect zero because the titles are the same
    assertEquals(0, comparator.compare(noteA, noteA));

  }

}
