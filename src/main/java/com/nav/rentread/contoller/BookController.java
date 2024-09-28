package com.nav.rentread.contoller;

import com.nav.rentread.entities.Book;
import com.nav.rentread.entities.Rental;
import com.nav.rentread.entities.User;
import com.nav.rentread.service.BookService;
import com.nav.rentread.service.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Log4j2
public class BookController {
    final private BookService bookService;
    final private RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book,@AuthenticationPrincipal User user) {
        log.debug("User: "+user);
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }





    @PostMapping("{bookId}/rent")
    public Rental rentBook(@PathVariable Long bookId, @AuthenticationPrincipal User user) {
        return rentalService.rentBook(user, bookId);
    }

    @PostMapping("{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId, @AuthenticationPrincipal User user) {
        rentalService.returnBook(bookId,user.getId());
        return ResponseEntity.ok().build();
    }

}

