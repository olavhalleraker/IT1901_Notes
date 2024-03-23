package json.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.NoteOverview;
import core.User;
import java.io.IOException;

/**
 * Deserializer for User.
 */
public class UserDeserializer extends JsonDeserializer<User> {
  private final NoteOverviewDeserializer noteOverviewDeserializer = new NoteOverviewDeserializer();

  @Override
  public User deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
    JsonNode node = jsonParser.readValueAsTree();
    String password = node.get(UserSerializer.PASSWORD_FIELD_NAME).asText();
    String username = node.get(UserSerializer.USERNAME_FIELD_NAME).asText();
    NoteOverview noteOverview = noteOverviewDeserializer.deserialize(jsonParser, ctxt);

    return new User(username, password, noteOverview);
  }

  /**
   * Deserialize a JSON representation of a User object from JsonNode object.
   *
   * @param node The JsonNode containing the JSON representation of User.
   * @return A User object deserialized from JSON.
   * @throws IOException IOException If there is an I/O error during deserialization.
   */
  public User deserialize(JsonNode node) throws IOException {
    ObjectNode objectNode = (ObjectNode) node;
    String password = objectNode.get(UserSerializer.PASSWORD_FIELD_NAME).asText();
    String username = objectNode.get(UserSerializer.USERNAME_FIELD_NAME).asText();
    NoteOverview noteOverview = noteOverviewDeserializer.deserialize(
            objectNode.get(UserSerializer.NOTEOVERVIEW_FIELD_NAME));

    return new User(username, password, noteOverview);
  }
}
