package core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class that tests the Accounts class
 */
public class AccountsTest {

  private Accounts accounts;
  private User user1;
  private User user2;

  /**
   * Setup before each test in the class
   */
  @BeforeEach
  public void setUp() {
    accounts = new Accounts();
    NoteOverview noteOverview1 = new NoteOverview();
    NoteOverview noteOverview2 = new NoteOverview();

    user1 = new User("defaultUserONE", "defaultPassword1", noteOverview1); // Changed this line
    user2 = new User("defaultUserTWO", "defaultPassword2", noteOverview2); // Changed this line

    accounts.addUser(user1);
    accounts.addUser(user2);
  }

  /**
   * Test the getAccounts method
   */
  @Test
  public void getAccountsTest() {
    assertNotNull(accounts.getAccounts());
  }

  /**
   * Test the addUser method
   */
  @Test
  public void testAddUser() {
    NoteOverview noteOverview = new NoteOverview();
    User newUser = new User("testUser", "testPassword1", noteOverview);

    // Try adding a new user, should succeed because user does not exist yet.
    assertDoesNotThrow(() -> accounts.addUser(newUser));

    // Try adding the same user again, should throw an exception because user
    // already exists.
    assertThrows(IllegalStateException.class, () -> accounts.addUser(newUser));

    // Test if accounts now contains newUser
    assertTrue(accounts.containsUser(newUser));
  }

  /**
   * Test the containsUser method
   */
  @Test
  public void containsTest() {
    assertTrue(accounts.containsUser(user1));
  }

  /**
   * Test the removeUser method
   */
  @Test
  public void testRemoveUser() {
    NoteOverview noteOverview = new NoteOverview();
    User user = new User("testUser", "testPassword1", noteOverview);

    // Try removing a user that hasn't been added, should throw an exception.
    assertThrows(IllegalArgumentException.class, () -> accounts.removeUser(user));

    // Now, add the user and then try removing, should succeed.
    accounts.addUser(user);
    assertDoesNotThrow(() -> accounts.removeUser(user));
  }

  /**
   * Test the iterator in the class
   */
  @Test
  public void testIterator() {
    Iterator<User> userIterator = accounts.iterator();
    assertTrue(userIterator.hasNext());
    assertEquals(user1, userIterator.next());
    assertTrue(userIterator.hasNext());
    assertEquals(user2, userIterator.next());
    assertFalse(userIterator.hasNext());
  }

  /**
   * Test the getUser method
   */
  @Test
  public void testGetUser() {

    assertEquals(user1, accounts.getUser("defaultUserONE"));
    assertEquals(user1, accounts.getUser("defaultUserONE", "defaultPassword1"));

    // Wrong password to username
    assertEquals(null, accounts.getUser("defaultUserTWO", "defaultPassword1"));
  }

  /**
   * Test the validUserLogin method
   */
  @Test
  public void testValidUserLogin() {
    NoteOverview noteOverview = new NoteOverview();
    String username = "validUser";
    String password = "validPassword1";

    User validUser = new User(username, password, noteOverview);
    accounts.addUser(validUser);

    // Test if login valid for a valid user.
    assertTrue(accounts.checkValidUserLogin(username, password));

    // Test login valid for invalid password.
    assertFalse(accounts.checkValidUserLogin(username, "invalidPassword1"));

    // Test login valid for nonexistent user.
    assertFalse(accounts.checkValidUserLogin("invalidUser", password));
  }


}
