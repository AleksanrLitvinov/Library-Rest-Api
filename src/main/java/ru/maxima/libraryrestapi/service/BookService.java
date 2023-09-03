package ru.maxima.libraryrestapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.libraryrestapi.model.Book;
import ru.maxima.libraryrestapi.model.Person;
import ru.maxima.libraryrestapi.repositories.BooksRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BooksRepository booksRepository;
    private final JwtService jwtService;

    public List<Book> getAllBook() {
        return booksRepository.findAll();
    }

    public Book creatBook(Book book){
        Book bookForSave = Book.builder()
                .name(book.getName())
                .author(book.getAuthor())
                .yearOfProduction(book.getYearOfProduction())
                .annotation(book.getAnnotation())
                .createdPerson(jwtService.getUserNameFromToken().getName())
                .createdAt(LocalDateTime.now())
                .build();
      return booksRepository.save(bookForSave);
    }

    public Book updateBook(Book book) {
        String updatedPersonName = jwtService.getUserNameFromToken().getName();

        Book bookUpdate = booksRepository.getReferenceById(book.getId());

        bookUpdate.setName(book.getName());
        bookUpdate.setAuthor(book.getAuthor());
        bookUpdate.setYearOfProduction(book.getYearOfProduction());
        bookUpdate.setAnnotation(book.getAnnotation());
        bookUpdate.setUpdatedPerson(updatedPersonName);
        bookUpdate.setUpdatedAt(LocalDateTime.now());

        return booksRepository.save(bookUpdate);
    }

    public Book takeBook(Long id) {
            Book book = booksRepository.findById(id).orElseThrow();
            if (book.getPerson() == null) {
                book.setPerson(jwtService.getUserNameFromToken());
                booksRepository.save(book);
            }
            return book;
    }

    public Book returnBook(Long bookId) {
            Book bookForReturn = booksRepository.findById(bookId).orElseThrow();
            if (bookForReturn.getPerson() != null) {
                bookForReturn.setPerson(null);
                booksRepository.save(bookForReturn);
            }
        return bookForReturn;
    }

    public Book removeBook(Long id){
        Book bookForRemove = booksRepository.findById(id).orElseThrow();
        bookForRemove.setRemovedAt(LocalDateTime.now());
        bookForRemove.setRemovedPerson(jwtService.getUserNameFromToken().getName());
        bookForRemove.setUpdatedAt(LocalDateTime.now());
        bookForRemove.setUpdatedPerson(jwtService.getUserNameFromToken().getName());
        return booksRepository.save(bookForRemove);
    }
}
