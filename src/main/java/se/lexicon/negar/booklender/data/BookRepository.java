package se.lexicon.negar.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.negar.booklender.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findAll();
    Book findByBookId(int bookId);
    List<Book> findAllByReserved(boolean ReservedStatus);
    List<Book> findAllByAvailable(boolean availableStatus);
    List<Book> findAllByTitleContainingIgnoreCase(String title);
}