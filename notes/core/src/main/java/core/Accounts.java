package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that stores users.
 */
public class Accounts {
  protected final List<User> accounts = new ArrayList<>();

  /**
   * Access method for accounts.
   *
   * @return the accounts
   */
  public List<User> getAccounts() {
    return new ArrayList<>(accounts);
  }

  /**
   * Adds new user to this list of users.
   *
   * @param user the user to add
   * @throws IllegalStateException if the user already exists.
   */
  public void addUser(User user) {
    if (containsUser(user)) {
      throw new IllegalStateException(Errors.EXISTING_USER.getMessage());
    }
    this.accounts.add(user);
  }

  /**
   * Removes already existing user.
   *
   * @param user to remove
   * @throws IllegalStateException if the user dosen't already exists.
   */
  public void removeUser(User user) {
    if (!containsUser(user)) {
      throw new IllegalArgumentException(Errors.NOT_EXISTING_USER.getMessage());
    }
    this.accounts.remove(user);
  }

  /**
   * Iterator to iterate over objects in list.
   *
   * @return iterator of accounts
   */
  public Iterator<User> iterator() {
    return accounts.iterator();
  }

  /**
   * Checks if the given user already exists in the list of users.
   *
   * @param user to check
   * @return true or false based on if the user exists
   */
  public boolean containsUser(User user) {
    return accounts.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));
  }

  /**
   * Checks if the user login is valid.
   *
   * @param username username to check
   * @param password password to check
   * @return boolean
   */
  public boolean checkValidUserLogin(String username, String password) {
    User user = getUser(username);

    if (user == null) {
      return false; // user doesnt exist
    }
    return user.getPassword().equals(password);
  }

  /**
   * Gets the user by username and password.
   *
   * @param username the username
   * @param password the password
   * @return the user
   */
  public User getUser(String username, String password) {
    User user = null;

    for (User u : accounts) {
      if (u.getUsername().equals(username)) {
        user = u;
      }
    }
    if (user == null) {
      return null;
    }
    if (user.getPassword().equals(password)) {
      return user;
    }
    return null;
  }

  /**
   * get accounts by username.
   *
   * @param username the username
   * @return accounts if they exist, null else
   */

  public User getUser(String username) {
    return getAccounts().stream().filter(u -> u.getUsername().equals(username)).findAny()
        .orElse(null);
  }

}
