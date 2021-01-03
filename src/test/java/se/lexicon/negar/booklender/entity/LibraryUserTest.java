package se.lexicon.negar.booklender.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryUserTest {

    LibraryUser testLibraryUser;
    @BeforeEach
    void setUp() {
        testLibraryUser = new LibraryUser(LocalDate.of(2021,1,1), "Negar","test@gmail.com");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void successfully_created(){
        assertNotNull(testLibraryUser);
        assertTrue(testLibraryUser.getUserId() == 0);
        assertEquals("Negar", testLibraryUser.getName());
        assertEquals("test@gmail.com", testLibraryUser.getEmail());
    }

    @Test
    void testEquals() {
        LibraryUser copy = new LibraryUser(LocalDate.of(2021,1,1), "Negar", "test@gmail.com");

        assertTrue(testLibraryUser.equals(copy));
    }

    @Test
    void testHashcode() {
        LibraryUser copy = new LibraryUser(LocalDate.of(2021,1,1), "Negar", "test@gmail.com");

        assertEquals(copy.hashCode(), testLibraryUser.hashCode());
    }

    @Test
    void testToString() {
        String toString = testLibraryUser.toString();

        assertTrue(toString.contains(Integer.toString(testLibraryUser.getUserId())));
        assertTrue(toString.contains(testLibraryUser.getName()));
        assertTrue(toString.contains(testLibraryUser.getEmail()));
    }

}