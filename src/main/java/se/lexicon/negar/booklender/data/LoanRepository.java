package se.lexicon.negar.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.negar.booklender.entity.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {
    List<Loan> findAll();
    Loan findByLoanId(long loanId);
    List<Loan> findAllByLoanTaker_UserId(int userId);
    List<Loan> findAllByBook_BookId(int bookId);
    List<Loan> findAllByTerminated(boolean expiredStatus);
}