package ui;

import dataaccess.RemoteNotesAccess;
import java.net.URI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controllers.LoginController;

/**
 * The RemoteApp class is a JavaFX application that initializes and displays the login interface.
 */
public class RemoteApp extends Application {

  private NotesConfig notesConfig = new NotesConfig();

  /**
   * Starts the primary stage of the application.
   * This method sets up the initial scene using an FXML loader and displays the login screen.
   * It initializes the RemoteNotesAccess with the server URI from the configuration.
   *
   * @param stage The primary stage for this application.
   * @throws Exception if there is an error during the setup process.
   */
  @Override
  public void start(Stage stage) throws Exception {
    URI uri = new URI(notesConfig.getProperty("serverURI"));

    final FXMLLoader loader = new FXMLLoader();

    LoginController controller = new LoginController();
    controller.setDataAccess(new RemoteNotesAccess(uri));
    loader.setController(controller);
    loader.setLocation(App.class.getResource("/ui/Login.fxml"));
    final Parent parent = loader.load();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
    * The main method for launching the JavaFX application.
    *
    * @param args Command line arguments.
    */
  public static void main(String[] args) {
    launch();
  }
}