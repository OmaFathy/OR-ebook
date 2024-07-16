package com.example.ebook_edition1.service;

import com.example.ebook_edition1.model.Book;

import java.util.List;

public interface BookService {
    List<Book> fetchProgrammingBooks();
    List<Book> getAllBooks();
    void saveBook(Book book);
    List<Book> searchBook(String keyword);
    Book findById(Long id);
}
