package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Author;

import java.util.Collection;

public interface IAuthorService {
    Collection<Author> getAuthors();
    Author getAuthor(int id);
    void createAuthor(String firstName, String lastName);
    Author updateAuthor(int id, String firstName, String lastName);
    Boolean deleteAuthor(int id);
}
