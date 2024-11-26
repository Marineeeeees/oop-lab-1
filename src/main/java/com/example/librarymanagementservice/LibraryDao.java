package com.example.librarymanagementservice;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryDao {

    private static final String filePath = "data\\library.csv";

    static {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBook(Book book) {
        try (PrintStream printStream = new PrintStream(new FileOutputStream(filePath, true))) {
            String csvString = convertBookToSCVString(book);
            printStream.println(csvString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeBook(String isbn) {
        List<Book> books = readAllBooks().stream()
                .filter(book -> !book.getIsbn().equals(isbn))
                .toList();
        try (PrintStream printStream = new PrintStream(new FileOutputStream(filePath, false))) {
           books.forEach(book -> printStream.println(convertBookToSCVString(book)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(String isbn, Book book) {
        List<Book> books = readAllBooks().stream()
                .map(sBook -> sBook.getIsbn().equals(isbn) ? book : sBook)
                .toList();
        try (PrintStream printStream = new PrintStream(new FileOutputStream(filePath, false))) {
            books.forEach(sBook -> printStream.println(convertBookToSCVString(sBook)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> readAllBooks() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))){
            return reader.lines()
                    .map(this::convertCSVStringToBook)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Book convertCSVStringToBook(String csvString) {
        String[] bookData = csvString.split("\t");
        return Book.builder()
                .isbn(bookData[0])
                .title(bookData[1])
                .author(bookData[2])
                .publicationDate(bookData[3])
                .build();
    }

    private String convertBookToSCVString(Book book) {
        return String.join("\t",
                List.of(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublicationDate())
        );
    }
}
