package com.vladmeh.javaRushTestTask.Controller;

import com.vladmeh.javaRushTestTask.Entity.Book;
import com.vladmeh.javaRushTestTask.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/books")
public class BookController {
    final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping(path = "")
    public @ResponseBody
    List<Book> getAllBook() {
        return bookService.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Book findBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping(value = "")
    public @ResponseBody
    Book create(@RequestBody Book book){
        logger.info("Creating book: " + book);
        bookService.save(book);
        logger.info("Book created successfully with info: " + book);

        return book;
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody
    void update(@RequestBody Book book, @PathVariable Long id){
        logger.info("Updating book: " + book);
        bookService.save(book);
        logger.info("Book update successfully with info: " + book);
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody
    void delete(@PathVariable Long id){
        logger.info("Deleting book with id: " + id);
        Book book = bookService.findById(id);
        bookService.delete(book);
        logger.info("book deleted successfully");
    }
}
