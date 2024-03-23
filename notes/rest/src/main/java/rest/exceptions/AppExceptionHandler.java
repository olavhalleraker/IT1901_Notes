package rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Allows overwriting exceptions from server.
 */
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> handleUserNotFound(UserNotFoundException e,
          WebRequest webRequest) {
    return new ResponseEntity<>(new ApiError(e.getMessage(), 404,
            HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException e,
          WebRequest webRequest) {
    return new ResponseEntity<>(new ApiError(e.getMessage(), 409,
            HttpStatus.CONFLICT), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(NoteNotFoundException.class)
  public ResponseEntity<Object> handleNoteNotFound(NoteNotFoundException e,
          WebRequest webRequest) {
    return new ResponseEntity<>(new ApiError(e.getMessage(), 404,
            HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(FileException.class)
  public ResponseEntity<Object> handleFileException(FileException e,
          WebRequest webRequest) {
    return new ResponseEntity<>(new ApiError(e.getMessage(), 404,
            HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }
}
