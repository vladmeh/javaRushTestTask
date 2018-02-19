package com.vladmeh.javaRushTestTask.Controller;

import com.vladmeh.javaRushTestTask.Entity.Book;
import com.vladmeh.javaRushTestTask.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping(path = "/books")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookRestController.class);

    @Value("src/main/resources/static/images/")
    private String pathUploadImage;

    @Autowired
    private BookService bookService;

    @RequestMapping(path = "")
    public String viewBooksList (
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ask") String order,
            @RequestParam(required = false, defaultValue = "") String term, // запрос на поиск
            @RequestParam(required = false, defaultValue = "0") int afterYear, // минимальный год выхода книги в печать
            @RequestParam(required = false, defaultValue = "") String ready,
            Model uiModel
    ){
        Sort sort;
        if (order.equals("desc")) sort = new Sort(Sort.Direction.DESC, sortBy);
        else sort = new Sort(Sort.Direction.ASC, sortBy);

        //Нумерация страниц для Spring Data JPA начинается с 0
        Integer pageNumber = (page > 0) ? page - 1 : 0;
        PageRequest pageRequest = new PageRequest(pageNumber, 8, sort);

        Page<Book> books;

        if (!ready.equals("") && (ready.equals("true") || ready.equals("false")))
            books = bookService.search(term, afterYear, Boolean.parseBoolean(ready), pageRequest);
        else
            books = bookService.search(term, afterYear, pageRequest);

        uiModel.addAttribute("books", books);
        uiModel.addAttribute("current", pageNumber);
        uiModel.addAttribute("term", term);
        uiModel.addAttribute("year", afterYear);
        uiModel.addAttribute("ready", ready);

        return "books/list";
    }

    @GetMapping(path = "/{id}")
    public String viewBook (@PathVariable Long id, Model uiModel){
        Book book = bookService.findById(id);

        uiModel.addAttribute("book", book);

        return "books/view";
    }

    @PostMapping(path = "/delete/{id}")
    public String deleteBook(@PathVariable Long id){
        Book book = bookService.findById(id);
        bookService.delete(book);

        return "redirect:/books";
    }

    @PostMapping(path = "/ready/{id}")
    public String isReadyBook(@PathVariable Long id, RedirectAttributes redirectAttributes){
        Book book = bookService.findById(id);
        book.setReadAlready(true);
        bookService.save(book);

        redirectAttributes.addAttribute("id", id);

        return "redirect:/books/{id}";
    }

    @GetMapping(path = "/edition/{id}")
    public String newEditionBook(@PathVariable Long id, Model uiModel){
        Book book = bookService.findById(id);
        uiModel.addAttribute("book", book);
        return "books/edition";
    }

    @PostMapping(path = "/edition/{id}")
    public String editionSubmit(
            @ModelAttribute Book book,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ){
        bookService.update(book, id);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/books/{id}";
    }

    @GetMapping(path = "/add")
    public String addBook(Model uiModel){
        uiModel.addAttribute("book", new Book());
        return "books/newBook";
    }

    @PostMapping(path = "/add")
    public String addSubmit(
            @ModelAttribute Book book
    ){
        bookService.save(book);

        return "redirect:/books";
    }
}
