package ui;

import dataaccess.LocalNotesAccess;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controllers.LoginController;
 
/**
 * The App class is the main JavaFX application class which sets up and displays the primary stage
 * of the user interface.
 */
public class App extends Application {

  /**
   * Starts the primary stage of the JavaFX application.
   */
  @Override
  public void start(Stage stage) throws IOException {
    final FXMLLoader loader = new FXMLLoader();

    LoginController controller = new LoginController();
    controller.setDataAccess(new LocalNotesAccess()); 
    loader.setController(controller);
    loader.setLocation(App.class.getResource("/ui/Login.fxml"));
    final Parent parent = loader.load();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Configures the application to run in a headless environment, if required.
   */
  public static void supportHeadless() {
    if (Boolean.getBoolean("headless")) {
      System.setProperty("testfx.robot", "glass");
      System.setProperty("testfx.headless", "true");
      System.setProperty("prism.order", "sw");
      System.setProperty("prism.text", "t2k");
      System.setProperty("java.awt.headless", "true");
    }
  }
}
