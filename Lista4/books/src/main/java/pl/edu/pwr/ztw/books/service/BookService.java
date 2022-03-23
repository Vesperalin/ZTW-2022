package pl.edu.pwr.ztw.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.model.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BookService implements IBooksService {
    private static final List<Book> booksRepo = new ArrayList<>();
    private static int nextId = 1;

    private final AuthorService authorService;

    @Autowired
    public BookService(@Lazy AuthorService authorService) {
        this.authorService = authorService;
    }

    static {
        booksRepo.add(new Book(1, "Potop", 1, 936));
        booksRepo.add(new Book(2, "Wesele", 2, 150));
        booksRepo.add(new Book(3, "Dziady", 3, 292));
        nextId += 3;
    }

    @Override
    public Collection<Book> getBooks() {
        return booksRepo;
    }

    @Override
    public Book getBook(int id) {
        return booksRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Boolean createBook(String title, int authorId, int pages) {
        Author bookAuthor = authorService.getAuthor(authorId);
        if (bookAuthor != null) {
            booksRepo.add(new Book(nextId, title, authorId, pages));
            nextId += 1;
            return true;
        }
        return false;
    }

    @Override
    public Book updateBook(int id, String title, int authorId, int pages) {
        Book bookToUpdate = booksRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);

        if (bookToUpdate != null) {
            Author bookAuthor = authorService.getAuthor(authorId);
            if (bookAuthor != null) {
                bookToUpdate.setAuthorId(authorId);
                bookToUpdate.setTitle(title);
                bookToUpdate.setPages(pages);
            } else return null;
        }
        return bookToUpdate;
    }

    @Override
    public Boolean deleteBook(int id) {
        Book bookToDelete = booksRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
        return bookToDelete != null && booksRepo.remove(bookToDelete);
    }
}
