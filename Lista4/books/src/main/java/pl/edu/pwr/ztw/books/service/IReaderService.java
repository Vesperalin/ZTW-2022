package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Reader;

import java.util.Collection;

public interface IReaderService {
    Collection<Reader> getReaders();
    Reader getReader(int id);
    void createReader(String firstName, String lastName);
    Reader updateReader(int id, String firstName, String lastName);
    Boolean deleteReader(int id);
}
