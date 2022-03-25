package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Reader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReaderService implements IReaderService {
    private static List<Reader> readersRepo = new ArrayList<>();
    private static int nextId = 1;

    static {
        readersRepo.add(new Reader(1, "Karol", "Abacki"));
        readersRepo.add(new Reader(2, "Zygmunt", "Kisiel"));
        readersRepo.add(new Reader(3, "Katarzyna", "Strojwąs"));
        nextId += 3;
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

    @Override
    public void createReader(String firstName, String lastName) {
        readersRepo.add(new Reader(nextId, firstName, lastName));
        nextId += 1;
    }

    @Override
    public Reader updateReader(int id, String firstName, String lastName) {
        Reader readerToUpdate = readersRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);

        if (readerToUpdate != null) {
            readerToUpdate.setFirstName(firstName);
            readerToUpdate.setLastName(lastName);
        }
        return readerToUpdate;
    }

    @Override
    public Boolean deleteReader(int id) {
        Reader readerToDelete = readersRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
        if (readerToDelete != null) {
            return readersRepo.remove(readerToDelete);
        }
        return false;
    }
}
