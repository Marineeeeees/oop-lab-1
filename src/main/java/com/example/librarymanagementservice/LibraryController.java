package com.example.librarymanagementservice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class LibraryController {

    private Library library = new Library();

    @FXML private AnchorPane mainMenuPane;
    @FXML private AnchorPane addBookPane;
    @FXML private AnchorPane findBooksMenuPane;
    @FXML private AnchorPane findBookByAuthorNamePane;
    @FXML private AnchorPane findBookByYearPane;
    @FXML private AnchorPane findAllBooksPane;
    @FXML private AnchorPane findBookByTitlePane;
    @FXML private AnchorPane updateBookPane;
    @FXML private AnchorPane deleteBookPane;

    @FXML private TextField isbnField;
    @FXML private TextField titleField;
    @FXML private TextField authorNameField;
    @FXML private DatePicker publicationDateField;

    @FXML private TextField deleteIsbnField;

    @FXML private TextField findAuthorNameField;
    @FXML private TableView<Book> booksByAuthorTableView;
    @FXML private TableColumn<Book, String> isbnColumnAuthor;
    @FXML private TableColumn<Book, String> titleColumnAuthor;
    @FXML private TableColumn<Book, String> authorColumnAuthor;
    @FXML private TableColumn<Book, String> publicationDateColumnAuthor;

    @FXML private TextField findYearField;
    @FXML private TableView<Book> booksByYearTableView;
    @FXML private TableColumn<Book, String> isbnColumnYear;
    @FXML private TableColumn<Book, String> titleColumnYear;
    @FXML private TableColumn<Book, String> authorColumnYear;
    @FXML private TableColumn<Book, String> publicationDateColumnYear;

    @FXML private TableView<Book> booksTableView;
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> publicationDateColumn;

    @FXML private TextField updateISBNField;
    @FXML private TextField updateTitleField;
    @FXML private TextField updateAuthorNameField;
    @FXML private DatePicker updatePublicationDateField;

    @FXML private TextField findTitleField;

    @FXML
    public void initialize() {
        isbnColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        publicationDateColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));

        isbnColumnYear.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleColumnYear.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumnYear.setCellValueFactory(new PropertyValueFactory<>("author"));
        publicationDateColumnYear.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));

        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publicationDateColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
    }

    @FXML
    private void showAddBookScene(ActionEvent event) {
        showPane(addBookPane);
    }

    @FXML
    private void showFindBooksMenu(ActionEvent event) {
        showPane(findBooksMenuPane);
    }

    @FXML
    private void showFindBookByAuthorNameScene(ActionEvent event) {
        showPane(findBookByAuthorNamePane);
    }

    @FXML
    private void showFindBookByYearScene(ActionEvent event) {
        showPane(findBookByYearPane);
    }

    @FXML
    private void showUpdateBookScene(ActionEvent event) {
        showPane(updateBookPane);
    }

    @FXML
    private void showFindAllBooksScene(ActionEvent event) {
        showPane(findAllBooksPane);
        findAllBooks(event);
    }

    @FXML
    private void showFindBookByTitleScene(ActionEvent event)  {
        showPane(findBookByTitlePane);
    }

    @FXML
    private void showDeleteBookScene(ActionEvent event) {
        showPane(deleteBookPane);
    }

    @FXML
    private void showMainMenu(ActionEvent event) {
        mainMenuPane.setVisible(true);
        addBookPane.setVisible(false);
        deleteBookPane.setVisible(false);
        updateBookPane.setVisible(false);
        findBooksMenuPane.setVisible(false);
        findBookByAuthorNamePane.setVisible(false);
        findBookByYearPane.setVisible(false);
        findAllBooksPane.setVisible(false);
        findBookByTitlePane.setVisible(false);
    }

    @FXML
    private void addBook(ActionEvent event) {
        String isbn = isbnField.getText();
        String title = titleField.getText();
        String author = authorNameField.getText();
        String publicationDate = publicationDateField.getValue().toString();

        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || publicationDate.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please, fill all fields").show();
            return;
        }

        Book book = new Book(isbn, title, author, publicationDate);
        library.addBook(book);
        new Alert(Alert.AlertType.INFORMATION, "Book added successfully.").show();
    }

    @FXML
    private void findBooksByAuthorName(ActionEvent event) {
        String authorName = findAuthorNameField.getText();
        List<Book> books = library.findByAuthorName(authorName);
        booksByAuthorTableView.getItems().setAll(books);
    }

    @FXML
    private void findBooksByYear(ActionEvent event) {
        String year = findYearField.getText();

        List<Book> books = library.findByPublicationDate(year);
        booksByYearTableView.getItems().setAll(books);
    }

    @FXML
    private void findAllBooks(ActionEvent event) {
        List<Book> books = library.findAll();
        booksTableView.getItems().setAll(books);
    }

    @FXML
    private void updateBook(ActionEvent event) {
        String isbn = updateISBNField.getText();
        String title = updateTitleField.getText();
        String author = updateAuthorNameField.getText();
        String publicationDate = updatePublicationDateField.getValue() != null ? updatePublicationDateField.getValue().toString() : "";

        Book book = new Book(isbn, title, author, publicationDate);
        library.updateBook(isbn, book);
        new Alert(Alert.AlertType.INFORMATION, "Book updated successfully.").show();
    }

    @FXML
    public void deleteBook(ActionEvent event) {
        String isbn = deleteIsbnField.getText();
        library.deleteBook(isbn);
        new Alert(Alert.AlertType.INFORMATION, "Book deleted successfully.").show();
    }

    @FXML
    private void findBookByTitle(ActionEvent event) {
        String title = findTitleField.getText();
        Book book = library.findByTitle(title);
        if (book != null) {
            new Alert(Alert.AlertType.INFORMATION, "Book found: " + book).show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Book not found.").show();
        }
    }

    private void showPane(AnchorPane pane) {
        mainMenuPane.setVisible(false);
        addBookPane.setVisible(false);
        findBooksMenuPane.setVisible(false);
        findBookByAuthorNamePane.setVisible(false);
        findBookByYearPane.setVisible(false);
        findAllBooksPane.setVisible(false);
        findBookByTitlePane.setVisible(false);

        pane.setVisible(true);
    }
}
