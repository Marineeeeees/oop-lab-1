package com.example.librarymanagementservice.exceptions;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setHeaderText(e.getCause().getCause().getMessage());
            alert.setContentText(e.getCause().getCause().getMessage());
            alert.showAndWait();
        });
    }
}