package json;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.Accounts;
import core.Note;
import core.NoteOverview;
import core.User;

/**
 * Tests the serialization and deserialization logic for the Accounts class.
 */
public class AccountsPersistanceTest {

  private AccountsPersistence persistence = new AccountsPersistence();
  private NoteOverview noteOverview = new NoteOverview();

  /**
   * Tests the serialization and deserialization processes for Accounts objects.
   */
  @Test
  public void testSerializersDeserializers() {
    Accounts accounts = new Accounts();
    User hammad = new User("Hammad", "Password123", noteOverview);
    LocalDate crDate = LocalDate.now();
    LocalDate edDate = LocalDate.now();

    ObjectMapper mapper = AccountsPersistence.getObjectMapper();
    assertNotNull(mapper, "ObjectMapper should not be null");
    Note note = new Note("Januar 2021", "Text", crDate, edDate);
    hammad.addNote(note);
    accounts.addUser(hammad);

    try {
      assertThrows(IllegalArgumentException.class, () -> persistence.loadAccounts());
      assertThrows(IllegalStateException.class, () -> persistence.saveAccounts(accounts));
      Path path = Paths.get(System.getProperty("user.home"), "AccountsTest.json");
      persistence.setFilePath("AccountsTest.json");
      assertEquals(path, persistence.getSaveFilePath());
      persistence.saveAccounts(accounts);
      Accounts accounts2 = persistence.loadAccounts();
      Assertions.assertTrue(accounts2.iterator().hasNext());
      User hammad2 = ((User) accounts2.iterator().next());
      Assertions.assertNotEquals("Brage", hammad2.getUsername());
      Iterator<User> it = accounts2.iterator();
      Assertions.assertTrue(it.hasNext());
      Assertions.assertEquals("Hammad", hammad2.getUsername());
      Assertions.assertEquals("Password123", hammad2.getPassword());
      Assertions.assertTrue(hammad2.getNoteOverview().getNotes().size() == 1);
      Assertions
          .assertTrue(hammad2.getNoteOverview().getNotes().get(0).getTitle().equals("Januar 2021"));
      Assertions.assertTrue(hammad2.getNoteOverview().getNotes().get(0).getText().equals("Text"));
      Assertions
          .assertTrue(hammad2.getNoteOverview().getNotes().get(0).getEditedDate().equals(edDate));
      Assertions
          .assertTrue(hammad2.getNoteOverview().getNotes().get(0).getCreatedDate().equals(crDate));

    } catch (IOException e) {
      Assertions.fail();
    }
  }
}
