package com.vladmeh.javaRushTestTask.Service;


import com.vladmeh.javaRushTestTask.BookBuilder;
import com.vladmeh.javaRushTestTask.Controller.BookController;
import com.vladmeh.javaRushTestTask.Entity.Book;
import org.junit.Before;
import org.junit.Test;

import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceTest {
    private final List<Book> books = new ArrayList<>();

    @Before
    public void initBooks(){
        Book book = new BookBuilder()
                .id(990L)
                .autor("Иван Портянкин")
                .title("Swing. Эффектные пользовательские интерфейсы")
                .description("Создание пользовательских интерфейсов Java-приложений с помощью библиотеки Swing и Java Foundation Classes")
                .isbn("978-5-85582-305-9")
                .printYear(2011)
                .readAlready(false)
                .build();

        books.add(book);
    }


    @Test
    public void getAllBookTest() throws Exception{
        BookService bookService = mock(BookService.class);
        when(bookService.findAll()).thenReturn(books);

        BookController bookController = new BookController();

        ReflectionTestUtils.setField(bookController, "bookService", bookService);

        ExtendedModelMap uiModel = new ExtendedModelMap();
        uiModel.addAttribute("books", bookController.getAllBook());

        assertEquals(1, books.size());
    }

    @Test
    public void createTest() throws Exception{
        final Book newBook = new BookBuilder()
                .id(999L)
                .autor("Джошуа Блох")
                .title("Java. Эффективное программирование")
                .description("Первое издание книги \"Java. Эффективное программирование\", содержащей пятьдесят семь ценных правил, предлагает решение задач программирования, с которыми большинство разработчиков сталкиваются каждый день")
                .isbn("978-5-85582-347-9")
                .printYear(2014)
                .readAlready(false)
                .build();

        BookService bookService = mock(BookService.class);
        when(bookService.save(newBook)).thenAnswer((Answer<Book>) invocationOnMock -> {
            books.add(newBook);
            return newBook;
        });

        BookController bookController = new BookController();
        ReflectionTestUtils.setField(bookController, "bookService", bookService);

        Book book = bookController.create(newBook);
        assertEquals(999L, book.getId());
        assertEquals("Джошуа Блох", book.getAutor());
        assertEquals("978-5-85582-347-9", book.getIsbn());

        assertEquals(2, books.size());
    }
}
