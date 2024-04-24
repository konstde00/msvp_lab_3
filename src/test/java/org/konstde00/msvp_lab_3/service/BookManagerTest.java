package org.konstde00.msvp_lab_3.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.konstde00.msvp_lab_3.model.Book;
import org.konstde00.msvp_lab_3.model.Genre;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookManagerTest {

    @Spy
    private BookRepositoryImpl bookRepository = new BookRepositoryImpl();

    @InjectMocks
    private BookManager bookManager;

    @Test
    void testAddBook() {
        Book book = new Book("123", "Book Title", "Author Name", Genre.ROMANCE);
        doNothing().when(bookRepository).addBook(book);

        bookManager.addBook(book);
        verify(bookRepository, times(1)).addBook(book);
    }

    @Test
    void testFindBooksByTitle() {
        Book book = new Book("123", "Book Title", "Author Name", Genre.ADVENTURE);
        when(bookRepository.findBooksByTitle("Book Title")).thenReturn(Collections.singletonList(book));

        var result = bookManager.findBooksByTitle("Book Title");
        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
    }

    @Test
    void testExceptionHandlingWithSpy() {
        // Trying to get a non-existent book to test exception handling
        doThrow(new IllegalStateException("This is a forced exception")).when(bookRepository).getBook("999");

        Exception exception = assertThrows(IllegalStateException.class, () -> bookManager.getBook("999"));
        assertEquals("This is a forced exception", exception.getMessage());
    }

    @Test
    void testGetBooksByAuthorUsingRealMethod() {
        // Setup: We mock only `getBook` method
        Book book1 = new Book("1", "Java Concurrency", "Brian Goetz", Genre.ADVENTURE);
        bookRepository.addBook(book1);  // Use real method to add a book

        // Action: Fetch books by author using the real method
        List<Book> booksByAuthor = bookManager.getBooksByAuthor("Brian Goetz");

        // Assert
        assertEquals(1, booksByAuthor.size());
        assertEquals(book1, booksByAuthor.getFirst());

        // Verify that getBook was never called in this scenario
        verify(bookRepository, never()).getBook(anyString());
    }
}

