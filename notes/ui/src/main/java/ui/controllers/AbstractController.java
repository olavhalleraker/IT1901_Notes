package ui.controllers;

import dataaccess.NotesAccess; 
import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;         
import javafx.scene.Parent;     
import javafx.scene.Scene;      
import javafx.stage.Stage;
import ui.App;

/**
 * AbstractController is a base class for all controllers in the application, providing common 
 * functionalities and access to the data layer.
 */
public abstract class AbstractController {

  protected NotesAccess dataAccess;

  /**
   * Enum defining the various controllers and their associated FXML files.  
   * This aids in managing the different scenes and their corresponding controllers.
   */
  public enum Controllers {
    LOGIN("Login.fxml", new LoginController()), NOTEOVERVIEW("App.fxml", new AppController()), NOTE(
        "Note.fxml", new NoteController()), NOTE_EDIT("NoteEdit.fxml",
            new NoteEditController()), CREATE_USER("CreateUser.fxml", new CreateUserController());

    private final String fxml;
    private final AbstractController abstractController;

    Controllers(String fxml, AbstractController abstractController) {
      this.fxml = "/ui/" + fxml;
      this.abstractController = abstractController;
    }

    public AbstractController getControllerInstance() {
      return this.abstractController;
    }

    public String getFxmlString() {
      return this.fxml;
    }
  }

  /**
   * Sets the data access object for the controller.
   *
   * @param dataAccess The data access object to be used by the controller.
   */
  public void setDataAccess(NotesAccess dataAccess) {
    this.dataAccess = dataAccess;
  }

  /**
   * Gets the current data access object used by the controller.
   *
   * @return The current data access object.
   */
  public NotesAccess getDataAccess() {
    return this.dataAccess;
  }

  /**
   * Changes the scene on the stage based on the specified controller type. 
   * This method also initializes the controller with necessary data access.
   *
   * @param type The type of controller to switch to.
   * @param event The event that triggered the scene change.
   * @param dataAccess The data access object to be passed to the controller.
   */
  public void setScene(Controllers type, Event event, NotesAccess dataAccess) {

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    try {
      AbstractController controller = type.getControllerInstance();
      FXMLLoader loader = new FXMLLoader();
      loader.setController(controller);
      loader.setLocation(App.class.getResource(type.getFxmlString()));
      controller.setDataAccess(dataAccess);
      Parent parent = loader.load();
      if (controller instanceof AppController) {
        ((AppController) controller).startScene();
      } else if (controller instanceof NoteEditController) {
        ((NoteEditController) controller).loadEditInfo(); 
      }
      Scene newScene = new Scene(parent);
      stage.setScene(newScene);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}