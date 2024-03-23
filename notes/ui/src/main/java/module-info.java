module notes.ui {
  requires com.fasterxml.jackson.databind;
  requires transitive notes.core;
  requires javafx.controls;
  requires transitive javafx.fxml;
  requires transitive javafx.base;
  requires transitive javafx.graphics;
  requires java.net.http;

  opens ui.controllers to javafx.graphics, javafx.fxml;

  exports ui.controllers;
  exports ui to javafx.graphics;

  opens dataaccess;

  exports dataaccess;
}
