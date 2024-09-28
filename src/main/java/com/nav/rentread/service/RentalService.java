package com.nav.rentread.service;

import com.nav.rentread.entities.Book;
import com.nav.rentread.entities.Rental;
import com.nav.rentread.entities.User;
import com.nav.rentread.exceptions.AlreadyExistException;
import com.nav.rentread.exceptions.NotFoundException;
import com.nav.rentread.repository.BookRepository;
import com.nav.rentread.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RentalService {
    final  private RentalRepository rentalRepository;

    final private BookService bookService;
    final private BookRepository bookRepository;
    public Rental rentBook(User user, Long bookId) {
        Book book = bookService.getBookById(bookId);
        // Check availability
        if (!book.isAvailabilityStatus()) {
            throw new NotFoundException("Book is not available for rent");
        }

        Collection<Rental> rentals = rentalRepository.findByUserIdAndReturnDateIsNull(user.getId());


         if(rentals.stream().anyMatch((rental)-> rental.getBook().getId().equals(bookId))){
            throw new AlreadyExistException("User has already rented this book");
         }
        long alreadyRentedBooks =  rentals.stream().filter((rental)->{
            return rental.getReturnDate() == null;
        }).count();
        if (alreadyRentedBooks >=2) {
            throw new AlreadyExistException("User has already rented 2 books");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentalDate(LocalDateTime.now());

        // Update book availability
        book.setAvailabilityStatus(false);
        bookRepository.save(book);

        return rentalRepository.save(rental);
    }

    public void returnBook(Long bookId,Long userId) {
        Rental rental = rentalRepository.findByUserIdAndBookIdAndReturnDateIsNull(userId,bookId).orElseThrow(
                () -> new NotFoundException("User not rented this book")
        );
        rental.setReturnDate(LocalDateTime.now());
        rentalRepository.save(rental);

        // Update book availability
        Book book = rental.getBook();
        book.setAvailabilityStatus(true);
        bookRepository.save(book);
    }


    public Collection<Rental> getUserBooks(Long userId) {
        return rentalRepository.findByUserIdAndReturnDateIsNull(userId);
    }
}

