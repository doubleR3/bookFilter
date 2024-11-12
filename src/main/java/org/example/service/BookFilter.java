package org.example.service;


import org.example.dtos.Book;
import org.example.dtos.BookDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookFilter {

    public Optional<BookDate> filter(String filter, List<Book> books) {
        // Imprimir los libros sin fecha de publicacion
        books.stream()
                .filter(book -> book.getPublicationTimestamp() <= 0)
                .forEach(book -> System.out.println("Libro sin fecha de publicación: " + book.getTitle()));

        // Filtramos por el  parametro filter
        List<Book> filteredBooks = books.stream()
                .filter(book -> containsIgnoreCase(book.getTitle(), filter)
                        || containsIgnoreCase(book.getSummary(), filter)
                        || containsIgnoreCase(book.getAuthor().getBio(), filter))
                .collect(Collectors.toList());

        // Si existe mas de uno devolvemos el mas reciente
        if (!filteredBooks.isEmpty()) {
            Book mostRecentBook = filteredBooks.stream()
                    .filter(book -> book.getPublicationTimestamp() > 0)
                    .max(Comparator.comparing(Book::getPublicationTimestamp))
                    .orElse(null);

            if (mostRecentBook != null) {
                String formattedDate = formatDate(mostRecentBook.getPublicationTimestamp());
                return Optional.of(new BookDate(mostRecentBook, formattedDate));
            }
        }

        // Ordenar lista por fecha de publicación y luego por longitud de biografía del autor
        books.sort(Comparator
                .comparing((Book b) -> b.getPublicationTimestamp() > 0 ? b.getPublicationTimestamp() : Long.MAX_VALUE)
                .thenComparingInt(b -> b.getAuthor().getBio().length())
        );

        return Optional.empty();
    }

    // Metodo que pasa el texto a y la keyword a lowercase y busca esa keyword en el texto
    private boolean containsIgnoreCase(String text, String keyword) {
        return text != null && keyword != null && text.toLowerCase().contains(keyword.toLowerCase());
    }

    // Formateo de timestamp a MM-dd-yyy
    private String formatDate(long timestamp) {
        LocalDate date = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        return date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }
}
