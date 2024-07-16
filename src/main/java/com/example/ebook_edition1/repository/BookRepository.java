package com.example.ebook_edition1.repository;

import com.example.ebook_edition1.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitleAndAuthor(String title, String author);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorStartingWithIgnoreCase(String author);

}
