package com.nav.rentread.service;

import static org.junit.jupiter.api.Assertions.*;


import com.nav.rentread.entities.Book;
import com.nav.rentread.entities.Rental;
import com.nav.rentread.entities.User;
import com.nav.rentread.exceptions.AlreadyExistException;
import com.nav.rentread.exceptions.NotFoundException;
import com.nav.rentread.repository.BookRepository;
import com.nav.rentread.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {

    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    private User user;
    private Book book;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);

        book = new Book();
        book.setId(1L);
        book.setAvailabilityStatus(true);
    }

    @Test
    public void testRentBook_Success() {
        when(bookService.getBookById(1L)).thenReturn(book);
        when(rentalRepository.findByUserIdAndReturnDateIsNull(user.getId())).thenReturn(Collections.emptyList());
        when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Rental rental = rentalService.rentBook(user, 1L);

        assertNotNull(rental);
        assertEquals(user.getId(), rental.getUser().getId());
        assertEquals(book.getId(), rental.getBook().getId());
        assertFalse(book.isAvailabilityStatus());
    }

    @Test
    public void testRentBook_BookNotAvailable() {
        book.setAvailabilityStatus(false);
        when(bookService.getBookById(1L)).thenReturn(book);

        Exception exception = assertThrows(NotFoundException.class, () -> {
            rentalService.rentBook(user, 1L);
        });

        assertEquals("Book is not available for rent", exception.getMessage());
    }

    @Test
    public void testRentBook_UserAlreadyRentedBook() {
        Rental existingRental = new Rental();
        existingRental.setBook(book);
        existingRental.setReturnDate(null); // Book is still rented

        when(bookService.getBookById(1L)).thenReturn(book);
        when(rentalRepository.findByUserIdAndReturnDateIsNull(user.getId())).thenReturn(Collections.singletonList(existingRental));

        Exception exception = assertThrows(AlreadyExistException.class, () -> {
            rentalService.rentBook(user, 1L);
        });

        assertEquals("User has already rented this book", exception.getMessage());
    }

    @Test
    public void testRentBook_UserAlreadyRentedTwoBooks() {
        Rental rental1 = new Rental();
        rental1.setBook(book);
        rental1.setReturnDate(null);
        Rental rental2 = new Rental();
        rental2.setBook(book);
        rental2.setReturnDate(null);

        when(bookService.getBookById(3L)).thenReturn(book);
        when(rentalRepository.findByUserIdAndReturnDateIsNull(user.getId())).thenReturn(List.of(rental1, rental2));

        Exception exception = assertThrows(AlreadyExistException.class, () -> {
            rentalService.rentBook(user, 3L);
        });

        assertEquals("User has already rented 2 books", exception.getMessage());
    }

    @Test
    public void testReturnBook_Success() {
        Rental rental = new Rental();
        rental.setBook(book);
        rental.setReturnDate(null);
        rental.setUser(user);

        when(rentalRepository.findByUserIdAndBookIdAndReturnDateIsNull(user.getId(), book.getId())).thenReturn(Optional.of(rental));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        rentalService.returnBook(book.getId(), user.getId());

        assertNotNull(rental.getReturnDate());
        assertTrue(book.isAvailabilityStatus());
    }

    @Test
    public void testReturnBook_RentalNotFound() {
        when(rentalRepository.findByUserIdAndBookIdAndReturnDateIsNull(user.getId(), book.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            rentalService.returnBook(book.getId(), user.getId());
        });

        assertEquals("User not rented this book", exception.getMessage());
    }

    @Test
    public void testGetUserBooks_Success() {
        Rental rental = new Rental();
        rental.setBook(book);
        rental.setReturnDate(null);
        rental.setUser(user);

        when(rentalRepository.findByUserIdAndReturnDateIsNull(user.getId())).thenReturn(List.of(rental));

        Collection<Rental> rentals = rentalService.getUserBooks(user.getId());

        assertEquals(1, rentals.size());
        assertEquals(book.getId(), rentals.iterator().next().getBook().getId());
    }
}
