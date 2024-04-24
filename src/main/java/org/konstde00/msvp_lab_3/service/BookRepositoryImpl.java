package org.konstde00.msvp_lab_3.service;

import org.konstde00.msvp_lab_3.model.Book;
import org.konstde00.msvp_lab_3.model.Genre;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookRepositoryImpl implements BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    @Override
    public Book getBook(String id) {
        if (!books.containsKey(id)) {
            throw new IllegalArgumentException("No book found with ID: " + id);
        }
        return books.get(id);
    }

    @Override
    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public Book removeBook(String id) {
        return books.remove(id);
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    @Override
    public List<Book> findBooksByGenre(Genre genre) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getGenre().equals(genre)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }
}

