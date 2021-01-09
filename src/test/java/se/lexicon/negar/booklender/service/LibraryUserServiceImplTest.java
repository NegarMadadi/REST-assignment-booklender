package se.lexicon.negar.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.negar.booklender.data.LibraryUserRepository;
import se.lexicon.negar.booklender.dto.LibraryUserDto;
import se.lexicon.negar.booklender.entity.LibraryUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class LibraryUserServiceImplTest {
    LibraryUserServiceImpl testLibraryUserServiceImpl;

    LibraryUser user1;
    LibraryUser user2;
    LibraryUserDto userDto1;
    LibraryUserDto userDto2;

    @Autowired
    LibraryUserRepository libraryUserRepository;

    @BeforeEach
    void setUp() {
        testLibraryUserServiceImpl = new LibraryUserServiceImpl(libraryUserRepository);

        user1 = new LibraryUser(LocalDate.now().minusYears(10), "John", "john@yahoo.com");
        libraryUserRepository.save(user1);
        userDto1 = new LibraryUserDto(user1);

        user2 = libraryUserRepository.save(new LibraryUser(LocalDate.now().minusYears(20), "Jim", "jim@yahoo.com"));
        userDto2 = new LibraryUserDto(user2);
    }

    @Test
    void successfully_created() {
        assertNotNull(user1);
        assertNotNull(user2);
        assertNotNull(userDto1);
        assertNotNull(userDto2);
    }

    @Test
    void findById() {
        assertEquals(userDto1, testLibraryUserServiceImpl.findById(user1.getUserId()));
        assertEquals(userDto2, testLibraryUserServiceImpl.findById(user2.getUserId()));
    }

    @Test
    void findByEmail() {
        assertEquals(userDto1, testLibraryUserServiceImpl.findByEmail("JoHn@yahoo.com"));
        assertEquals(userDto2, testLibraryUserServiceImpl.findByEmail("jIM@yahoo.com"));
    }

    @Test
    void findAll() {
        assertEquals(2, testLibraryUserServiceImpl.findAll().size());
        assertTrue(testLibraryUserServiceImpl.findAll().contains(userDto1));
        assertTrue(testLibraryUserServiceImpl.findAll().contains(userDto2));
    }

    @Test
    void create() {
        LibraryUserDto userDto3 = new LibraryUserDto();
        userDto3.setRegDate(LocalDate.now());
        userDto3.setUserId(500);
        userDto3.setName("Smith");
        userDto3.setEmail("smith@yahoo.com");

        userDto3 = testLibraryUserServiceImpl.create(userDto3);

        assertEquals(3, testLibraryUserServiceImpl.findAll().size());
        assertEquals("smith@yahoo.com", testLibraryUserServiceImpl.findById(userDto3.getUserId()).getEmail());
        assertEquals(userDto3, testLibraryUserServiceImpl.findById(userDto3.getUserId()));
    }

    @Test
    void update() {
        userDto2.setName("test");
        userDto2.setEmail("test@test.com");
        testLibraryUserServiceImpl.update(userDto2);

        assertEquals(userDto2, testLibraryUserServiceImpl.findById(userDto2.getUserId()));
        assertEquals("test@test.com", testLibraryUserServiceImpl.findById(userDto2.getUserId()).getEmail());
        assertEquals("test", testLibraryUserServiceImpl.findById(userDto2.getUserId()).getName());
    }

    @Test
    void delete() {
        assertTrue(testLibraryUserServiceImpl.delete(userDto1.getUserId()));
        assertEquals(1, testLibraryUserServiceImpl.findAll().size());

        assertTrue(testLibraryUserServiceImpl.findAll().contains(userDto2));
        assertFalse(testLibraryUserServiceImpl.findAll().contains(userDto1));
    }
}