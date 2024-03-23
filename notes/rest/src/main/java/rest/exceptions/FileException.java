package rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when user is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such file")
public class FileException extends RuntimeException {

  public FileException(String message) {
    super(message);
  }

  public FileException() {
    super("No such file");
  }

}
