package dao;

import entity.Reader;
import java.util.List;

public interface ReaderDAO {
    void add(Reader reader);
    void update(Reader reader);
    void delete(int readerId);
    Reader getById(int readerId);
    List<Reader> getAll();
} 