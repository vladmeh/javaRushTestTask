package com.vladmeh.javaRushTestTask.Repository;


import com.vladmeh.javaRushTestTask.Entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}
