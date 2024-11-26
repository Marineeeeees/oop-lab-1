module com.example.librarymanagementservice {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires static lombok;

    opens com.example.librarymanagementservice to javafx.fxml;
    exports com.example.librarymanagementservice;
    exports com.example.librarymanagementservice.exceptions;
    opens com.example.librarymanagementservice.exceptions to javafx.fxml;
}