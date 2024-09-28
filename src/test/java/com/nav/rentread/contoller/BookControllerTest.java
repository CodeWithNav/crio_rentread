package com.nav.rentread.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.nav.rentread.entities.Book;
import com.nav.rentread.entities.Rental;
import com.nav.rentread.entities.User;
import com.nav.rentread.service.BookService;
import com.nav.rentread.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@WithMockUser(authorities = {
        "ROLE_ADMIN"
})
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private RentalService rentalService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    User globalUser;
    @BeforeEach
    public void setUp() {
        globalUser = new User();
        globalUser.setId(1L);
        globalUser.setFirstName("Test");
        globalUser.setLastName("User");
        globalUser.setEmail("admin@test.com");
        globalUser.setPassword("12345678");
        globalUser.setRole(User.Role.ADMIN);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setAvailabilityStatus(true);

        when(bookService.getAllBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/books").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test
    @WithMockUser(authorities = {
            "ROLE_ADMIN"
    })
    public void testCreateBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("New Book");
        book.setAuthor("Author");

        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old Title");
        existingBook.setAuthor("Author");

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Author");

        when(bookService.updateBook(any(Long.class), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/books/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = {
            "ROLE_ADMIN"
    })
    public void testRentBook_Success() throws Exception {
        User user = new User();
        user.setId(1L);

        Book book = new Book();
        book.setId(1L);

        Rental rental = new Rental();
        rental.setBook(book);
        rental.setUser(user);

        when(rentalService.rentBook(user, 1L)).thenReturn(rental);

        mockMvc.perform(post("/books/1/rent")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test

    public void testReturnBook_Success() throws Exception {
        User user = new User();
        user.setId(1L);

        doNothing().when(rentalService).returnBook(1L, user.getId());

        mockMvc.perform(post("/books/1/return").with(user(globalUser)).with(csrf())
                        ).andDo(print()) // Simulate authenticated user
                .andExpect(status().isOk());
    }
}
