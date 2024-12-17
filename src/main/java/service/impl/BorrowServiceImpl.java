package service.impl;

import dao.BorrowDAO;
import dao.impl.BorrowDAOImpl;
import entity.Borrow;
import service.BorrowService;
import java.util.Date;
import java.util.List;

public class BorrowServiceImpl implements BorrowService {
    private final BorrowDAO borrowDAO = new BorrowDAOImpl();
    
    @Override
    public void borrowBook(Borrow borrow) {
        validateBorrow(borrow);
        borrowDAO.add(borrow);
    }
    
    @Override
    public void returnBook(int borrowId) {
        Borrow borrow = borrowDAO.getById(borrowId);
        if (borrow == null) {
            throw new IllegalArgumentException("Borrow record not found");
        }
        
        borrow.setActualReturnDate(new Date());
        borrowDAO.update(borrow);
    }
    
    @Override
    public void deleteBorrowRecord(int borrowId) {
        if (borrowId <= 0) {
            throw new IllegalArgumentException("Invalid borrow ID");
        }
        borrowDAO.delete(borrowId);
    }
    
    @Override
    public Borrow findBorrow(int borrowId) {
        if (borrowId <= 0) {
            throw new IllegalArgumentException("Invalid borrow ID");
        }
        return borrowDAO.getById(borrowId);
    }
    
    @Override
    public List<Borrow> getAllBorrows() {
        return borrowDAO.getAll();
    }
    
    @Override
    public List<Borrow> getBorrowsByReader(int readerId) {
        if (readerId <= 0) {
            throw new IllegalArgumentException("Invalid reader ID");
        }
        return borrowDAO.getByReaderId(readerId);
    }
    
    private void validateBorrow(Borrow borrow) {
        if (borrow == null) {
            throw new IllegalArgumentException("Borrow record cannot be null");
        }
        if (borrow.getReader() == null) {
            throw new IllegalArgumentException("Reader cannot be null");
        }
        if (borrow.getBook() == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (borrow.getBorrowDate() == null) {
            throw new IllegalArgumentException("Borrow date cannot be null");
        }
        if (borrow.getExpectedReturnDate() == null) {
            throw new IllegalArgumentException("Expected return date cannot be null");
        }
        if (borrow.getExpectedReturnDate().before(borrow.getBorrowDate())) {
            throw new IllegalArgumentException("Expected return date cannot be before borrow date");
        }
    }
} 