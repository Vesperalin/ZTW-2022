package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.service.RentalService;

@RestController
public class RentalController {
    @Autowired
    RentalService rentalService;

    @RequestMapping(value = "/rent-book/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> rentBook(@PathVariable("id") int bookId, int readerId) {
        Book rentedBook = rentalService.rentBook(readerId, bookId);
        if (rentedBook != null)
            return new ResponseEntity<>(rentedBook, HttpStatus.OK);
        else
            return new ResponseEntity<>("Book is already rented!", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/return-book/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> returnBook(@PathVariable("id") int bookId) {
        boolean isReturned = rentalService.returnBook(bookId);
        if (isReturned)
            return new ResponseEntity<>("Book returned", HttpStatus.OK);
        else
            return new ResponseEntity<>("Book is not rented and cannot be returned!", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public ResponseEntity<Object> getRentals() {
        return new ResponseEntity<>(rentalService.getRentalsRepo(), HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRentals(@PathVariable("id") int id) {
        return new ResponseEntity<>(rentalService.getRentedBooks(id), HttpStatus.OK);
    }
}
