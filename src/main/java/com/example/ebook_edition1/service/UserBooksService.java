package com.example.ebook_edition1.service;

import com.example.ebook_edition1.model.User;
import com.example.ebook_edition1.model.Book;

import java.util.List;

public interface UserBooksService {
    void addUserBook(User user, Book book);
    List<Book> getUserBooks(User user);
    List<Book>getBooksByUserEmail(String email);
}
