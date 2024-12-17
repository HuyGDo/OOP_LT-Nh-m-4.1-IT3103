package service.impl;

import java.util.List;

import dao.impl.ReaderDAOImpl;
import entity.Reader;

public class ReaderServiceImpl {
    private final ReaderDAOImpl readerDAO = new ReaderDAOImpl();
    
    public void addReader(Reader reader) {
        validateReader(reader);
        readerDAO.add(reader);
    }
    
    public void updateReader(Reader reader) {
        validateReader(reader);
        readerDAO.update(reader);
    }
    
    public void deleteReader(int readerId) {
        if (readerId <= 0) {
            throw new IllegalArgumentException("Invalid reader ID");
        }
        readerDAO.delete(readerId);
    }
    
    public Reader findReader(int readerId) {
        if (readerId <= 0) {
            throw new IllegalArgumentException("Invalid reader ID");
        }
        return readerDAO.getById(readerId);
    }
    
    public List<Reader> getAllReaders() {
        return readerDAO.getAll();
    }
    
    private void validateReader(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null");
        }
        if (reader.getFullName() == null || reader.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Reader name cannot be empty");
        }
        if (reader.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        if (reader.getAddress() == null || reader.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
    }
} 