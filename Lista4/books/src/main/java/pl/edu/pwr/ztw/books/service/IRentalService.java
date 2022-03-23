package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Reader;

import java.util.ArrayList;
import java.util.Map;

public interface IRentalService {
    Map<Reader, ArrayList<Book>> getRentalsRepo();

    Book rentBook(int readerId, int bookId);

    boolean returnBook(int bookId);

    boolean isRented(Book book);

    public ArrayList<Book> getRentedBooks(int readerId);
}
