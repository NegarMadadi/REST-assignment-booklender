package se.lexicon.negar.booklender.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.negar.booklender.entity.Book;
import se.lexicon.negar.booklender.entity.LibraryUser;
import se.lexicon.negar.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanDtoTest {
    BookDto bookDto1;
    BookDto bookDto2;

    LibraryUserDto libraryUserDto1;
    LibraryUserDto libraryUserDto2;

    List<Loan> loans;
    LoanDto loanDto;


    @BeforeEach
    void setUp() {

        Book book1 = new Book("Chemi", 4, BigDecimal.valueOf(10), "Beginner");
        Book book2 = new Book("Math", 3, BigDecimal.valueOf(11), "Level3");
        bookDto1 = new BookDto(book1);
        bookDto2 = new BookDto(book2);

        LibraryUser libraryUser1 = new LibraryUser(LocalDate.now(),"User 1","user1@gmail.com");
        LibraryUser libraryUser2 = new LibraryUser(LocalDate.now().minusYears(1),"User 2","user2@gmail.com");

        libraryUserDto1 = new LibraryUserDto(libraryUser1);
        libraryUserDto2 = new LibraryUserDto(libraryUser2);

        loanDto = new LoanDto(1,libraryUserDto1,bookDto1,LocalDate.now(),false);
    }

    // constructor test
    @Test
    void successfully_created() {
        assertEquals(bookDto1.getDescription(), loanDto.getBook().getDescription());
        assertEquals(bookDto1.getBookId(), loanDto.getBook().getBookId());
        assertEquals(bookDto1.getFinePerDay(), loanDto.getBook().getFinePerDay());
        assertEquals(bookDto1.getMaxLoanDays(), loanDto.getBook().getMaxLoanDays());
        assertEquals(bookDto1.getTitle(), loanDto.getBook().getTitle());

        assertEquals(libraryUserDto1.getEmail(), loanDto.getLoanTaker().getEmail());
        assertEquals(libraryUserDto1.getName(), loanDto.getLoanTaker().getName());
        assertEquals(libraryUserDto1.getRegDate(), loanDto.getLoanTaker().getRegDate());
        assertEquals(libraryUserDto1.getUserId(), loanDto.getLoanTaker().getUserId());

        assertEquals(1, loanDto.getLoanId());
    }

    @AfterEach
    void tearDown() {
    }
}