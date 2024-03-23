package core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class that tests the UserValidation class
 */
public class UserValidationTest {

  private User testUser;
  private NoteOverview noteOverview;

  /**
   * Set up for each test in this class
   */
  @BeforeEach
  public void setUp() {
    testUser = new User("Username", "Pasword123", noteOverview);
  }

  /**
   * Tests the constructor in the class
   */
  @Test
  public void testDefaultConstructor() {
    UserValidation userVal = new UserValidation(); // Create an instance using the default
                                                   // constructor

    // Add assertions to check the default state of the object
    assertNotNull(userVal); // Make sure the object is not null
    // Add more assertions as needed to verify the default state of the object
  }

  /**
   * Tests the usercredentials method in the class
   */
  @Test
  public void testUserCredentials() {

    Assertions.assertThrows(IllegalArgumentException.class, () -> testUser.setUsername("2"));
    String password = testUser.getPassword();
    Assertions.assertThrows(IllegalArgumentException.class, () -> testUser.setPassword("password"));

    Assertions.assertThrows(IllegalArgumentException.class, () -> testUser.setUsername(""));
    Assertions.assertThrows(IllegalArgumentException.class, () -> testUser.setPassword(""));
    String username = testUser.getUsername();

    Assertions.assertDoesNotThrow(() -> UserValidation.checkValidUser(username, password));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> UserValidation.checkEqualPassword("password1", "password2"));
    Assertions
        .assertDoesNotThrow(() -> UserValidation.checkEqualPassword("Password1", "Password1"));

    Accounts accounts = new Accounts();
    Assertions.assertThrows(IllegalArgumentException.class, () -> UserValidation
        .isNotExistingUser(testUser.getUsername(), testUser.getPassword(), accounts));

    accounts.addUser(testUser);
    Assertions.assertDoesNotThrow(() -> UserValidation.isValidLogin(username, password, accounts));
    Assertions.assertDoesNotThrow(() -> UserValidation.isNotExistingUser(testUser.getUsername(),
        testUser.getPassword(), accounts));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> UserValidation.isValidLogin("Username", "Password123", accounts));

    assertThrows(IllegalArgumentException.class, () -> UserValidation.allFieldsEmpty("", ""));
    assertDoesNotThrow(() -> UserValidation.allFieldsEmpty("", "Password123"));
    assertDoesNotThrow(() -> UserValidation.allFieldsEmpty("Username", ""));
    assertDoesNotThrow(() -> UserValidation.allFieldsEmpty("Username", "Password123"));

  }

}
