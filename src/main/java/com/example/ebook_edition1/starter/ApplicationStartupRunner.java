package com.example.ebook_edition1;

import com.example.ebook_edition1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements ApplicationRunner {

    @Autowired
    private BookService bookService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // جلب وتخزين الكتب إذا كانت قاعدة البيانات فارغة
        if (bookService.getAllBooks().isEmpty()) {
            bookService.fetchProgrammingBooks();
        }
    }
}
