package ui;

import core.Note;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * CustomListCell is a custom cell for displaying Note objects in a JavaFX ListView.
 */
public class CustomListCell extends ListCell<Note> {
  private HBox content;
  private Text title = new Text();
  private Text text = new Text();
  private Text date = new Text();

  /**
   * Constructs a CustomListCell, creating the layout for displaying a Note. The cell consists of a
   * VBox containing a title and text, and an HBox for displaying the creation date. The title is
   * set to wrap to the next line when it exceeds a maximum width, and it's displayed in a bold
   * font.
   */
  public CustomListCell() {
    super();
    VBox vbox = new VBox(title, text);
    HBox hbox = new HBox(date);

    content = new HBox(vbox, hbox);
    content.setSpacing(10);

    // Set alignment for the child HBoxes
    HBox.setHgrow(vbox, Priority.ALWAYS); // Makes vBox take as much space as possible on the left
    HBox.setHgrow(hbox, Priority.NEVER);
  }

  /**
   * Updates the ListCell with the provided Note object. If the Note is not null and the cell is not
   * empty, it sets the title, text, and creation date to their respective values. The title text is
   * set to wrap to the next line if it exceeds a maximum width, and it's displayed in a bold font.
   *
   * @param note The Note object to display in the cell.
   * @param empty A boolean indicating whether the cell is empty.
   */
  @Override
  protected void updateItem(Note note, boolean empty) {
    super.updateItem(note, empty);
    if (note != null && !empty) { // <== test for null item and empty parameter
      title.setText(note.getTitle());
      text.setText(String.format("%s", note.getText()));
      title.setFont(Font.font("Arial", FontWeight.BOLD, 13));
      date.setText(String.format("%s", note.getCreatedDate()));
      title.setWrappingWidth(300); // Set the maximum width for title text
      text.setWrappingWidth(300);
      setGraphic(content);
    } else {
      setGraphic(null);
    }
  }
}
