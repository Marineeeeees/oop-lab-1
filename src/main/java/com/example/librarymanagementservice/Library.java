package com.example.librarymanagementservice;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books;
    private final LibraryDao libraryDao;

    public Library() {
        this.libraryDao = new LibraryDao();
        this.books = new ArrayList<>(libraryDao.readAllBooks());
    }

    public void addBook(Book book) {
        if (isBookExists(book.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN already exists: " + book.getIsbn());
        }
        books.add(book);
        libraryDao.addBook(book);
    }

    public void deleteBook(String isbn) {
        Book book = findBookByIsbn(isbn);
        books.remove(book);
        libraryDao.removeBook(isbn);
    }

    public void updateBook(String isbn, Book updatedInfo) {
        Book existingBook = findBookByIsbn(isbn);

        if (isNonEmptyString(updatedInfo.getTitle())) {
            existingBook.setTitle(updatedInfo.getTitle());
        }
        if (isNonEmptyString(updatedInfo.getAuthor())) {
            existingBook.setAuthor(updatedInfo.getAuthor());
        }
        if (isNonEmptyString(updatedInfo.getPublicationDate())) {
            existingBook.setPublicationDate(updatedInfo.getPublicationDate());
        }

        libraryDao.updateBook(isbn, existingBook);
    }

    public List<Book> findByAuthorName(String authorName) {
        return books.stream()
                .filter(book -> authorName.equals(book.getAuthor()))
                .toList();
    }

    public Book findByTitle(String title) {
        return books.stream()
                .filter(book -> title.equals(book.getTitle()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book with title not found: " + title));
    }

    public List<Book> findByPublicationDate(String date) {
        return books.stream()
                .filter(book -> date.equals(book.getPublicationDate()))
                .toList();
    }

    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    private boolean isBookExists(String isbn) {
        return books.stream().anyMatch(book -> book.getIsbn().equals(isbn));
    }

    private Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book with ISBN not found: " + isbn));
    }

    private boolean isNonEmptyString(String value) {
        return value != null && !value.trim().isEmpty();
    }
}