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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BookRestControllerTest {

    private static final String PAGE_NUMBER_STRING = "2";
    private static final int PAGE_NUMBER = Integer.parseInt(PAGE_NUMBER_STRING) - 1;
    private static final int PAGE_SIZE = 10;
    private static final String FIELD_NAME_TITLE = "printYear";
    private static final String SORT_ORDER = "desc";
    private static final Long TEST_BOOK_ID = 1L;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
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
    public void getAllBook_shouldReturnSizeColletionTitleFirstBookAsJson() throws Exception {
        mockMvc.perform(get("/books/api/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$[0].title", is("Spring 4 для профессионалов")))
        ;
    }

    @Test
    public void getPageBooks_shouldReturnHttpResponseStatusOkNoParam() throws Exception {
        mockMvc.perform(get("/books/api")).andExpect(status().isOk());
    }

    @Test
    public void getPageBooks_shouldReturnHttpResponseStatusOkWithParam() throws Exception {
        mockMvc.perform(get("/books/api")
                .param("page", PAGE_NUMBER_STRING)
                .param("sortBy", FIELD_NAME_TITLE)
                .param("order", SORT_ORDER)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getPageBooks_shouldReturnPageNumberAndPageSizeAsJsonNoParam() throws Exception {
        mockMvc.perform(get("/books/api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(PAGE_SIZE)))
                .andExpect(jsonPath("$.content", hasSize(10)))
        ;
    }

    @Test
    public void getPageBooks_shouldReturnPageNumberAndPageSizeAsJsonWithParam() throws Exception {
        mockMvc.perform(get("/books/api")
                .param("page", PAGE_NUMBER_STRING)
                .param("sortBy", FIELD_NAME_TITLE)
                .param("order", SORT_ORDER)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$.number", is(PAGE_NUMBER)))
                .andExpect(jsonPath("$.size", is(PAGE_SIZE)));
    }

    @Test
    public void findBookById_shouldReturnHttpResponseStatusIsOKAndTitleAsJson() throws Exception{
        mockMvc.perform(get("/books/api/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Spring 4 для профессионалов")));
    }

    @Test
    public void create_shouldReturnHttpResponseStatusIsCreated() throws Exception {
        Book book = new BookBuilder()
                .autor("Иван Портянкин")
                .title("Swing. Эффектные пользовательские интерфейсы")
                .description("Создание пользовательских интерфейсов Java-приложений с помощью библиотеки Swing и Java Foundation Classes")
                .isbn("978-5-85582-305-9")
                .printYear(2011)
                .readAlready(false)
                .build();

        mockMvc.perform(post("/books/api")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(book))
            )
            .andExpect(status().isCreated());

        em.flush();
    }

    @Test
    public void updateAllData__shouldReturnHttpResponseStatusIsOK() throws Exception{

        Book book = new BookBuilder()
                .autor("Мет Зандстр")
                .title("PHP объекты, шаблоны и методики программирования")
                .description("Создавайте высокопрофессиональный код на PHP, изучив его объектно-орентированные возможности, шаблоны проектирования и важные средства разработ")
                .isbn("978-5-8459-1689-1")
                .printYear(2013)
                .readAlready(true)
                .build();

        mockMvc.perform(put("/books/api/{id}", TEST_BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(book))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("PHP объекты, шаблоны и методики программирования")))
        ;

        em.flush();
    }

    @Test
    public void updateOneFieldData_shouldReturnHttpResponseStatusIsOkAndIsReadAlReadyFalse() throws Exception{

        Book book = new BookBuilder().readAlready(false).build();

        mockMvc.perform(put("/books/api/{id}", TEST_BOOK_ID).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(book))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.readAlready", is(false)))
        ;

        em.flush();
    }

    @Test
    public void delete_shouldReturnHttpResponseStatusIsOkAndContentIsDeleted() throws Exception{

        mockMvc.perform(delete("/books/api/{id}", TEST_BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("deleted Book #" + TEST_BOOK_ID))
        ;

        em.flush();
    }

    @Test
    public void search_shouldReturnHttpResponseStatusOkWithoutParametrs() throws Exception{
        mockMvc.perform(get("/books/api/search")).andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(PAGE_SIZE)));

    }

    @Test
    public void search_shouldReturnHttpResponseStatusOkWithSearchTerm() throws Exception{
        mockMvc.perform(get("/books/api/search")
                .param("term", "java")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType));
    }

    @Test
    public void search_shouldReturnHttpResponseStatusOkWithAfterYear() throws Exception{
        mockMvc.perform(get("/books/api/search")
                .param("afterYear", "2016")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType));
    }

    @Test
    public void search_shouldReturnHttpResponseStatusOkWithRead() throws Exception{
        mockMvc.perform(get("/books/api/search")
                .param("read", "true")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType));
    }
}
