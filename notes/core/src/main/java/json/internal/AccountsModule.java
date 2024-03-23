package json.internal;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import core.Accounts;
import core.Note;
import core.NoteOverview;
import core.User;

/**
 * Jackson module for Accounts.
 */
public class AccountsModule extends Module {

  @Override
  public String getModuleName() {
    return "AccountsModule";
  }

  @Override
  public Version version() {
    return Version.unknownVersion();
  }

  private final SimpleDeserializers deserializers = new SimpleDeserializers();
  private final SimpleSerializers serializers = new SimpleSerializers();

  /**
   * Constructor that adds all the seria- deserializers.
   *
   */
  public AccountsModule() {
    serializers.addSerializer(Note.class, new NoteSerializer());
    serializers.addSerializer(NoteOverview.class, new NoteOverviewSerializer());
    serializers.addSerializer(User.class, new UserSerializer());
    serializers.addSerializer(Accounts.class, new AccountsSerializer());
    deserializers.addDeserializer(Note.class, new NoteDeserializer());
    deserializers.addDeserializer(NoteOverview.class, new NoteOverviewDeserializer());
    deserializers.addDeserializer(User.class, new UserDeserializer());
    deserializers.addDeserializer(Accounts.class, new AccountsDeserializer());
  }

  @Override
  public void setupModule(SetupContext context) {
    context.addSerializers(serializers);
    context.addDeserializers(deserializers);
  }
}
