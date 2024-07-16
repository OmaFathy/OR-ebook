package com.example.ebook_edition1.controller;

import com.example.ebook_edition1.model.Book;
import com.example.ebook_edition1.model.User;
import com.example.ebook_edition1.service.BookService;
import com.example.ebook_edition1.service.UserBooksService;
import com.example.ebook_edition1.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserBooksController {

    @Autowired
    private UserBooksService userBooksService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookService bookService;

    @PostMapping("/add-to-mybooks/{id}")
    @ResponseBody
    public String addBooks(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailOfCurrentUser = authentication.getName();
        User currentUser = userService.getUserByEmail(emailOfCurrentUser);
        Book book = bookService.findById(id);

        userBooksService.addUserBook(currentUser, book);

        return "Book added successfully!";
    }

    @GetMapping("/mybooks")
    public String myBooks(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailOfCurrentUser = authentication.getName();
        User currentUser = userService.getUserByEmail(emailOfCurrentUser);
        List<Book> books = userBooksService.getUserBooks(currentUser);
        model.addAttribute("books", books);
        return "pages/mybooks";
    }
}
