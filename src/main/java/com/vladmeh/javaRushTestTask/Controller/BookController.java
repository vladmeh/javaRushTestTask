package com.vladmeh.javaRushTestTask.Controller;

import com.vladmeh.javaRushTestTask.Entity.Book;
import com.vladmeh.javaRushTestTask.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/books")
public class BookController {
    final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/all")
    public @ResponseBody
    List<Book> getAllBook() {
        return bookService.findAll();
    }

    @GetMapping(path = "")
    public @ResponseBody
    Page<Book> getPageBooks(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ask") String order
    ) {
        Sort sort;
        if (order.equals("desc")) sort = new Sort(Sort.Direction.DESC, sortBy);
        else sort = new Sort(Sort.Direction.ASC,sortBy);

        //Нумерация страниц для Spring Data JPA начинается с 0
        Integer pageNumber = (page > 0) ? page-1 : 0;
        PageRequest pageRequest = new PageRequest(pageNumber, 10, sort);
        return bookService.findAllByPage(pageRequest);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Book findBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Book create(@RequestBody Book book) {
        logger.info("Creating book: " + book);
        bookService.save(book);
        logger.info("Book created successfully with info: " + book);

        return book;
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody
    void update(@RequestBody Book book, @PathVariable Long id) {
        logger.info("Updating book: " + book);
        bookService.save(book);
        logger.info("Book update successfully with info: " + book);
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody
    void delete(@PathVariable Long id) {
        logger.info("Deleting book with id: " + id);
        Book book = bookService.findById(id);
        bookService.delete(book);
        logger.info("book deleted successfully");
    }
}
