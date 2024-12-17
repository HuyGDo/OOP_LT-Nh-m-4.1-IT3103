package service;

import entity.Borrow;
import java.util.List;

public interface BorrowService {
    void borrowBook(Borrow borrow);
    void returnBook(int borrowId);
    void deleteBorrowRecord(int borrowId);
    Borrow findBorrow(int borrowId);
    List<Borrow> getAllBorrows();
    List<Borrow> getBorrowsByReader(int readerId);
} 