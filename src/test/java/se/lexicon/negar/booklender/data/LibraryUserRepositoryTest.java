package se.lexicon.negar.booklender.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.negar.booklender.entity.LibraryUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibraryUserRepositoryTest {

    LibraryUser user1;
    LibraryUser user2;

    @Autowired
    LibraryUserRepository libraryUserRepository;

    @BeforeEach
    void setUp() {
        user1 = libraryUserRepository.save(new LibraryUser(LocalDate.now(),"Test 1","test1@gmail.com"));
        user2 = libraryUserRepository.save(new LibraryUser(LocalDate.now(),"Test 2","test2@gmail.com"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        assertEquals(2,libraryUserRepository.findAll().size());
    }

    @Test
    void findByUserId() {
        assertEquals(user1.getUserId(),libraryUserRepository.findByUserId(user1.getUserId()).getUserId());
        assertEquals(user1.getName(),libraryUserRepository.findByUserId(user1.getUserId()).getName());
        assertEquals(user1.getEmail(),libraryUserRepository.findByUserId(user1.getUserId()).getEmail());
    }

    @Test
    void findByEmailIgnoreCase() {
        LibraryUser foundUser = libraryUserRepository.findByEmailIgnoreCase("TeSt1@gMaIl.com");
        assertEquals(user1.getUserId(),foundUser.getUserId());
        assertEquals(user1.getName(),foundUser.getName());
        assertEquals(user1.getEmail(),foundUser.getEmail());
    }
}