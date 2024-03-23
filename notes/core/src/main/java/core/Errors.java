package core;

/**
 * Enum representing error messages related to various validation and application logic.
 * Error messages are used to provide informative feedback to users or handle exceptional cases.
 */

public enum Errors {

  /**
  * Error enum constants that represent their designated errors.
  */
  USERNAME_FIELD_EMPTY("Please enter a username."),
  PWD_FIELD_EMPTY("Please enter a password."),
  EVERYTHING_EMPTY("All fields must be filled out."),
  INVALID_USERNAME("Name should only contain letters, and be atleast five letters.."),
  INVALID_PWD("Invalid password, must be at least 8 characters"
    + " and contain at least 1 digit and 1 lower and uppercase letter."),
  WRONG_PASSWORD("Wrong password"),
  NOT_REGISTERED("This user is not registered."),
  NOT_EQUAL_PASSWORD("Passwords does not match."),
  NOT_EQUAL_USERNAME("Username do not match"),
  EQUAL_NOTE_TITLE("Note with that title already exist"),
  NOTE_DOESNT_EXIST("This note was not deleted since it doesn't exist"),
  INVALID_CREATE_DATE("Note cannot be created after it was edited"),
  EXISTING_USER("User already exists"),
  NOT_EXISTING_USER("User does not exist!"),
  SELECT_NOTE("Pick a note"),
  EMPTY_TITLE("Please write a title"),
  EMPTY_TEXT("Please write a text"),
  INVALID_INDEX("Note doesn't exist");

  private final String errorMessage;

  /**
  * Constructs an Errors enum constant with the specified error message.
  *
  * @param errorMessage The error message associated with the enum constant.
  */
  Errors(final String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
  * Gets the error message associated with the enum constant.
  *
  * @return The error message.
  */
  public String getMessage() {
    return errorMessage;
  }
}
