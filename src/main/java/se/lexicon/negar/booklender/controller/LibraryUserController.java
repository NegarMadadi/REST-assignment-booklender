package se.lexicon.negar.booklender.controller;

import se.lexicon.negar.booklender.dto.LibraryUserDto;
import se.lexicon.negar.booklender.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class LibraryUserController {
    LibraryUserService libraryUserService;

    @Autowired
    public LibraryUserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<LibraryUserDto> findById(@PathVariable int userId) {
        return ResponseEntity.ok(libraryUserService.findById(userId));
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<LibraryUserDto> findByEmail(@Valid @PathVariable("email") String email) {
        return ResponseEntity.ok(libraryUserService.findByEmail(email));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<LibraryUserDto>> findAll() {
        return ResponseEntity.ok(libraryUserService.findAll());
    }

    @PostMapping
    public ResponseEntity<LibraryUserDto> create(@Valid @RequestBody LibraryUserDto libraryUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryUserService.create(libraryUserDto));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<LibraryUserDto> update(@RequestBody LibraryUserDto libraryUserDto) {
        return ResponseEntity.ok(libraryUserService.update(libraryUserDto));
    }
}
