    package com.example.ebook_edition1.service.impl;

    import com.example.ebook_edition1.model.UserBooks;
    import com.example.ebook_edition1.model.Book;
    import com.example.ebook_edition1.model.User;
    import com.example.ebook_edition1.repository.UserBooksRepository;
    import com.example.ebook_edition1.service.UserBooksService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class UserBooksServiceImpl implements UserBooksService {

        @Autowired
        private UserBooksRepository userBooksRepository;

        @Override
        public void addUserBook(User user, Book book) {
            if (!userBooksRepository.existsByUserAndBook(user,book)) {
                UserBooks userBook = new UserBooks();
                userBook.setUser(user);
                userBook.setBook(book);
                userBooksRepository.save(userBook);
            }
        }

        @Override
        public List<Book> getUserBooks(User user) {
            List<UserBooks> userBooks = userBooksRepository.findByUserId(user.getId());
            return userBooks.stream().map(UserBooks::getBook).collect(Collectors.toList());
        }
      public  List<Book>getBooksByUserEmail(String email)
      {
          return userBooksRepository.findBooksByUserEmail(email);
      }
    }
