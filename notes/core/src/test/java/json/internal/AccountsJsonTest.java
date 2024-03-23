package json.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import core.Accounts;
import core.Note;
import core.NoteOverview;
import core.User;

/**
 * Tests for JSON serialization and deserialization of various components
 */
public class AccountsJsonTest {
  private final ObjectMapper objectMapper = new ObjectMapper();
  {
    objectMapper.registerModule(new AccountsModule());
  }

  private void assertEqualsIgnoreWhitespace(final String expected, final String actual)
      throws Exception {
    assertEquals(expected, actual.replaceAll("\\s+", ""));
  }

  /**
   * Tests the serialization of a NoteOverview object into a JSON string.
   * 
   * @throws Exception if serialization fails
   */
  @Test
  public void testNoteOverviewSerialization() throws Exception {
    LocalDate localDate = java.time.LocalDate.of(2022, 1, 1);

    final String actualJson = objectMapper.writeValueAsString(
        new NoteOverview(Arrays.asList(new Note("title", "text", localDate, localDate),
            new Note("title2", "text2", localDate, localDate))));
    final String expectedJson =
        "[{\"title\":\"title\",\"text\":\"text\",\"created\":\"2022-01-01\",\"edited\":\"2022-01-01\"},{\"title\":\"title2\",\"text\":\"text2\",\"created\":\"2022-01-01\",\"edited\":\"2022-01-01\"}]";
    assertEqualsIgnoreWhitespace(expectedJson, actualJson);
  }

  /**
   * Tests the deserialization of a JSON string into a NoteOverview object.
   * 
   * @throws Exception if deserialization fails
   */
  @Test
  public void testNoteOverviewDeserialization() throws Exception {

    final String jsonT = "\"key\":\"value\"}";
    final NoteOverview noteOverviewT = objectMapper.readValue(jsonT, NoteOverview.class);
    assertNull(noteOverviewT);
    JsonNode node = objectMapper.readTree(jsonT);

    // Make sure it's not an ArrayNode
    assertFalse(node instanceof ArrayNode);
    NoteOverviewDeserializer deserializer = new NoteOverviewDeserializer();
    NoteOverview noteOverviewK = deserializer.deserialize(node);

    // Assertions
    assertNull(noteOverviewK);

    final String json =
        "[{\"title\":\"title\",\"text\":\"text\",\"created\":\"2022-01-01\",\"edited\":\"2022-01-01\"},{\"title\":\"title2\",\"text\":\"text2\",\"created\":\"2022-01-01\",\"edited\":\"2022-01-01\"}]";
    final NoteOverview noteOverview = objectMapper.readValue(json, NoteOverview.class);

    assertEquals(2, noteOverview.getNotes().size());
    assertEquals("title", noteOverview.getNotes().get(0).getTitle());
    assertEquals("text", noteOverview.getNotes().get(0).getText());
    assertEquals("title2", noteOverview.getNotes().get(1).getTitle());
    assertEquals("text2", noteOverview.getNotes().get(1).getText());
    assertEquals(LocalDate.of(2022, 1, 1), noteOverview.getNotes().get(0).getCreatedDate());
    assertEquals(LocalDate.of(2022, 1, 1), noteOverview.getNotes().get(0).getEditedDate());

  }

  /**
   * Tests the deserialization of a JSON string into an array of Note objects.
   * 
   * @throws Exception if deserialization fails
   */
  @Test
  public void testNoteDeserialization() throws Exception {
    final String json =
        "[{\"title\":\"title\",\"text\":\"text\",\"created\":\"2022-01-01\",\"edited\":\"2022-01-01\"},{\"title\":\"title2\",\"text\":\"text2\",\"created\":\"2022-01-01\",\"edited\":\"2022-01-01\"}]";

    // Deserialize the JSON array into an array of Note objects
    Note[] notes = objectMapper.readValue(json, Note[].class);

    // Now you can access individual notes from the array
    assertEquals("title", notes[0].getTitle());
    assertEquals("text", notes[0].getText());
    assertEquals(LocalDate.of(2022, 1, 1), notes[0].getCreatedDate());
    assertEquals(LocalDate.of(2022, 1, 1), notes[0].getEditedDate());

    assertEquals("title2", notes[1].getTitle());
    assertEquals("text2", notes[1].getText());
    assertEquals(LocalDate.of(2022, 1, 1), notes[1].getCreatedDate());
    assertEquals(LocalDate.of(2022, 1, 1), notes[1].getEditedDate());
  }

  /**
   * Tests the deserialization of a JSON string into a User object.
   * 
   * @throws Exception if deserialization fails
   */
  @Test
  public void testUserDeserialization() throws Exception {
    final String json =
        "{\"username\":\"sampleUser\",\"password\":\"samplePassword123\",\"noteOverview\":[{\"title\":\"title\",\"text\":\"text\",\"created\":\"2022-01-01\",\"edited\":\"2022-01-01\"}]}";

    User user = objectMapper.readValue(json, User.class);
    assertEquals("sampleUser", user.getUsername());
    assertEquals("samplePassword123", user.getPassword());
    assertNotNull(user.getNoteOverview());
  }

  /**
   * Tests the serialization of an Accounts object into a JSON string.
   * 
   * @throws Exception if serialization fails
   */
  @Test
  public void testAccountsSerialization() throws Exception {
    final LocalDate localDate = java.time.LocalDate.of(2022, 1, 1);
    final String expectedJson =
        "[{\"username\":\"username\",\"password\":\"Password1\",\"noteOverview\":[{\"title\":\"title\",\"text\":\"text\",\"created\":\"2022-01-01\",\"edited\":\"2022-01-01\"}]}]";

    Accounts accounts = new Accounts();
    NoteOverview noteOverview = new NoteOverview();
    Note note = new Note("title", "text", localDate, localDate);
    noteOverview.addNote(note);
    User user = new User("username", "Password1", noteOverview);
    accounts.addUser(user);

    final String actualJson = objectMapper.writeValueAsString(accounts);
    assertEqualsIgnoreWhitespace(expectedJson, actualJson);


  }

  /**
   * Tests the deserialization of a JSON string into an Accounts object.
   * 
   * @throws Exception if deserialization fails
   */
  @Test
  public void testAccountsDeserialization() throws Exception {
    final String jsonT = "{\"key\":\"value\"}"; // This is a JSON object, not an array
    final Accounts accountsT = objectMapper.readValue(jsonT, Accounts.class);
    assertNull(accountsT);
    final String json =
        "[{\"username\":\"username\",\"password\":\"Password1\",\"noteOverview\":[{\"title\":\"title\",\"text\":\"text\",\"created\":\"2022-01-01\",\"edited\":\"2022-01-01\"}]}]";
    final Accounts accounts = objectMapper.readValue(json, Accounts.class);
    assertEquals("username", accounts.getUser("username").getUsername());
    assertEquals("Password1", accounts.getUser("username").getPassword());
    assertEquals("title",
        accounts.getUser("username").getNoteOverview().getNotes().get(0).getTitle());

  }
}
