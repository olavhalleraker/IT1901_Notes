package json.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import core.Accounts;
import core.User;
import java.io.IOException;

/**
 * Deserializer for Accounts.
 */
public class AccountsDeserializer extends JsonDeserializer<Accounts> {
  private final UserDeserializer userDeserializer = new UserDeserializer();

  @Override
  public Accounts deserialize(JsonParser jsonParser, DeserializationContext deserContext)
      throws IOException {
    final JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
    Accounts accounts = new Accounts();

    if (jsonNode instanceof ArrayNode) {
      ArrayNode userArray = (ArrayNode) jsonNode;
      for (JsonNode userNode : userArray) {
        User user = userDeserializer.deserialize(userNode);
        accounts.addUser(user);
      }
      return accounts;
    }

    return null;
  }
}
