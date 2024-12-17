package service;

import entity.Reader;
import java.util.List;

public interface ReaderService {
    void addReader(Reader reader);
    void updateReader(Reader reader);
    void deleteReader(int readerId);
    Reader findReader(int readerId);
    List<Reader> getAllReaders();
} 