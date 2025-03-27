package com.example.library.model;

import lombok.Data;

@Data
public class Book {
    @NotBlank(message = "ID cannot be blank")
    private String id;
    
    @NotBlank(message = "Title cannot be blank")
    private String title;
    
    @NotBlank(message = "Author cannot be blank")
    private String author;
    
    private String genre;
    
    private String status = "Available"; // Default status
    
    public boolean isValidStatus() {
        return status.equals("Available") || status.equals("Checked Out");
    }
}