package ui.controllers;

import core.UserValidation;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * The LoginController class manages the login interface of the application.
 * It handles user authentication and navigation to different parts of the application.
 */
public class LoginController extends AbstractController {

  /**
   * Input field.
   */
  @FXML
  private TextField usernameInput;
  
  /**
   * Input field.
   */
  @FXML
  private TextField passwordInput;

  @FXML
  private Text errorMessage;

  /**
   * Button to initialize login.
   */
  @FXML
  private Button loginButton;

  @FXML
  private Button createUserButton;


  /**
   * Handles the action triggered by the "Create User" button.
   * Navigates to the user creation interface.
   *
   * @param event The ActionEvent triggered by clicking the "Create User" button.
   * @throws IOException If there is an issue with transitioning scenes.
   */
  @FXML
  public void createUserAction(ActionEvent event) throws IOException {
    setScene(Controllers.CREATE_USER, event, getDataAccess());
  }

  /**
   * Handles the user login action Initiates the user login process by first accessing and
   * retrieving user information .json-file Displays any issues directly on the UI.
   *
   * @param event when clicking on "logg inn"
   * @throws IOException if something goes wrong when reading from file
   */
  @FXML
  public void loginUserAction(ActionEvent event) throws IOException {
    String username = usernameInput.getText();
    String password = passwordInput.getText();
    try {
      UserValidation.isValidLogin(username, password, getDataAccess().readAccounts());

      dataAccess.userLogin(username, password);

      setScene(Controllers.NOTEOVERVIEW, event, getDataAccess());

    } catch (IllegalArgumentException e) {
      errorMessage.setText(e.getMessage());
    }
  }
}