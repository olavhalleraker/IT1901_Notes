package rest.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception shown when API error occurs.
 */
public class ApiError {

  private final String message;
  private final int status;
  private final HttpStatus httpStatus;
  
  /**
   * Api error message including following.
   *
   * @param message a message
   * @param status the status
   * @param httpStatus the http status
   */
  public ApiError(String message, int status, HttpStatus httpStatus) {
    this.message = message;
    this.status = status;
    this.httpStatus = httpStatus;
  } 

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
