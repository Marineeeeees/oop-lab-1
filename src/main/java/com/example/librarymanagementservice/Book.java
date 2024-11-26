package com.example.librarymanagementservice;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    private String isbn;

    private String title;

    private String author;

    private String publicationDate;
}
