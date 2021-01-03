package se.lexicon.negar.booklender.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    Book testBook;
    @BeforeEach
    void setUp() {
        testBook = new Book("Title",3, BigDecimal.valueOf(456),"Desc");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void successfully_created(){
        assertNotNull(testBook);
        assertTrue(testBook.getBookId() == 0);
        assertEquals("Title", testBook.getTitle());
        assertEquals("Desc", testBook.getDescription());
        assertEquals(BigDecimal.valueOf(456), testBook.getFinePerDay());
    }

    @Test
    void testEquals() {
        Book copy = new Book("Title",3, BigDecimal.valueOf(456),"Desc");

        assertTrue(testBook.equals(copy));
    }

    @Test
    void testHashcode() {
        Book copy = new Book("Title",3, BigDecimal.valueOf(456),"Desc");

        assertEquals(copy.hashCode(), testBook.hashCode());
    }

    @Test
    void testToString() {
        String toString = testBook.toString();

        assertTrue(toString.contains(Integer.toString(testBook.getBookId())));
        assertTrue(toString.contains(testBook.getTitle()));
        assertTrue(toString.contains(testBook.getDescription()));
        assertTrue(toString.contains(testBook.getFinePerDay().toString()));
    }

}