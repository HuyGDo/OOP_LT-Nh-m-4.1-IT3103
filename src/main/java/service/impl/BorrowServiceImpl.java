package service.impl;

import java.util.Date;
import java.util.List;

import dao.impl.BorrowDAOImpl;
import entity.Borrow;

public class BorrowServiceImpl {
    private final BorrowDAOImpl borrowDAO = new BorrowDAOImpl();
    
    public void borrowBook(Borrow borrow) {
        validateBorrow(borrow);
        borrowDAO.add(borrow);
    }
    
    public void returnBook(int borrowId) {
        Borrow borrow = borrowDAO.getById(borrowId);
        if (borrow == null) {
            throw new IllegalArgumentException("Borrow record not found");
        }
        
        borrow.setActualReturnDate(new Date());
        borrowDAO.update(borrow);
    }
    
    public void deleteBorrowRecord(int borrowId) {
        if (borrowId <= 0) {
            throw new IllegalArgumentException("Invalid borrow ID");
        }
        borrowDAO.delete(borrowId);
    }
    
    public Borrow findBorrow(int borrowId) {
        if (borrowId <= 0) {
            throw new IllegalArgumentException("Invalid borrow ID");
        }
        return borrowDAO.getById(borrowId);
    }
    
    public List<Borrow> getAllBorrows() {
        return borrowDAO.getAll();
    }
    
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