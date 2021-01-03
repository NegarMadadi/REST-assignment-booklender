package se.lexicon.negar.booklender.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.negar.booklender.entity.Book;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BookRepositoryTest {
    Book book1;
    Book book2;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        book1 = new Book("Test 1",3, BigDecimal.valueOf(200),"Desc 1");
        book1.setReserved(true);
        book1.setAvailable(true);
        bookRepository.save(book1);
        book2 = bookRepository.save(new Book("Test 2",5, BigDecimal.valueOf(100),"Desc 2"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        assertEquals(2,bookRepository.findAll().size());
    }

    @Test
    void findByBookId() {
        Book foundBook = bookRepository.findByBookId(book1.getBookId());
        assertEquals(book1.getBookId(),foundBook.getBookId());
        assertEquals(book1.getDescription(),foundBook.getDescription());
        assertEquals(book1.getTitle(),foundBook.getTitle());
        assertEquals(book1.getMaxLoanDays(),foundBook.getMaxLoanDays());
        assertEquals(book1.isReserved(),foundBook.isReserved());
        assertEquals(book1.isAvailable(),foundBook.isAvailable());
        assertTrue(book1.equals(foundBook));
    }

    @Test
    void findAllByReserved() {
        List<Book> foundBooks = bookRepository.findAllByReserved(true);
        assertEquals(1,foundBooks.size());
        assertTrue(book1.equals(foundBooks.get(0)));
    }

    @Test
    void findAllByAvailable() {
        List<Book> foundBooks = bookRepository.findAllByAvailable(true);
        assertEquals(1,foundBooks.size());
        assertTrue(book1.equals(foundBooks.get(0)));
    }

    @Test
    void findAllByTitleContainingIgnoreCase() {
        List<Book> foundBooks = bookRepository.findAllByTitleContainingIgnoreCase("Test 1");
        assertEquals(1,foundBooks.size());
        assertTrue(book1.equals(foundBooks.get(0)));
    }
}