package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Test class that tests the createdDate class
 */
public class CreatedDateComparatorTest {

  LocalDate today = LocalDate.now();
  LocalDate yesterday = today.minusDays(1);
  LocalDate dayBeforeYesterday = today.minusDays(2);

  Note earlierCreatedNote = new Note("Earlier Created Note", "This note was created earlier.",
      dayBeforeYesterday, yesterday);
  Note laterCreatedNote =
      new Note("Later Created Note", "This note was created today.", yesterday, today);

  // Create an instance of your comparator
  CreatedDateComparator comparator = new CreatedDateComparator();

  /**
   * Tests the compare method in the class
   */
  @Test
  public void testCompare() {
    // Test the comparison results

    // Expected earlierCreatedNote to be less than laterCreatedNote
    assertTrue(comparator.compare(earlierCreatedNote, laterCreatedNote) < 0);

    // Expected laterCreatedNote to be greater than earlierCreatedNote
    assertTrue(comparator.compare(laterCreatedNote, earlierCreatedNote) > 0);

    // Expected to be equal when comparing the same note instances
    assertEquals(0, comparator.compare(earlierCreatedNote, earlierCreatedNote));
  }



}
