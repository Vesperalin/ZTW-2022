package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.service.IBooksService;
import pl.edu.pwr.ztw.books.validators.BookValidator;

@RestController
public class BooksController {
    @Autowired
    IBooksService booksService;

    @CrossOrigin
    @RequestMapping(value = "/get/books", method = RequestMethod.GET)
    public ResponseEntity<Object> getBooks() {
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/get/book/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getBook(@PathVariable("id") int id) {
        Book foundBook = booksService.getBook(id);
        if (foundBook != null)
            return new ResponseEntity<>(foundBook, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @RequestMapping(value = "/create/book", method = RequestMethod.POST)
    public ResponseEntity<Object> createBook(@RequestParam("title") String title, @RequestParam("authorId") int authorId, @RequestParam("pages") int pages) {
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

    @CrossOrigin
    @RequestMapping(value = "/update/book/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateBook(@PathVariable("id") int id, @RequestParam("title") String title, @RequestParam("authorId") int authorId, @RequestParam("pages") int pages) {
        boolean isValid = BookValidator.isValid(pages, title, authorId);
        if (isValid) {
            Book updatedBook = booksService.updateBook(id, title, authorId, pages);
            if (updatedBook != null)
                return new ResponseEntity<>(updatedBook, HttpStatus.OK);
            else return new ResponseEntity<>("Invalid book data", HttpStatus.NOT_ACCEPTABLE);
        } else
            return new ResponseEntity<>("Invalid book data", HttpStatus.NOT_ACCEPTABLE);
    }

    @CrossOrigin
    @RequestMapping(value = "/delete/book/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteBook(@PathVariable("id") int id) {
        boolean isBookDeleted = booksService.deleteBook(id);
        if (isBookDeleted)
            return new ResponseEntity<>("Book is deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);

    }
}
