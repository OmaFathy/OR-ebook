package com.example.ebook_edition1.controller;

import com.example.ebook_edition1.model.Book;
import com.example.ebook_edition1.service.BookService;
import com.example.ebook_edition1.service.UserBooksService;
import com.example.ebook_edition1.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private UserBooksService userBooksService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String showHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String currentUsername = userService.getUsernameByEmail(email);

        List<Book> books = bookService.getAllBooks();
        List<Book> userBooks = userBooksService.getBooksByUserEmail(email);
        Set<Long> userBookIds = userBooks.stream().map(Book::getId).collect(Collectors.toSet());

        model.addAttribute("books", books);
        model.addAttribute("userBookIds", userBookIds);
        model.addAttribute("username", currentUsername);
        return "pages/home";
    }
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/";
        }

        List<Book> books = bookService.searchBook(keyword);
        model.addAttribute("books", books);

        // أضف معرّفات الكتب الخاصة بالمستخدم لضمان استمرار عرض حالة الكتب بشكل صحيح
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Book> userBooks = userBooksService.getBooksByUserEmail(email);
        Set<Long> userBookIds = userBooks.stream().map(Book::getId).collect(Collectors.toSet());
        model.addAttribute("userBookIds", userBookIds);

        return "fragments/book-list :: book-list";
    }

}
