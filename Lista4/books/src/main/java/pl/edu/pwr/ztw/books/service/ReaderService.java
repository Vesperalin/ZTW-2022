package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Reader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReaderService implements IReaderService {
    private static List<Reader> readersRepo = new ArrayList<>();

    static {
        readersRepo.add(new Reader(1, "Karol", "Abacki"));
        readersRepo.add(new Reader(2, "Zygmunt", "Kisiel"));
        readersRepo.add(new Reader(3, "Katarzyna", "Strojwąs"));
    }

    @Override
    public Collection<Reader> getReaders() {
        return readersRepo;
    }

    @Override
    public Reader getReader(int id) {
        return readersRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
    }
}
