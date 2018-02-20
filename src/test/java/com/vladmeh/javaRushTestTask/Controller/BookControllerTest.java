package com.vladmeh.javaRushTestTask.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmeh.javaRushTestTask.Application;
import com.vladmeh.javaRushTestTask.BookBuilder;
import com.vladmeh.javaRushTestTask.Entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileInputStream;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BookControllerTest {

    private static final String PAGE_NUMBER_STRING = "2";
    private static final int PAGE_NUMBER = Integer.parseInt(PAGE_NUMBER_STRING) - 1;
    private static final int PAGE_SIZE = 10;
    private static final String FIELD_NAME_TITLE = "printYear";
    private static final String SORT_ORDER = "desc";
    private static final Long TEST_BOOK_ID = 1L;

    private MediaType contentType = new MediaType(MediaType.TEXT_HTML.getType(),
            MediaType.TEXT_HTML.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void viewBooksList_shouldReturnHttpResponseStatusOkNoParam() throws Exception {
        this.mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(content().string(containsString("Книжная полка")));
    }

    @Test
    public void viewBooksList_houldReturnHttpResponseStatusOkWithParam() throws Exception{
        mockMvc.perform(get("/books")
                .param("page", PAGE_NUMBER_STRING)
                .param("sortBy", FIELD_NAME_TITLE)
                .param("order", SORT_ORDER)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void viewBook_shouldReturnHttpResponseStatusIsOKAndTitle() throws Exception{
        mockMvc.perform(get("/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(containsString("Spring 4 для профессионалов")));
    }

    @Test
    public void deleteBook_shouldReturnHttpResponseStatusIsRedirection() throws Exception{

        mockMvc.perform(post("/books/delete/{id}", TEST_BOOK_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        em.flush();
    }

    @Test
    public void isReadyBook_shouldReturnHttpResponseStatusIsRedirection() throws Exception{

        mockMvc.perform(post("/books/ready/{id}", TEST_BOOK_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + TEST_BOOK_ID));

        em.flush();
    }

    @Test
    public void newEditionBook_shouldReturnHttpResponseStatusIsOkAndTitle() throws Exception{

        mockMvc.perform(get("/books/edition/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(containsString("Spring 4 для профессионалов")));
    }

    @Test
    public void editionSubmit_shouldSaveUploadedFile() throws Exception{
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.jpg",
                MediaType.IMAGE_JPEG_VALUE, "Spring Framework".getBytes());

        mockMvc.perform(fileUpload("/books/edition/{id}", TEST_BOOK_ID).file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + TEST_BOOK_ID));

        em.flush();
    }

    @Test
    public void addBook_shouldReturnHttpResponseStatusIsOkAndTitle() throws Exception{

        mockMvc.perform(get("/books/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(containsString("Новая книга")));
    }

    @Test
    public void addSubmit_shouldReturnHttpResponseStatusIsRedirection() throws Exception{
        Book book = new BookBuilder()
                .autor("Иван Портянкин")
                .title("Swing. Эффектные пользовательские интерфейсы")
                .description("Создание пользовательских интерфейсов Java-приложений с помощью библиотеки Swing и Java Foundation Classes")
                .isbn("978-5-85582-305-9")
                .printYear(2011)
                .readAlready(false)
                .build();

        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE, "Spring Framework".getBytes());

        mockMvc.perform(fileUpload("/books/add")
                .file(multipartFile)
                .content(objectMapper.writeValueAsString(book))
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        em.flush();
    }
}