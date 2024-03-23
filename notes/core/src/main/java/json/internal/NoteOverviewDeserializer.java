package json.internal;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import core.Note;
import core.NoteOverview;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Deserializer for NoteOverview.
 */
public class NoteOverviewDeserializer extends JsonDeserializer<NoteOverview> {
  private final NoteDeserializer noteDeserializer = new NoteDeserializer();

  @Override
  public NoteOverview deserialize(JsonParser jsonParser, DeserializationContext deserContext)
      throws IOException, JacksonException {
    final JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
    if (jsonNode instanceof ArrayNode) {
      final ArrayNode noteArray = (ArrayNode) jsonNode;
      final ArrayList<Note> notes = new ArrayList<>(noteArray.size());
      for (final JsonNode noteNode : noteArray) {
        final Note note = noteDeserializer.deserialize(noteNode);
        notes.add(note);
      }
      return new NoteOverview(notes);
    }
    return null;
  }

  /**
   * Deserialize a JSON representation of NoteOverview
   * from a JsonNode object into a NoteOverview object.
   *
   * @param node The JsonNode containing the JSON representation of NoteOverview. 
   * @return A NoteOverview object deserialized from JSON. 
   * @throws IOException If there is an I/O error during deserialization. 
   * @throws JacksonException If there is an issue with Jackson JSON processing. 
   */
  public NoteOverview deserialize(JsonNode node) throws IOException, JacksonException {
    if (node instanceof ArrayNode) {
      final ArrayNode noteArray = (ArrayNode) node;
      final ArrayList<Note> notes = new ArrayList<>(noteArray.size());
      for (final JsonNode noteNode : noteArray) {
        final Note note = noteDeserializer.deserialize(noteNode);
        notes.add(note);
      }
      return new NoteOverview(notes);
    }
    return null;
  }
}
