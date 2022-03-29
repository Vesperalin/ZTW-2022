package pl.edu.pwr.ztw.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class RentalService implements IRentalService {
    private static final HashMap<Integer, ArrayList<Book>> rentalsRepo = new HashMap<>();

    @Autowired
    BookService bookService;
    @Autowired
    ReaderService readerService;

    @Override
    public Map<Integer, ArrayList<Book>> getRentalsRepo() {
        return rentalsRepo;
    }

    @Override
    public Book rentBook(int readerId, int bookId) {
        Book bookToRent = bookService.getBook(bookId);
        if (bookToRent != null) {
            if (isRented(bookToRent)) {
                return null;
            } else {
                rentalsRepo.computeIfAbsent(readerId, k -> new ArrayList<>()).add(bookToRent);
            }
        }
        return bookToRent;
    }

    @Override
    public boolean returnBook(int bookId) {
        Book bookToRent = bookService.getBook(bookId);
        if (bookToRent != null) {
            if (!isRented(bookToRent)) {
                return false;
            } else {
                rentalsRepo.values().forEach(books -> {
                    if (books.contains(bookToRent)) {
                        books.remove(bookToRent);
                        return;
                    }
                });
                return true;
            }
        }
        else return false;
    }

    @Override
    public boolean isRented(Book book) {
        ArrayList<Book> rentedBooks = rentalsRepo.values().stream().reduce(new ArrayList<>(), (books, item) -> {
            books.addAll(item);
            return books;
        });
        Book rentedBook = rentedBooks.stream()
                .filter(b -> b.getId() == book.getId())
                .findAny()
                .orElse(null);
        return rentedBook != null;
    }

    @Override
    public ArrayList<Book> getRentedBooks(int readerId) {
        return rentalsRepo.get(readerId);
    }
}
