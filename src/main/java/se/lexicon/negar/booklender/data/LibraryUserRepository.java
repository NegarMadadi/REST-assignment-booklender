package se.lexicon.negar.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.negar.booklender.entity.LibraryUser;

import java.util.List;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, Integer> {
    List<LibraryUser> findAll();
    LibraryUser findByUserId(int userId);
    LibraryUser findByEmailIgnoreCase(String email);
}