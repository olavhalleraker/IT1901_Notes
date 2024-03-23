package json.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Note;
import java.io.IOException;

/**
 * Serializer for Note.
 */
public class NoteSerializer extends JsonSerializer<Note> {
  public static final String TITLE_FIELD_NAME = "title";
  public static final String TEXT_FIELD_NAME = "text";
  public static final String CREATED_FIELD_NAME = "created";
  public static final String EDITED_FIELD_NAME = "edited";

  // Format: {title: title, text: text, created: created date, edited: edited date}
  @Override
  public void serialize(Note note, JsonGenerator jsonGen, SerializerProvider provider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeFieldName(TITLE_FIELD_NAME);
    jsonGen.writeString(note.getTitle());
    jsonGen.writeFieldName(TEXT_FIELD_NAME);
    jsonGen.writeString(note.getText());
    jsonGen.writeFieldName(CREATED_FIELD_NAME);
    jsonGen.writeString(note.getCreatedDate().toString());
    jsonGen.writeFieldName(EDITED_FIELD_NAME);
    jsonGen.writeString(note.getEditedDate().toString());
    jsonGen.writeEndObject();
  }
}
