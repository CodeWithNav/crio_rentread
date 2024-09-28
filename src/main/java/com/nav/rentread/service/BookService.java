package com.nav.rentread.service;
import com.nav.rentread.entities.Book;
import com.nav.rentread.exceptions.NotFoundException;
import com.nav.rentread.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    final private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setGenre(bookDetails.getGenre());
        book.setAvailabilityStatus(bookDetails.isAvailabilityStatus());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        getBookById(id);
        bookRepository.deleteById(id);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Book not found with id: " + id)
        );
    }
}

