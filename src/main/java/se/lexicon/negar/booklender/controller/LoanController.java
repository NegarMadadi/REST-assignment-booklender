package se.lexicon.negar.booklender.controller;

import se.lexicon.negar.booklender.dto.LoanDto;
import se.lexicon.negar.booklender.service.LoanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/loans")
public class LoanController {
    LoanServiceImpl loanService;

    @Autowired
    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping(path = "/{loanId}")
    public ResponseEntity<LoanDto> findById(@PathVariable long loanId) {
        return ResponseEntity.ok(loanService.findById(loanId));
    }

    @GetMapping
    public ResponseEntity<Object> find(
            @RequestParam(name = "type", defaultValue = "*") String type,
            @RequestParam(name = "value", defaultValue = "*") String value) {

        if(type.toLowerCase().trim().equalsIgnoreCase("bookid"))
            return ResponseEntity.ok(loanService.findByBookId(Integer.parseInt(value)));
        else if(type.toLowerCase().trim().equalsIgnoreCase("userid"))
            return ResponseEntity.ok(loanService.findByUserId(Integer.parseInt(value)));
        else if(type.toLowerCase().trim().equalsIgnoreCase("expired"))
            return ResponseEntity.ok(loanService.findByTerminated(Boolean.parseBoolean(value)));
        else return ResponseEntity.ok(loanService.findAll());
    }

    @PostMapping
    public ResponseEntity<LoanDto> save(@Valid @RequestBody LoanDto loanDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(loanDto));
    }

    @PutMapping
    public ResponseEntity<LoanDto> update(@RequestBody LoanDto loanDto) {
        return ResponseEntity.ok(loanService.update(loanDto));
    }
}
