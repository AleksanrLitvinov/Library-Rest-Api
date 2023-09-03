package ru.maxima.libraryrestapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.maxima.libraryrestapi.model.Book;
import ru.maxima.libraryrestapi.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<Book>> listAllBooks() {
        List<Book> allBooks = bookService.getAllBook();
        if (allBooks == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if (allBooks.isEmpty())
            return new ResponseEntity<>(allBooks, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority(@authorities.ROLE_ADMIN)")
    @PostMapping()
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book bookCreated = bookService.creatBook(book);
        return new ResponseEntity<>(bookCreated, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyAuthority(@authorities.ROLE_ADMIN)")
    @PostMapping("/remove/{id}")
    public void removeBookById(@PathVariable Long id)  {
        bookService.removeBook(id);
    }

    @PreAuthorize("hasAnyAuthority(@authorities.ROLE_ADMIN)")
    @PostMapping("/edit")
    public ResponseEntity<Book> updateBook(@RequestBody Book book)  {
        Book bookUpdate = bookService.updateBook(book);
       return new ResponseEntity<>(bookUpdate, HttpStatus.OK);
    }


    @PostMapping("/take/{id}")
    public ResponseEntity<Book> takeBook(@PathVariable Long id)  {
          Book takeBook = bookService.takeBook(id);
            return new ResponseEntity<>(takeBook, HttpStatus.OK);
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<Book> returnBook(@PathVariable Long id) {
        Book returnBook = bookService.returnBook(id);
            return new ResponseEntity<>(returnBook, HttpStatus.OK);
    }


}