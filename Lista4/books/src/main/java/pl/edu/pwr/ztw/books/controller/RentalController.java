package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.service.RentalService;

import java.util.ArrayList;

@RestController
public class RentalController {
    @Autowired
    RentalService rentalService;

    @CrossOrigin
    @RequestMapping(value = "/rent-book/{bookId}", method = RequestMethod.POST)
    public ResponseEntity<Object> rentBook(@PathVariable("bookId") int bookId, @RequestParam("readerId") int readerId) {
        Book rentedBook = rentalService.rentBook(readerId, bookId);
        if (rentedBook != null)
            return new ResponseEntity<>(rentedBook, HttpStatus.OK);
        else
            return new ResponseEntity<>("Cannot rent book!", HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @RequestMapping(value = "/return-book/{bookId}", method = RequestMethod.GET)
    public ResponseEntity<Object> returnBook(@PathVariable("bookId") int bookId) {
        boolean isReturned = rentalService.returnBook(bookId);
        if (isReturned)
            return new ResponseEntity<>("Book returned", HttpStatus.OK);
        else
            return new ResponseEntity<>("Cannot return book!", HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public ResponseEntity<Object> getRentals() {
        return new ResponseEntity<>(rentalService.getRentalsRepo(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/rentals/{readerId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRentals(@PathVariable("readerId") int id) {
        ArrayList<Book> rentedBooks = rentalService.getRentedBooks(id);
        if (rentedBooks != null)
            return new ResponseEntity<>(rentedBooks, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
