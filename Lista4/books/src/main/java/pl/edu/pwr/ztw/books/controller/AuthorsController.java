package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.service.IAuthorService;
import pl.edu.pwr.ztw.books.validators.AuthorValidator;

@RestController
public class AuthorsController {
    @Autowired
    IAuthorService authorsService;

    @RequestMapping(value = "/get/authors", method = RequestMethod.GET)
    public ResponseEntity<Object> getAuthors() {
        return new ResponseEntity<>(authorsService.getAuthors(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/author/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAuthor(@PathVariable("id") int id) {
        return new ResponseEntity<>(authorsService.getAuthor(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/create/author", method = RequestMethod.POST)
    public ResponseEntity<Object> addAuthor(String firstName, String lastName) {
        boolean isValid = AuthorValidator.isValid(firstName.strip(), lastName.strip());
        if (isValid) {
            authorsService.createAuthor(firstName, lastName);
            return new ResponseEntity<>("Author is created successfully", HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("Invalid author data", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/update/author/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateAuthor(@PathVariable("id") int id, String firstName, String lastName) {
        boolean isValid = AuthorValidator.isValid(firstName.strip(), lastName.strip());
        if (isValid) {
            Author updatedAuthor = authorsService.updateAuthor(id, firstName, lastName);
            if (updatedAuthor != null)
                return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
            else return new ResponseEntity<>("Invalid author data", HttpStatus.NOT_ACCEPTABLE);
        } else
            return new ResponseEntity<>("Invalid author data", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/delete/author/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAuthor(@PathVariable("id") int id) {
        boolean isAuthorDeleted = authorsService.deleteAuthor(id);
        if (isAuthorDeleted)
            return new ResponseEntity<>("Author is deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);

    }
}
