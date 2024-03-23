package json.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.User;
import java.io.IOException;

/**
 * Serializer for User.
 */
public class UserSerializer extends JsonSerializer<User> {

  public static final String NOTEOVERVIEW_FIELD_NAME = "noteOverview";
  public static final String PASSWORD_FIELD_NAME = "password";
  public static final String USERNAME_FIELD_NAME = "username";

  
  // Format: {username: username, password: password, noteOverview: [notes]}
  @Override
  public void serialize(User user, JsonGenerator jsonGen, SerializerProvider serializers)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeFieldName(USERNAME_FIELD_NAME);
    jsonGen.writeString(user.getUsername());
    jsonGen.writeFieldName(PASSWORD_FIELD_NAME);
    jsonGen.writeString(user.getPassword());
    jsonGen.writeFieldName(NOTEOVERVIEW_FIELD_NAME);
    jsonGen.writeObject(user.getNoteOverview());
    jsonGen.writeEndObject();
  }
}
