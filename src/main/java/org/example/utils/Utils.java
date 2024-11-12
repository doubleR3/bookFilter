package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dtos.Book;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Utils {

    public static List<Book> readBooksFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = Utils.class.getResourceAsStream("/books.json")) {
            if (inputStream == null) {
                throw new IOException("No se pudo encontrar el archivo books.json en resources");
            }

            return objectMapper.readValue(inputStream, new TypeReference<List<Book>>() {
            });
        } catch (IOException e) {
            System.err.println("Error al leer el archivo books.json: " + e.getMessage());
            return List.of(); // Retorna una lista vacía en caso de error
        }
    }
}
