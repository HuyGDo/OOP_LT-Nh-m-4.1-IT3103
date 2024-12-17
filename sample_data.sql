-- Insert Categories
INSERT INTO categories (category_name) VALUES 
('Fiction'),
('Non-Fiction'),
('Science'),
('Technology'),
('History'),
('Literature'),
('Biography'),
('Self-Help');

-- Insert Books
INSERT INTO books (title, author, publisher, quantity, category_id) VALUES 
('The Great Gatsby', 'F. Scott Fitzgerald', 'Scribner', 5, 1),
('To Kill a Mockingbird', 'Harper Lee', 'Grand Central', 3, 1),
('A Brief History of Time', 'Stephen Hawking', 'Bantam', 2, 3),
('Clean Code', 'Robert C. Martin', 'Prentice Hall', 4, 4),
('The World War II', 'Winston Churchill', 'Houghton Mifflin', 2, 5),
('Pride and Prejudice', 'Jane Austen', 'Penguin Classics', 3, 6),
('Steve Jobs', 'Walter Isaacson', 'Simon & Schuster', 3, 7),
('The 7 Habits', 'Stephen Covey', 'Free Press', 5, 8);

-- Insert Readers
INSERT INTO readers (full_name, birth_date, address) VALUES 
('John Smith', '1990-05-15', '123 Main St, Boston'),
('Emma Wilson', '1985-08-22', '456 Park Ave, New York'),
('Michael Brown', '1992-03-10', '789 Oak Rd, Chicago'),
('Sarah Davis', '1988-11-28', '321 Pine St, San Francisco'),
('David Miller', '1995-07-03', '654 Maple Dr, Seattle');

-- Insert Borrows (some returned, some still borrowed)
INSERT INTO borrows (reader_id, book_id, borrow_date, expected_return_date, actual_return_date) VALUES 
(1, 1, '2024-01-15', '2024-01-29', '2024-01-28'),
(2, 3, '2024-02-01', '2024-02-15', '2024-02-14'),
(3, 4, '2024-02-10', '2024-02-24', NULL),
(4, 2, '2024-02-15', '2024-02-29', NULL),
(1, 5, '2024-02-20', '2024-03-05', NULL); 