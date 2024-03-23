package dataaccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Accounts;
import core.Note;
import core.NoteOverview;
import core.User;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import json.AccountsPersistence;

/**
 * RemoteNotesAccess provides data access functionalities for a remote note-taking application.
 * It handles interactions with a server for operations, 
 * like user authentication, note creation, deletion, and sorting.
 */
public class RemoteNotesAccess implements NotesAccess {

  private final URI uri;
  private ObjectMapper objectMapper;
  private User user;
  private int selectedIndex;

  /**
   * Constructor for RemoteNotesAccess.
   *
   * @param uri The base URI for the remote server. 
   */
  public RemoteNotesAccess(final URI uri) {
    this.uri = uri;
    this.objectMapper = AccountsPersistence.getObjectMapper();
  }

  /**
   * Creates a URI by resolving the input string against the uri for fetching from the server.
   *
   * @param serverUri the path.
   * @return the URI on the server with the given path.
   */
  public URI resolveUriAccounts(String serverUri) {
    return uri.resolve(serverUri);
  }

  /**
   * Sends HTTP GET message to fetch Accounts object from server.
   */ 
  @Override
  public Accounts readAccounts() {
    Accounts accounts;
    String getMappingPath = "accounts";
    HttpRequest httpRequest = HttpRequest.newBuilder(resolveUriAccounts(getMappingPath))
        .header("Accept", "application/json").GET().build();

    try {
      final HttpResponse<String> httpResponse =
          HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());

      accounts = objectMapper.readValue(httpResponse.body(), Accounts.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    return accounts;
  }

  /**
   * Sends HTTP PUT message to create new user and add it to Accounts.
   */
  @Override
  public void createUser(User user) {
    String putMappingPath = "create-user";
    try {
      String json = objectMapper.writeValueAsString(user);
      HttpRequest httpRequest = HttpRequest.newBuilder(resolveUriAccounts(putMappingPath))
          .header("Accept", "application/json").header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(json)).build();

      HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());

    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends HTTP POST message to authenticate login info.
   */
  @Override
  public User userLogin(String username, String password) {
    String postMappingPath = "authenticate-user?";
    String key1 = "username=";
    String value1 = username + "&";
    String key2 = "password=";
    String value2 = password;

    try {
      HttpRequest httpRequest = HttpRequest
          .newBuilder(resolveUriAccounts(postMappingPath + key1 + value1 + key2 + value2))
          .header("Accept", "application/json")
          .POST(BodyPublishers.ofString(username + "|" + password)).build();
      final HttpResponse<String> httpResponse =
          HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
      this.user = objectMapper.readValue(httpResponse.body(), User.class);
      return objectMapper.readValue(httpResponse.body(), User.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get method that returns logged in user.
   *
   * @throws IllegalArgumentException if user is not logged in.
   */
  @Override
  public User getLoggedInUser() {
    if (user == null) {
      throw new IllegalArgumentException("User not logged in"); 
    }
    return userLogin(user.getUsername(), user.getPassword());
  }

  /**
   * Sends HTTP PUT message to create new note and add it to the users noteoverview.
   */
  @Override
  public void addNote(Note note) {
    String putMappingPath = "create-note?";
    String key = "username=";
    String value = getLoggedInUser().getUsername();
    try {
      String json = objectMapper.writeValueAsString(note);
      HttpRequest httpRequest =
          HttpRequest.newBuilder(resolveUriAccounts(putMappingPath + key + value))
              .header("Accept", "application/json").header("Content-Type", "application/json")
              .PUT(BodyPublishers.ofString(json)).build();

      HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());

    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Note: method not used. TBA.
   */
  @Override
  public void updateNotes(String username) {
    String postMappingPath = "user/edit?";
    String key = "username=";
    String value = username;

    try {
      HttpRequest httpRequest =
          HttpRequest.newBuilder(resolveUriAccounts(postMappingPath + key + value))
              .header("Accept", "application/json").POST(BodyPublishers.ofString(username)).build();

      HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());

    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends HTTP GET message to get noteoverview of the logged in user.
   */
  @Override
  public NoteOverview getUserNoteOverview() {
    String getMappingPath = "user/noteOverview?";
    String key = "username=";
    String value = getLoggedInUser().getUsername();
    NoteOverview noteOverview;

    HttpRequest httpRequest =
        HttpRequest.newBuilder(resolveUriAccounts(getMappingPath + key + value))
            .header("Accept", "application/json").GET().build();

    try {
      final HttpResponse<String> httpResponse =
          HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
      noteOverview = objectMapper.readValue(httpResponse.body(), NoteOverview.class);
      return noteOverview;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends HTTP DELETE message to delete a note by index.
   */
  @Override
  public void deleteNote(int index) {
    String deleteMappingPath = "delete-note?";
    String key1 = "username=";
    String value1 = getLoggedInUser().getUsername();
    String key2 = "&index=";
    String value2 = Integer.toString(index);

    HttpRequest httpRequest = HttpRequest
        .newBuilder(resolveUriAccounts(deleteMappingPath + key1 + value1 + key2 + value2))
        .header("Accept", "application/json").DELETE().build();

    try {
      HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends HTTP POST message to sort notes by created date.
   */
  @Override
  public void sortNotesByCreatedDate() {
    String postMapping = "user/sort-created?";
    String key = "username=";
    String value = getLoggedInUser().getUsername();

    HttpRequest httpRequest = HttpRequest.newBuilder(resolveUriAccounts(postMapping + key + value))
        .header("Accept", "application/json")
        .POST(BodyPublishers.ofString(getLoggedInUser().getUsername())).build();

    try {
      HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends HTTP POST message to sort notes by title.
   */
  @Override
  public void sortNotesByTitle() {
    String postMapping = "user/sort-title?";
    String key = "username=";
    String value = getLoggedInUser().getUsername();

    HttpRequest httpRequest = HttpRequest.newBuilder(resolveUriAccounts(postMapping + key + value))
        .header("Accept", "application/json")
        .POST(BodyPublishers.ofString(getLoggedInUser().getUsername())).build();

    try {
      HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends HTTP POST message to sort notes by edited date.
   */
  @Override
  public void sortNotesByLastEditedDate() {
    String postMapping = "user/sort-edited?";
    String key = "username=";
    String value = getLoggedInUser().getUsername();

    HttpRequest httpRequest = HttpRequest.newBuilder(resolveUriAccounts(postMapping + key + value))
        .header("Accept", "application/json")
        .POST(BodyPublishers.ofString(getLoggedInUser().getUsername())).build();

    try {
      HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends HTTP GET message to fetch note by username and index.
   */
  @Override
  public Note getNote(String username, int index) {
    String getMappingPath = "user/note?";
    String key1 = "username=";
    String value1 = username + "&";
    String key2 = "index=";
    String value2 = Integer.toString(index);
    Note note;

    HttpRequest httpRequest =
        HttpRequest.newBuilder(resolveUriAccounts(getMappingPath + key1 + value1 + key2 + value2))
            .header("Accept", "application/json").GET().build();

    try {
      final HttpResponse<String> httpResponse =
          HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
      note = objectMapper.readValue(httpResponse.body(), Note.class);
      return note;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sets the index of the selected note.
   */
  @Override
  public void setSelectedIndex(int index) {
    this.selectedIndex = index;
  }

  /**
   * Gets the index of the selected note.
   */
  @Override
  public int getSelectedIndex() {
    return this.selectedIndex;
  }

  /**
   * Sets the application to test mode by sending an HTTP POST request to the 'test-mode' endpoint.
   *
   * @throws RuntimeException if there is an IOException or InterruptedException.
   */
  @Override
  public void setTestMode() {
    String postMappingPath = "test-mode";

    HttpRequest httpRequest = HttpRequest.newBuilder(resolveUriAccounts(postMappingPath))
        .header("Accept", "application/json").POST(BodyPublishers.noBody()).build();
    try {
      HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Switches the application back to normal mode from test mode,
   * by sending an HTTP POST request to the 'normal-mode' endpoint.
   *
   * @throws RuntimeException if there is an IOException or InterruptedException.
   */
  public void setNormalMode() {
    String postMappingPath = "normal-mode";

    HttpRequest httpRequest = HttpRequest.newBuilder(resolveUriAccounts(postMappingPath))
        .header("Accept", "application/json").POST(BodyPublishers.noBody()).build();
    try {
      HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
