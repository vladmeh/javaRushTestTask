package com.vladmeh.javaRushTestTask.Service;


import com.vladmeh.javaRushTestTask.Entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    Book save(Book book);
    void delete(Book book);
    Page<Book> findAllByPage(Pageable pageable);

    Book update(Book book, Long id);

    Page<Book> search(String term, int printYear, Pageable pageable);
    Page<Book> search(String term, int printYear, boolean readAlReady, Pageable pageable);
}
