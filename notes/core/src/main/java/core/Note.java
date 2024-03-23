package core;

import java.time.LocalDate;

/**
 * Class for note with title, text, created date and edited date.
 */
public class Note {
  private String title;
  private String text;
  private LocalDate created;
  private LocalDate edited;

  /**
   * Constructor for Note class with title and text parameters.
   *
   * @param title of the note
   * @param text of the note
   */
  public Note(String title, String text) {
    if (title.equals("")) {
      throw new IllegalArgumentException(Errors.EMPTY_TITLE.getMessage());
    }
    this.title = title;

    if (text.equals("")) {
      throw new IllegalArgumentException(Errors.EMPTY_TEXT.getMessage());
    }
    this.text = text;
    this.created = LocalDate.now();
    this.edited = LocalDate.now();
  }

  /**
   * Constructor for Note class with specified dates. 
   * Creation date must not be after the edited date. 
   * LocalDate format: year-month-day Example: 2023-09-25.
   *
   * @param title of note
   * @param text describing the note
   * @param created local date when the Note was created
   * @param edited local date when the Note was last edited
   * @throws IllegalArgumentException if the creation date is after the edited date
   */
  public Note(String title, String text, LocalDate created, LocalDate edited) {
    if (title.equals("")) {
      throw new IllegalArgumentException(Errors.EMPTY_TITLE.getMessage());
    }
    this.title = title;

    if (text.equals("")) {
      throw new IllegalArgumentException(Errors.EMPTY_TEXT.getMessage());
    }
    this.text = text;

    if (created.isAfter(edited)) {
      throw new IllegalArgumentException(Errors.INVALID_CREATE_DATE.getMessage());
    }
    this.created = created;
    this.edited = edited;
  }

  /** 
   * Get method for title field.
   *
   * @return the the title of the Note
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set method for title field.
   *
   * @param title of the Note
   */
  public void setTitle(String title) {
    if (title.equals("")) {
      throw new IllegalArgumentException(Errors.EMPTY_TITLE.getMessage());
    }
    this.title = title;
  }

  /**
   * Get method for text field.
   *
   * @return the text in the Note
   */
  public String getText() {
    return text;
  }

  /**
   * Set method for text field.
   *
   * @param text text in the note
   */
  public void setText(String text) {
    if (text.equals("")) {
      throw new IllegalArgumentException(Errors.EMPTY_TEXT.getMessage());
    }
    this.text = text;
  }

  /**
   * Get method for created date.
   *
   * @return when the Note was created
   */
  public LocalDate getCreatedDate() {
    return created;
  }

  /**
   * Get method for edited date.
   *
   * @return when the Note last was edited
   */
  public LocalDate getEditedDate() {
    return edited;
  }

  /**
   * Sets edited date to todays date.
   */
  public void setEditedDate() {
    this.edited = LocalDate.now();
  }

}
