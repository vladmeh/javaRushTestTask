package com.vladmeh.javaRushTestTask.Service;


import com.vladmeh.javaRushTestTask.Entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    Book save(Book book);
    void delete(Book book);
}
