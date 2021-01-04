package se.lexicon.negar.booklender.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.negar.booklender.entity.Book;
import se.lexicon.negar.booklender.entity.LibraryUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryUserDtoTest {

    LibraryUser libraryUser1;
    LibraryUser libraryUser2;
    LibraryUser libraryUser3;
    List<LibraryUser> libraryUsers;
    LibraryUserDto libraryUserDto1;


    @BeforeEach
    void setUp() {

        libraryUser1 = new LibraryUser(LocalDate.now(),"User 1","user1@gmail.com");
        libraryUser2 = new LibraryUser(LocalDate.now().minusYears(1),"User 2","user2@gmail.com");
        libraryUser3 = new LibraryUser(LocalDate.now().minusYears(2),"User 3","user3@gmail.com");

        libraryUserDto1 = new LibraryUserDto(libraryUser1);

        libraryUsers = new ArrayList<>();
        libraryUsers.add(libraryUser1);
        libraryUsers.add(libraryUser2);
        libraryUsers.add(libraryUser3);
    }

    // constructor test
    @Test
    void successfully_converts_libraryUser_toLibraryUserDto() {
        assertEquals(libraryUser1.getEmail(),libraryUserDto1.getEmail());
        assertEquals(libraryUser1.getName(),libraryUserDto1.getName());
        assertEquals(libraryUser1.getUserId(),libraryUserDto1.getUserId());
        assertEquals(libraryUser1.getRegDate(),libraryUserDto1.getRegDate());
    }


    @Test
    void successfully_converts_List_of_books_to_List_of_bookDtos() {
        List<LibraryUserDto> libraryUserDtos = LibraryUserDto.toLibraryUserDtos(libraryUsers);
        assertEquals(libraryUsers.size(), libraryUserDtos.size());

    }


    @AfterEach
    void tearDown() {
    }
}