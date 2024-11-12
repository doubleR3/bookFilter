package org.example;

import org.example.dtos.Book;
import org.example.dtos.BookDate;
import org.example.service.BookFilter;
import org.example.utils.Utils;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<Book> books = Utils.readBooksFromJson();
        BookFilter bookFilter = new BookFilter();
        String filter = "example";

        if (!books.isEmpty()) {
            books.forEach(System.out::println);

            Optional<BookDate> result = bookFilter.filter(filter, books);

            result.ifPresent(bookDate -> System.out.println("Libro encontrado: " + bookDate));
            System.out.println("Libros ordenados:");
            books.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros en el archivo JSON.");
        }



    }
}