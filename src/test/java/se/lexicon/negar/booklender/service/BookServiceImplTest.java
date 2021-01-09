package se.lexicon.negar.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.negar.booklender.data.BookRepository;
import se.lexicon.negar.booklender.dto.BookDto;
import se.lexicon.negar.booklender.entity.Book;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BookServiceImplTest {
    BookServiceImpl testBookServiceImpl;

    Book book1;
    Book book2;

    BookDto bookDto1 = new BookDto();
    BookDto bookDto2 = new BookDto();

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        testBookServiceImpl = new BookServiceImpl(bookRepository);

        book1 = new Book("First Book", 30, BigDecimal.valueOf(5), "Java");
        bookRepository.save(book1);

        book2 = bookRepository.save(new Book("Second Book", 30, BigDecimal.valueOf(9), "React"));

        bookDto2 = new BookDto(book2);
        bookDto1 = new BookDto(book1);
    }

    @Test
    void successfully_created() {
        assertNotNull(book1);
        assertNotNull(book2);

        assertNotNull(bookDto1);
        assertNotNull(bookDto2);
    }

    @Test
    void findByReserved() {
        assertEquals(2, testBookServiceImpl.findByReserved(false).size());
        assertEquals(0, testBookServiceImpl.findByReserved(true).size());

        book1.setReserved(true);

        assertTrue(testBookServiceImpl.findByReserved(true).contains(new BookDto(book1)));
        assertFalse(testBookServiceImpl.findByReserved(true).contains(new BookDto(book2)));
    }

    @Test
    void findByTitle() {
        String title1 = "first book";
        String title2 = "sECond bOOk";

        assertEquals(1, testBookServiceImpl.findByTitle(title1).size());
        assertEquals(1, testBookServiceImpl.findByTitle(title2).size());
        assertTrue(testBookServiceImpl.findByTitle(title1).contains(bookDto1));
        assertFalse(testBookServiceImpl.findByTitle(title1).contains(bookDto2));
        assertFalse(testBookServiceImpl.findByTitle(title2).contains(bookDto1));
        assertTrue(testBookServiceImpl.findByTitle(title2).contains(bookDto2));
    }

    @Test
    void findByAvailable() {
        assertEquals(2, testBookServiceImpl.findByAvailable(false).size());
        assertEquals(0, testBookServiceImpl.findByAvailable(true).size());

        bookDto2.setAvailable(true);
        bookDto2 = testBookServiceImpl.update(bookDto2);
        assertEquals(1, testBookServiceImpl.findByAvailable(true).size());
        assertEquals(1, testBookServiceImpl.findByAvailable(false).size());
        assertTrue( testBookServiceImpl.findByAvailable(true).contains(bookDto2));
        assertFalse( testBookServiceImpl.findByAvailable(true).contains(bookDto1));
    }

    @Test
    void findById() {
        assertEquals(bookDto1, testBookServiceImpl.findById(bookDto1.getBookId()));
        assertEquals(bookDto2, testBookServiceImpl.findById(bookDto2.getBookId()));
    }

    @Test
    void findAll() {
        assertEquals(2, testBookServiceImpl.findAll().size());
        assertTrue(testBookServiceImpl.findAll().contains(bookDto1));
        assertTrue(testBookServiceImpl.findAll().contains(bookDto2));
    }

    @Test
    void create() {
        BookDto bookDto3 = new BookDto();
        bookDto3.setBookId(5000);
        bookDto3.setTitle("Programming");
        bookDto3.setMaxLoanDays(10);
        bookDto3.setFinePerDay(BigDecimal.valueOf(5));
        bookDto3.setDescription("Database");

        bookDto3 = testBookServiceImpl.create(bookDto3);

        assertEquals(3, testBookServiceImpl.findAll().size());
        assertTrue(testBookServiceImpl.findAll().contains(bookDto3));
        assertEquals(3, bookDto3.getBookId());
        assertTrue(testBookServiceImpl.findAll().contains(bookDto3));

    }

    @Test
    void update() {
        bookDto1.setTitle("UI/UX");
        bookDto1.setDescription("Start to create a website");
        testBookServiceImpl.update(bookDto1);

        assertEquals("UI/UX", testBookServiceImpl.findById(book1.getBookId()).getTitle());
        assertEquals("Start to create a website", testBookServiceImpl.findById(bookDto1.getBookId()).getDescription());
    }

    @Test
    void delete() {
        assertTrue(testBookServiceImpl.delete(book1.getBookId()));
        assertFalse(testBookServiceImpl.findAll().contains(bookDto1));
        assertEquals(1, testBookServiceImpl.findAll().size());
        assertTrue(testBookServiceImpl.findAll().contains(bookDto2));
    }
}