package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.service.IBooksService;
import pl.edu.pwr.ztw.books.validators.BookValidator;

@RestController
public class BooksController {
    @Autowired
    IBooksService booksService;

    @RequestMapping(value = "/get/books", method = RequestMethod.GET)
    public ResponseEntity<Object> getBooks() {
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/book/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getBook(@PathVariable("id") int id) {
        return new ResponseEntity<>(booksService.getBook(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/create/book", method = RequestMethod.POST)
    public ResponseEntity<Object> createBook(String title, int authorId, int pages) {
        boolean isValid = BookValidator.isValid(pages, title, authorId);
        if (isValid) {
            boolean isCreated = booksService.createBook(title, authorId, pages);
            if (isCreated)
                return new ResponseEntity<>("Book is created successfully", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>("Invalid book data", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/update/book/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateBook(@PathVariable("id") int id, String title, int authorId, int pages) {
        boolean isValid = BookValidator.isValid(pages, title, authorId);
        if (isValid) {
            Book updatedBook = booksService.updateBook(id, title, authorId, pages);
            if (updatedBook != null)
                return new ResponseEntity<>(updatedBook, HttpStatus.OK);
            else return new ResponseEntity<>("Invalid book data", HttpStatus.NOT_ACCEPTABLE);
        } else
            return new ResponseEntity<>("Invalid book data", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/delete/book/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteBook(@PathVariable("id") int id) {
        boolean isBookDeleted = booksService.deleteBook(id);
        if (isBookDeleted)
            return new ResponseEntity<>("Book is deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);

    }
}
