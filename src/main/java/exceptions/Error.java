package exceptions;

import javafx.scene.control.Alert;

/**
 * Handles errors and alerts, provides information in case of error.
 *
 * @author The team.
 * @version 0.1
 * @since 0.1
 */
public class Error {
  Alert alert;

  /**
   * provides more information about the error.
   *
   * @param type Alert type, can be Warning, Error, Info, Confirmation
   *             or none.
   * @param title String, title of the error popup.
   * @param header String, brief information about the error.
   * @param message String, options and possible solution for the error.
   */
  public Error(Alert.AlertType type,
               String title,
               String header,
               String message) {
    alert = new Alert(type, message);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.showAndWait();
  }

  /**
   * Provides brief information about an error, less likely to be thrown.
   *
   * @param type Alert type, can be Warning, Error, Info, Confirmation
   *             or none.
   * @param message String, options and possible solution for the error.
   */
  public Error(Alert.AlertType type, String message) {
    alert = new Alert(type, message);
    alert.showAndWait();
  }
}
