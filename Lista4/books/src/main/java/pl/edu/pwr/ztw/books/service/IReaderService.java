package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Reader;

import java.util.Collection;

public interface IReaderService {
    public abstract Collection<Reader> getReaders();

    Reader getReader(int id);
}
