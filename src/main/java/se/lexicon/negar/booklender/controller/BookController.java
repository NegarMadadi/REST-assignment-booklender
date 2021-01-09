package se.lexicon.negar.booklender.controller;

import se.lexicon.negar.booklender.dto.BookDto;
import se.lexicon.negar.booklender.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/api/books")
public class BookController {
    BookServiceImpl bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }


    @GetMapping(path = "/{bookId}")
    public ResponseEntity<BookDto> findById(@PathVariable int bookId) {

        return ResponseEntity.ok(bookService.findById(bookId));
    }
    
    @GetMapping
    public ResponseEntity<Object> find(@RequestParam(name = "type", defaultValue = "*") String type,
            @RequestParam(name = "value", defaultValue = "*") String value) {

        if(type.toLowerCase().trim().equalsIgnoreCase("title"))
            return ResponseEntity.ok(bookService.findByTitle(value));
        else if(type.toLowerCase().trim().equalsIgnoreCase("reserved"))
            return ResponseEntity.ok(bookService.findByReserved(Boolean.parseBoolean(value)));
        else if(type.toLowerCase().trim().equalsIgnoreCase("available"))
            return ResponseEntity.ok(bookService.findByAvailable(Boolean.parseBoolean(value)));
        else
            return ResponseEntity.ok(bookService.findAll());
    }
    
    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(bookDto));
    }

    @PutMapping
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.update(bookDto));
    }
}
