package se.lexicon.negar.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.negar.booklender.data.BookRepository;
import se.lexicon.negar.booklender.data.LibraryUserRepository;
import se.lexicon.negar.booklender.data.LoanRepository;
import se.lexicon.negar.booklender.dto.BookDto;
import se.lexicon.negar.booklender.dto.LibraryUserDto;
import se.lexicon.negar.booklender.dto.LoanDto;
import se.lexicon.negar.booklender.entity.Book;
import se.lexicon.negar.booklender.entity.LibraryUser;
import se.lexicon.negar.booklender.entity.Loan;

import java.util.List;

@Service
@Configurable
public class LoanServiceImpl implements LoanService {

    LoanRepository loanRepository;
    LibraryUserRepository libraryUserRepository;
    BookRepository bookRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, LibraryUserRepository libraryUserRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.libraryUserRepository = libraryUserRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public LibraryUser getLibraryUser(LibraryUserDto libraryUserDto){

        return libraryUserRepository.findByUserId(libraryUserDto.getUserId());
    }
    @Transactional
    public Book getBook(BookDto bookDto){
        return bookRepository.findById(bookDto.getBookId()).orElseThrow(()-> new IllegalArgumentException("Book doesn't exist"));
    }

    @Override
    public LoanDto findById(long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(()-> new RuntimeException(
                "Loan with " + loanId + " id doesn't exist"
        ));
        return new LoanDto(loan);
    }

    @Override
    public List<LoanDto> findByBookId(int bookId) {
        List<Loan> foundItems = loanRepository.findAllByBook_BookId(bookId);
        return LoanDto.toLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findByUserId(int userId) {
        List<Loan> foundItems = loanRepository.findAllByLoanTaker_UserId(userId);
        return LoanDto.toLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findByTerminated(boolean terminated) {
        List<Loan> foundItems = loanRepository.findAllByTerminated(terminated);
        return LoanDto.toLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findAll() {
        List<Loan> all = loanRepository.findAll();
        return LoanDto.toLoanDtos(all);
    }

    @Override
    @Transactional
    public LoanDto create(LoanDto loanDto) throws RuntimeException{
        if (loanRepository.existsById(loanDto.getLoanId()))
            throw new RuntimeException("Loan duplicated");
        Loan loan = new Loan(getLibraryUser(loanDto.getLoanTaker()),
                getBook(loanDto.getBook()),
                loanDto.getLoanDate(),
                loanDto.isTerminated());

        return new LoanDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public LoanDto update(LoanDto loanDto) throws RuntimeException{
        if (!loanRepository.existsById(loanDto.getLoanId()))
            throw new RuntimeException("Loan doesn't exist");
        Loan loan = loanRepository.findById(loanDto.getLoanId()).get();
        if (!loan.getBook().equals(getBook(loanDto.getBook())))
            loan.setBook(getBook(loanDto.getBook()));
        if (loan.getLoanDate() != loanDto.getLoanDate())
            loan.setLoanDate(loanDto.getLoanDate());
        if (!loan.getLoanTaker().equals(getLibraryUser(loanDto.getLoanTaker())))
            loan.setLoanTaker(getLibraryUser(loanDto.getLoanTaker()));

        return new LoanDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public boolean delete(int bookId) throws RuntimeException{
        boolean deleted = false;

        if (loanRepository.findAllByBook_BookId(bookId).isEmpty()){
            throw new RuntimeException("Loan with book id: " + bookId + " was not found");
        }else{
            List<Loan> foundItems = loanRepository.findAllByBook_BookId(bookId);
            for (Loan l : foundItems){
                loanRepository.delete(l);
                deleted = true;
            }
        }
        return deleted;
    }
}
