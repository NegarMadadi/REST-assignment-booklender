package se.lexicon.negar.booklender.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.negar.booklender.entity.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookDtoTest {
    Book book1;
    Book book2;
    Book book3;
    List<Book> books;
    BookDto bookDto1;


    @BeforeEach
    void setUp() {

        book1 = new Book("Chemi", 4, BigDecimal.valueOf(10), "Beginner");
        book2 = new Book("Math", 3, BigDecimal.valueOf(11), "Level3");
        book3 = new Book("Geo", 5, BigDecimal.valueOf(12), "Level4");
        bookDto1 = new BookDto(book1);
        books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);


    }

    // constructor test
    @Test
    void successfully_converts_book_toBookDto() {
        assertEquals(book1.getDescription(), bookDto1.getDescription());
        assertEquals(book1.getTitle(), bookDto1.getTitle());
        assertEquals(book1.getMaxLoanDays(), bookDto1.getMaxLoanDays());
        assertEquals(book1.getFinePerDay(), bookDto1.getFinePerDay());
        assertEquals(book1.getBookId(), bookDto1.getBookId());
    }


    @Test
    void successfully_converts_List_of_books_to_List_of_bookDtos() {

        List<BookDto> bookDtos = BookDto.toBookDtos(books);
        assertEquals(books.size(), bookDtos.size());

    }


    @AfterEach
    void tearDown() {
    }

}