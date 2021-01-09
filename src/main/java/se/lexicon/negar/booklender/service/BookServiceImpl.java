package se.lexicon.negar.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.negar.booklender.data.BookRepository;
import se.lexicon.negar.booklender.dto.BookDto;
import se.lexicon.negar.booklender.entity.Book;

import java.util.List;
import java.util.Optional;

@Service
@Configurable
public class BookServiceImpl implements BookService{

    BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    
    @Override
    public List<BookDto> findByReserved(boolean reserved) {
        List<Book> foundItems = bookRepository.findAllByReserved(reserved);
        return BookDto.toBookDtos(foundItems);
    }

    @Override
    public List<BookDto> findByAvailable(boolean available) {
        List<Book> foundItems = bookRepository.findAllByAvailable(available);
        return BookDto.toBookDtos(foundItems);
    }
    
    @Override
    public List<BookDto> findByTitle(String title) throws IllegalArgumentException {
        if (title == null || title.equals(""))
            throw new IllegalArgumentException("Title can not be empty");
        List<Book> foundItems = bookRepository.findAllByTitleContainingIgnoreCase(title);
        return BookDto.toBookDtos(foundItems);
    }
    
    @Override
    public BookDto findById(int bookId) throws RuntimeException {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new RuntimeException("Cannot find book with the id: " + bookId));
        return new BookDto(book);
    }
    
    @Override
    public List<BookDto> findAll() {
        List<Book> foundItems = bookRepository.findAll();
        return BookDto.toBookDtos(foundItems);
    }
    
    @Override
    @Transactional
    public BookDto create(BookDto bookDto) throws RuntimeException {
        if(bookDto.getBookId() == 0)
            throw new RuntimeException("Book id is invalid");
        if (bookRepository.findById(bookDto.getBookId()).isPresent())
            throw new RuntimeException("Book already exists, please update");
        Book book = new Book(bookDto.getTitle(),bookDto.getMaxLoanDays(),bookDto.getFinePerDay(),bookDto.getDescription());
        book.setAvailable(bookDto.isAvailable());
        book.setReserved(bookDto.isReserved());
        return new BookDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookDto update(BookDto bookDto) throws RuntimeException {
        Optional<Book> optionalBook = bookRepository.findById(bookDto.getBookId());
        if (!optionalBook.isPresent())
            throw new RuntimeException("Book doesn't exist");
        Book toUpdated = optionalBook.get();
        if (!toUpdated.getTitle().equals( bookDto.getTitle()))
            toUpdated.setTitle(bookDto.getTitle());
        if (toUpdated.isReserved() != bookDto.isReserved())
            toUpdated.setReserved(bookDto.isReserved());
        if (toUpdated.isAvailable() != bookDto.isAvailable())
            toUpdated.setAvailable(bookDto.isAvailable());
        if (toUpdated.getMaxLoanDays()!= bookDto.getMaxLoanDays())
            toUpdated.setMaxLoanDays(bookDto.getMaxLoanDays());
        if (!toUpdated.getFinePerDay().equals(bookDto.getFinePerDay()))
            toUpdated.setFinePerDay(bookDto.getFinePerDay());
        if (!toUpdated.getDescription().equals(bookDto.getDescription()))
            toUpdated.setDescription(bookDto.getDescription());

        return new BookDto(bookRepository.save(toUpdated));
    }

    @Override
    @Transactional
    public boolean delete(int bookId) throws RuntimeException{
        if (!bookRepository.findById(bookId).isPresent())
            throw new RuntimeException("Book doesn't exist");
        boolean deleted = false;
        if (bookRepository.existsById(bookId)){
            bookRepository.delete(bookRepository.findById(bookId).get());
            deleted = true;
        }
        return deleted;
    }
}
