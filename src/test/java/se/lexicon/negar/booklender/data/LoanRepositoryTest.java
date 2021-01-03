package se.lexicon.negar.booklender.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.negar.booklender.entity.Book;
import se.lexicon.negar.booklender.entity.LibraryUser;
import se.lexicon.negar.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LoanRepositoryTest {
    Book book1;
    Book book2;

    LibraryUser user1;
    LibraryUser user2;

    Loan loan1;
    Loan loan2;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryUserRepository libraryUserRepository;

    @Autowired
    LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        book1 = new Book("Test 1",3, BigDecimal.valueOf(200),"Desc 1");
        book2 = bookRepository.save(new Book("Test 2",5, BigDecimal.valueOf(100),"Desc 2"));

        user1 = libraryUserRepository.save(new LibraryUser(LocalDate.now(),"Test 1","test1@gmail.com"));
        user2 = libraryUserRepository.save(new LibraryUser(LocalDate.now(),"Test 2","test2@gmail.com"));

        loan1 = loanRepository.save(new Loan(user1,book1,LocalDate.now(),false));
        loan2 = loanRepository.save(new Loan(user2,book2,LocalDate.now().minusDays(book2.getMaxLoanDays()+1),true));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        assertEquals(2,loanRepository.findAll().size());
    }

    @Test
    void findByLoanId() {
        Loan found = loanRepository.findByLoanId(loan1.getLoanId());
        assertTrue(loan1.equals(found));
    }

    @Test
    void findAllByLoanTaker_UserId() {
        List<Loan> foundLoans = loanRepository.findAllByLoanTaker_UserId(loan1.getLoanTaker().getUserId());
        assertEquals(1,foundLoans.size());
        assertTrue(loan1.equals(foundLoans.get(0)));
    }

    @Test
    void findAllByBook_BookId() {
        List<Loan> foundLoans = loanRepository.findAllByBook_BookId(loan1.getBook().getBookId());
        assertEquals(1,foundLoans.size());
        assertTrue(loan1.equals(foundLoans.get(0)));
    }

    @Test
    void findAllByTerminated() {
        List<Loan> foundLoans = loanRepository.findAllByTerminated(true);
        assertEquals(1,foundLoans.size());
        assertTrue(loan2.equals(foundLoans.get(0)));
    }
}