package json.internal;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Accounts;
import core.User;
import java.io.IOException;

/**
 * Serializer for Accounts.
 */
public class AccountsSerializer extends JsonSerializer<Accounts> {

  @Override
  public void serialize(Accounts accounts, JsonGenerator jsonGen, SerializerProvider provider)
      throws IOException {
    jsonGen.writeStartArray();
    for (User user : accounts.getAccounts()) {
      jsonGen.writeObject(user);
    }
    jsonGen.writeEndArray();
  }
}
