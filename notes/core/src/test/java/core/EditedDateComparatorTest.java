package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Test class that tests the editedDate class
 */
public class EditedDateComparatorTest {

  LocalDate today = LocalDate.now();
  LocalDate yesterday = today.minusDays(1);
  LocalDate dayBeforeYesterday = today.minusDays(2);

  Note earlierNote =
      new Note("Earlier Note", "This note was edited earlier.", dayBeforeYesterday, yesterday);
  Note laterNote = new Note("Later Note", "This note was edited today.", dayBeforeYesterday, today);

  // Create an instance of your comparator
  EditedDateComparator comparator = new EditedDateComparator();

  /**
   * Tests the compare method in the class
   */
  @Test
  public void testCompare() {
    // Test the comparison results

    // Expected earlierNote to be less than laterNote
    assertTrue(comparator.compare(earlierNote, laterNote) < 0);

    // Expected laterNote to be greater than earlierNote
    assertTrue(comparator.compare(laterNote, earlierNote) > 0);

    // Expected to be equal when comparing the same note instances
    assertEquals(0, comparator.compare(earlierNote, earlierNote));

  }

}
