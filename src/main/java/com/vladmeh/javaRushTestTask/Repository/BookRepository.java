package com.vladmeh.javaRushTestTask.Repository;


import com.vladmeh.javaRushTestTask.Entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long>{
}
