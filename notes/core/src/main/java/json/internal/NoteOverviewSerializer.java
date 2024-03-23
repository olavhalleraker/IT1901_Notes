package json.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Note;
import core.NoteOverview;
import java.io.IOException;

/**
 * Serializer for NoteOverview.
 */
public class NoteOverviewSerializer extends JsonSerializer<NoteOverview> {

  @Override
  public void serialize(NoteOverview noteOverview, JsonGenerator jsonGen,
      SerializerProvider provider) throws IOException {
    jsonGen.writeStartArray();
    for (Note note : noteOverview.getNotes()) {
      jsonGen.writeObject(note);
    }
    jsonGen.writeEndArray();
  }
}
