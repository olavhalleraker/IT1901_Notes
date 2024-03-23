package ui.controllers;

import core.NoteOverview;
import core.User;
import core.UserValidation;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * The CreateUserController class manages the user creation interface of the application.
 * It provides functionalities to input new user details and create a user account.
 */
public class CreateUserController extends AbstractController {

  @FXML
  private TextField createUsernameInput;
  @FXML
  private TextField createPasswordInput;
  @FXML
  private TextField confirmPasswordInput;
  @FXML
  private Button createUser;
  @FXML
  private Text errorMessageDisplay;

  private String username;
  private String password;
  private String confirmPassword;

  /**
   * Handles the creation of a new user account when the "Create User" button is clicked.
   * Validates the input fields for the username and passwords.
   * Checks if they meet the specified criteria and that passwords match.
   * If validation passes, a new User object is made and is added to data access layer.
   *
   * @param event The ActionEvent triggered by clicking the "Create User" button.
   * @throws IOException If there is an issue with transitioning scenes.
   */
  @FXML
  private void createUser(ActionEvent event) throws IOException {

    try {
      UserValidation.checkValidUsername(createUsernameInput.getText());
      username = createUsernameInput.getText();
      UserValidation.checkValidPassword(createPasswordInput.getText());
      password = createPasswordInput.getText();
      confirmPassword = confirmPasswordInput.getText().trim();
      UserValidation.checkEqualPassword(password, confirmPassword);

      NoteOverview noteOverview = new NoteOverview(); // empty noteoverview
      User user = new User(username, password, noteOverview);
      getDataAccess().createUser(user);

      setScene(Controllers.LOGIN, event, getDataAccess());

    } catch (IllegalArgumentException e) {
      errorMessageDisplay.setText(e.getMessage());
    }
  }
}
