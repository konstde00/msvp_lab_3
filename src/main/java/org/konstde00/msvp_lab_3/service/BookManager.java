package org.konstde00.msvp_lab_3.service;

import org.konstde00.msvp_lab_3.model.Book;
import org.konstde00.msvp_lab_3.model.Genre;
import java.util.List;

public class BookManager {
    private final BookRepository bookRepository;

    public BookManager(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBook(String id) {
        return bookRepository.getBook(id);
    }

    public void addBook(Book book) {
        bookRepository.addBook(book);
    }

    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.getBooksByAuthor(author);
    }
}

