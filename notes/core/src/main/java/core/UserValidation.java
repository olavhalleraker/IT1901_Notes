package core;

import java.util.regex.Pattern;

/**
 * Class for validating users.
 */
public class UserValidation {

  private static final Pattern USERNAME_REGEX = Pattern.compile("^([a-åA-Å0-9]{3,})$");
  private static final Pattern PASSWORD_REGEX =
      Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[a-zA-Z]).{8,}$");

  /**
   * Check if string is valid according to REGEX pattern.
   *
   * @param regex to match with
   * @param string to match
   * @return boolean
   */
  private static boolean isValidRegex(Pattern regex, String string) {
    return regex.matcher(string).matches();
  }


  /**
   * Validates username.
   *
   * @param username to check
   * @throws IllegalArgumentException if field is empty or if username is invalid
   */
  public static void checkValidUsername(String username) {
    if (isEmpty(username)) {
      throw new IllegalArgumentException(Errors.USERNAME_FIELD_EMPTY.getMessage());
    }
    if (!isValidRegex(USERNAME_REGEX, username)) {
      throw new IllegalArgumentException(Errors.INVALID_USERNAME.getMessage());
    }
  }

  /**
   * Validates the password.
   *
   * @param password to check
   * @throws IllegalArgumentException if password is empty or invalid
   */
  public static void checkValidPassword(String password) {
    if (isEmpty(password)) {
      throw new IllegalArgumentException(Errors.PWD_FIELD_EMPTY.getMessage());
    }
    if (!isValidRegex(PASSWORD_REGEX, password)) {
      throw new IllegalArgumentException(Errors.INVALID_PWD.getMessage());
    }
  }

  /**
   * Check if user exists.
   *
   * @param username to check
   * @param password to check
   * @param accounts check if user exists in accounts
   * 
   * @throws IllegalArgumentException if user is not registerd
   */
  public static void isNotExistingUser(String username, String password, Accounts accounts) {
    if (accounts.getUser(username, password) == null) {
      throw new IllegalArgumentException(Errors.NOT_REGISTERED.getMessage());
    }
  }

  /**
   * Checks if an attempt to create new user is valid.
   *
   * @param username users username
   * @param password users password
   */
  public static void checkValidUser(String username, String password) {
    allFieldsEmpty(username, password);
    checkValidUsername(username);
    checkValidPassword(password);
  }

  /**
   * Checks if two passwords are equal.
   *
   * @param password to check
   * @param confirmedPassword whick is correct
   */
  public static void checkEqualPassword(String password, String confirmedPassword) {
    if (!password.equals(confirmedPassword)) {
      throw new IllegalArgumentException(Errors.NOT_EQUAL_PASSWORD.getMessage());
    }
  }

  /**
   * Checks if all fields are empty.
   *
   * @param username to check
   * @param password to check
   * @throws IllegalArgumentException if all fields are empty
   */
  public static void allFieldsEmpty(String username, String password) {
    if (isEmpty(username) && isEmpty(password)) {
      throw new IllegalArgumentException(Errors.EVERYTHING_EMPTY.getMessage());
    }
  }

  /**
   * Checks if user exists.
   *
   * @param username to check
   * @param password to check
   * @param accounts check if user exists in accounts
   * @throws IllegalArgumentException if user dont exist
   */
  public static void isValidLogin(String username, String password, Accounts accounts) {
    // checks if all fields are filled out
    allFieldsEmpty(username, password);

    // checks if username field is empty
    // if (username == null || username.trim().isEmpty()) {
    //   throw new IllegalArgumentException(Errors.USERNAME_FIELD_EMPTY.getMessage());
    // }
    checkValidUsername(username);

    // checks if password fiels is empty.
    if (password == null || password.trim().isEmpty()) {
      throw new IllegalArgumentException(Errors.PWD_FIELD_EMPTY.getMessage());
    }

    // checks if username exists in system
    User user = accounts.getUser(username);
    if (user == null) {
      throw new IllegalArgumentException(Errors.NOT_REGISTERED.getMessage());
    }

    //checks if password is valid and matches the username
    if (!accounts.checkValidUserLogin(username, password)) {
      throw new IllegalArgumentException(Errors.WRONG_PASSWORD.getMessage());
    }
  }

  /**
   * Checks if a string is empty.
   *
   * @param string to check if empty
   * @return true if empty, else false
   */
  public static boolean isEmpty(String string) {
    if (string.equals("")) {
      return true;
    }
    return false;
  }
}
