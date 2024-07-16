package com.example.ebook_edition1.service.impl;

import com.example.ebook_edition1.model.Book;
import com.example.ebook_edition1.repository.BookRepository;
import com.example.ebook_edition1.service.BookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=programming&maxResults=40";

    @Override
    public List<Book> fetchProgrammingBooks() {
        String response = restTemplate.getForObject(API_URL, String.class);
        List<Book> books = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("items");

            for (JsonNode item : items) {
                JsonNode volumeInfo = item.path("volumeInfo");
                JsonNode searchInfo = item.path("searchInfo");
                JsonNode imageLinks = volumeInfo.path("imageLinks");

                String title = volumeInfo.path("title").asText();
                String author = volumeInfo.path("authors").get(0).asText();

                // تحقق مما إذا كان الكتاب موجودًا بالفعل
                if (!bookRepository.existsByTitleAndAuthor(title, author)) {
                    Book book = new Book();
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setDescription(volumeInfo.path("description").asText());
                    book.setThumbnail(imageLinks.path("thumbnail").asText());
                    book.setTextSnippet(searchInfo.path("textSnippet").asText());
                    book.setAvailability(true); // assuming all fetched books are available by default

                    books.add(book);
                }
            }

            bookRepository.saveAll(books);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> searchBook(String keyword) {
        List<Book> booksByTitle = bookRepository.findByTitleContainingIgnoreCase(keyword);
        List<Book> booksByAuthor = bookRepository.findByAuthorStartingWithIgnoreCase(keyword);

        // استخدام مجموعة لتجنب التكرارات
        Set<Book> combinedSet = new HashSet<>(booksByTitle);
        combinedSet.addAll(booksByAuthor);

        return new ArrayList<>(combinedSet);
    }
    @Override
    public  Book findById(Long id)
    {
        return bookRepository.findById(id).get();
    }
}
