package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {
    private final ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();
    
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
    
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));
    }
    
    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }
    
    public void deleteById(String id) {
        books.remove(id);
    }
}