package json.internal;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Note;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Deserializer for Note class.
 */
public class NoteDeserializer extends JsonDeserializer<Note> {

  @Override
  public Note deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JacksonException {
    JsonNode node = jsonParser.readValueAsTree();
    String title = node.get(NoteSerializer.TITLE_FIELD_NAME).asText();
    String text = node.get(NoteSerializer.TEXT_FIELD_NAME).asText();
    String createdText = node.get(NoteSerializer.CREATED_FIELD_NAME).asText();
    String editedText = node.get(NoteSerializer.EDITED_FIELD_NAME).asText();
    LocalDate created = LocalDate.parse(createdText);
    LocalDate edited = LocalDate.parse(editedText);

    return new Note(title, text, created, edited);
  }

  /**
   * Method for deserializing JsonNode to a Note.
   *
   * @param node jsonNode
   * @return note
   * @throws IOException if IO exception
   * @throws JacksonException if not possible to deserialize
   */
  public Note deserialize(JsonNode node) throws IOException, JacksonException {
    ObjectNode objectNode = (ObjectNode) node;
    String title = objectNode.get(NoteSerializer.TITLE_FIELD_NAME).asText();
    String text = objectNode.get(NoteSerializer.TEXT_FIELD_NAME).asText();
    String createdText = objectNode.get(NoteSerializer.CREATED_FIELD_NAME).asText();
    String editedText = objectNode.get(NoteSerializer.EDITED_FIELD_NAME).asText();
    LocalDate created = LocalDate.parse(createdText);
    LocalDate edited = LocalDate.parse(editedText);
    return new Note(title, text, created, edited);
  }
}
