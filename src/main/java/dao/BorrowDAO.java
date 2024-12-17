package dao;

import java.util.List;

import entity.Borrow;

public interface BorrowDAO {
    void add(Borrow borrow);
    void update(Borrow borrow);
    void delete(int borrowId);
    Borrow getById(int borrowId);
    List<Borrow> getAll();
    List<Borrow> getByReaderId(int readerId);
} 