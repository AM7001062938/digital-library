package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }
    
    public List<Book> searchBooks(String query) {
        return bookRepository.findAll().stream()
            .filter(book -> book.getId().equalsIgnoreCase(query) || 
                           book.getTitle().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public Book addBook(Book book) throws IllegalArgumentException {
        if (bookRepository.findById(book.getId()).isPresent()) {
            throw new IllegalArgumentException("Book ID must be unique");
        }
        if (!book.isValidStatus()) {
            throw new IllegalArgumentException("Status must be 'Available' or 'Checked Out'");
        }
        return bookRepository.save(book);
    }
    
    public Book updateBook(String id, Book updatedBook) throws IllegalArgumentException {
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        
        if (!updatedBook.isValidStatus()) {
            throw new IllegalArgumentException("Status must be 'Available' or 'Checked Out'");
        }
        
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setStatus(updatedBook.getStatus());
        
        return bookRepository.save(existingBook);
    }
    
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}