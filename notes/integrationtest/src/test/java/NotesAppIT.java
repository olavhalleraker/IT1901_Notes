

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;


import core.Accounts;
import core.Note;
import core.NoteOverview;
import core.User;
import dataaccess.NotesAccess;
import dataaccess.RemoteNotesAccess;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.App;
import ui.NotesConfig;
import ui.controllers.LoginController;
import org.testfx.framework.junit5.ApplicationTest;



public class NotesAppIT extends ApplicationTest {

  private List<User> testUserList = new ArrayList<User>();
  private LoginController loginController;
  private NotesAccess dataAccess;
  private NotesConfig config;


  @Override
  public void start(Stage stage) throws IOException, URISyntaxException {
    FXMLLoader loader = new FXMLLoader();
    loginController = new LoginController();
    loader.setController(loginController);
    loader.setLocation(App.class.getResource("/ui/Login.fxml"));
    config = new NotesConfig();
    try {
      URI file = new URI(config.getProperty("serverURI"));
      dataAccess = new RemoteNotesAccess(file);

    } catch (IllegalThreadStateException e) {

    }
    loginController.setDataAccess(dataAccess);
    final Parent parent = loader.load();


    stage.setScene(new Scene(parent));
    stage.show();
  }


  @BeforeAll
  public static void setupHeadless() {
    App.supportHeadless();
  }

  @BeforeEach
  void generateData() {
    createTestUsers();
  }

  @AfterAll
  public static void tearDown() {
    Path.of(System.getProperty("user.home") + "springbootserver-notes.json").toFile().delete();
  }

  @Test
  @DisplayName("Client can read all users")
  void clientCanReadAllUsers() {
    try {
      Accounts accounts = loginController.getDataAccess().readAccounts();
      List<User> readAccounts = accounts.getAccounts();

      for (int i = 0; i < testUserList.size(); i++) {
        assertEquals(testUserList.get(i).getUsername(), readAccounts.get(i).getUsername());
      }
    } catch (IOException e) {
      fail();
    }
  }

  private void createTestUsers() {
    try {
      NoteOverview noteoverview = new NoteOverview();
      NoteOverview noteoverview2 = new NoteOverview();
      User user = new User("userone", "Password123!", noteoverview);
      User user2 = new User("usertwo", "Password1", noteoverview2);
      Note note1 = new Note("TestNote 1", "Text");
      user.addNote(note1);
      Note note2 = new Note("TestNote 2", "Text");
      user2.addNote(note2);
      testUserList.add(user);
      testUserList.add(user2);
      dataAccess.createUser(user);
      dataAccess.createUser(user2);

    } catch (Exception e) {
      System.err.println(e.getMessage());
      fail();
    }

  }
}
