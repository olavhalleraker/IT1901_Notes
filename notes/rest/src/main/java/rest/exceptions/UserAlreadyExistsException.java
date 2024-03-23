package rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when user is not found.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User already exist")
public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String message) {
    super(message);
  }

  public UserAlreadyExistsException() {
    super("No matching note exists");
  }

}
