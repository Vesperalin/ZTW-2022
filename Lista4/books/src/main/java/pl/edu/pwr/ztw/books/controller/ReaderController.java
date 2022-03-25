package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.service.IReaderService;
import pl.edu.pwr.ztw.books.validators.HumanValidator;

@RestController
public class ReaderController {
    @Autowired
    IReaderService readerService;

    @RequestMapping(value = "/get/readers", method = RequestMethod.GET)
    public ResponseEntity<Object> getReaders() {
        return new ResponseEntity<>(readerService.getReaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/reader/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getReader(@PathVariable("id") int id) {
        Reader foundReader = readerService.getReader(id);
        if (foundReader != null)
            return new ResponseEntity<>(foundReader, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/create/reader", method = RequestMethod.POST)
    public ResponseEntity<Object> addReader(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        boolean isValid = HumanValidator.isValid(firstName.strip(), lastName.strip());
        if (isValid) {
            readerService.createReader(firstName, lastName);
            return new ResponseEntity<>("Reader is created successfully", HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("Invalid reader data", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/update/reader/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateReader(@PathVariable("id") int id, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        boolean isValid = HumanValidator.isValid(firstName.strip(), lastName.strip());
        if (isValid) {
            Reader updatedReader = readerService.updateReader(id, firstName, lastName);
            if (updatedReader != null)
                return new ResponseEntity<>(updatedReader, HttpStatus.OK);
            else return new ResponseEntity<>("Invalid reader data", HttpStatus.NOT_ACCEPTABLE);
        } else
            return new ResponseEntity<>("Invalid reader data", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/delete/reader/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteReader(@PathVariable("id") int id) {
        boolean isReaderDeleted = readerService.deleteReader(id);
        if (isReaderDeleted)
            return new ResponseEntity<>("Reader is deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Reader not found", HttpStatus.NOT_FOUND);

    }
}
