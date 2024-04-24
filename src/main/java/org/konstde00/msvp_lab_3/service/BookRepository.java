package org.konstde00.msvp_lab_3.service;

import org.konstde00.msvp_lab_3.model.Book;
import org.konstde00.msvp_lab_3.model.Genre;
import java.util.List;

public interface BookRepository {
    Book getBook(String id);
    void addBook(Book book);
    Book removeBook(String id);
    List<Book> findBooksByTitle(String title);
    List<Book> findBooksByGenre(Genre genre);
    List<Book> getBooksByAuthor(String author);
}
