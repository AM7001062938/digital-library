package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }
    
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "books/add";
        }
        try {
            bookService.addBook(book);
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "books/add";
        }
    }
    
    @GetMapping("/search")
    public String searchBooks(@RequestParam String query, Model model) {
        model.addAttribute("books", bookService.searchBooks(query));
        model.addAttribute("searchQuery", query);
        return "books/list";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        bookService.getBookById(id).ifPresent(book -> model.addAttribute("book", book));
        return "books/edit";
    }
    
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable String id, @Valid @ModelAttribute Book book, 
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "books/edit";
        }
        try {
            bookService.updateBook(id, book);
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "books/edit";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}