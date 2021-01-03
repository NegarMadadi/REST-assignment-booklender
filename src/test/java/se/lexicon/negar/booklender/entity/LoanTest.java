package se.lexicon.negar.booklender.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {
    Book testBook;
    LibraryUser testUser;
    Loan testLoan;
    @BeforeEach
    void setUp() {
        testBook = new Book("Title",3, BigDecimal.valueOf(456),"Desc");
        testBook.setMaxLoanDays(5);
        testUser = new LibraryUser(LocalDate.of(2020,1,1), "Test User","test@gmail.com");
        testLoan = new Loan(testUser,testBook,LocalDate.of(2020,12,25),false);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void successfully_created(){
        assertNotNull(testLoan);
        assertTrue(testLoan.getLoanId() == 0);
        assertEquals(LocalDate.of(2020,12,25), testLoan.getLoanDate());
    }

    @Test
    void for_terminated_loan_return_zero(){
        Loan test = new Loan(testUser,testBook,LocalDate.now(),true);
        assertEquals(BigDecimal.ZERO,test.getFine());
    }

    @Test
    void for_not_expired_loan_return_zero(){
        Loan test = new Loan(testUser,testBook,LocalDate.now(),false);
        assertEquals(BigDecimal.ZERO,test.getFine());
    }

    @Test
    void for_expired_loan_return_fine(){
        Loan test = new Loan(testUser,testBook,LocalDate.now().minusDays(6),false);
        assertEquals(testBook.getFinePerDay(),test.getFine());
    }
    @Test
    void for_expired_loan_with_more_than_one_return_fine(){
        Loan test = new Loan(testUser,testBook,LocalDate.now().minusDays(7),false);
        assertEquals(testBook.getFinePerDay().multiply(BigDecimal.valueOf(2)),test.getFine());
    }

    @Test
    void extend_loan_with_days_more_than_bookMaxLoanDays(){
        Loan test = new Loan(testUser,testBook,LocalDate.now(),false);
        assertFalse(testLoan.extendLoan(testBook.getMaxLoanDays()+1));
    }

    @Test
    void extend_loan_with_days_less_than_bookMaxLoanDays(){
        Loan test = new Loan(testUser,testBook,LocalDate.now().minusDays(testBook.getMaxLoanDays()-1),false);
        LocalDate prevLoanDate = test.getLoanDate();
        assertTrue(test.extendLoan(testBook.getMaxLoanDays()-1));
        assertEquals(prevLoanDate.plusDays(testBook.getMaxLoanDays()-1),test.getLoanDate());
    }
}