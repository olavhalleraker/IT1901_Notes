package rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when user is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Note not found")
public class NoteNotFoundException extends RuntimeException {

  public NoteNotFoundException(String message) {
    super(message);
  }

  public NoteNotFoundException() {
    super("No matching note exists");
  }

}
