package ui;

import java.io.IOException;
import java.util.Properties;

/**
 * NotesConfig is responsible for loading access to configuration properties for the application.
 */
public class NotesConfig {
  private Properties properties;

  /**
   * Constructor for NotesConfig.
   */
  public NotesConfig() {
    this.properties = new Properties();

    try {
      properties.load(App.class.getClassLoader().getResourceAsStream("notes.properties"));
    } catch (IOException e) {
      throw new IllegalStateException("Could not load notes.properties");
    }
  }

  /**
   * With a key, value from notes.properties will be returned.
   *
   * @param key the key
   * @return the value
   */
  public String getProperty(String key) {
    return properties.getProperty(key);
  }
}
