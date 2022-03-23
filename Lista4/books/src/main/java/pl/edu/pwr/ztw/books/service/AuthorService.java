package pl.edu.pwr.ztw.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Author;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthorService implements IAuthorService {
    private static final List<Author> authorsRepo = new ArrayList<>();
    private static int nextId = 1;
    private final BookService bookService;

    @Autowired
    public AuthorService(BookService bookService) {
        this.bookService = bookService;
    }

    static {
        authorsRepo.add(new Author(1, "Henryk", "Sienkiewicz"));
        authorsRepo.add(new Author(2, "Władysław", "Reymont"));
        authorsRepo.add(new Author(3, "Adam", "Mickiewicz"));
        nextId += 3;
    }

    @Override
    public Collection<Author> getAuthors() {
        return authorsRepo;
    }

    @Override
    public Author getAuthor(int id) {
        return authorsRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public void createAuthor(String firstName, String lastName) {
        authorsRepo.add(new Author(nextId, firstName, lastName));
        nextId += 1;
    }

    @Override
    public Author updateAuthor(int id, String firstName, String lastName) {
        Author authorToUpdate = authorsRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);

        if (authorToUpdate != null) {
            authorToUpdate.setFirstName(firstName);
            authorToUpdate.setLastName(lastName);
        }
        return authorToUpdate;
    }

    @Override
    public Boolean deleteAuthor(int id) {
        Author authorToDelete = authorsRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
        if (authorToDelete != null) {
            bookService.getBooks().removeIf(book -> book.getAuthorId() == id);
            return authorsRepo.remove(authorToDelete);
        }
        return false;
    }

}
