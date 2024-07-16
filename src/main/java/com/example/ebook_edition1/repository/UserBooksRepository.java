package com.example.ebook_edition1.repository;

import com.example.ebook_edition1.model.Book;
import com.example.ebook_edition1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ebook_edition1.model.UserBooks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBooksRepository extends JpaRepository<UserBooks, Integer> {

  List<UserBooks> findByUserId(Long userId );
  Boolean existsByUserAndBook(User user, Book book);
  @Query("SELECT ub.book FROM UserBooks ub WHERE ub.user.email = :email")
  List<Book> findBooksByUserEmail(@Param("email") String email);
}
